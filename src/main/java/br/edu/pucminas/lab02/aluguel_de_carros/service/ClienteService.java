package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Cliente;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public Cliente buscar(String id) { return buscarEntidade(id); }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        preparar(cliente);
        validarCpf(cliente.getCpf());
        if (repository.existsByCpf(normalizarCpf(cliente.getCpf()))) {
            throw new CpfDuplicadoException();
        }
        return repository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(String id, Cliente dados) {
        Cliente cliente = buscarEntidade(id);
        preparar(dados);
        validarCpf(dados.getCpf());
        if (repository.existsByCpfAndIdNot(normalizarCpf(dados.getCpf()), id)) {
            throw new CpfDuplicadoException();
        }
        cliente.setNome(dados.getNome());
        cliente.setEmail(dados.getEmail());
        cliente.setSenha(dados.getSenha());
        cliente.setRg(dados.getRg());
        cliente.setCpf(dados.getCpf());
        cliente.setEndereco(dados.getEndereco());
        cliente.setProfissao(dados.getProfissao());
        cliente.setRendimentos(dados.getRendimentos());
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(String id) {
        repository.delete(buscarEntidade(id));
    }

    private Cliente buscarEntidade(String id) {
        return repository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    }

    private void preparar(Cliente cliente) {
        cliente.setCpf(normalizarCpf(cliente.getCpf()));
        cliente.getRendimentos().removeIf(rendimento ->
                rendimento.getNomeEmpregadora() == null || rendimento.getNomeEmpregadora().isBlank());
    }

    private String normalizarCpf(String cpf) {
        return cpf == null ? null : cpf.replaceAll("\\D", "");
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.chars().distinct().count() == 1
                || !validarDigito(cpf, 9) || !validarDigito(cpf, 10)) {
            throw new CpfInvalidoException();
        }
    }

    private boolean validarDigito(String cpf, int posicao) {
        int soma = 0;
        for (int indice = 0; indice < posicao; indice++) {
            soma += (cpf.charAt(indice) - '0') * (posicao + 1 - indice);
        }
        int digito = (soma * 10) % 11;
        return digito == 10 ? cpf.charAt(posicao) == '0' : cpf.charAt(posicao) - '0' == digito;
    }

    public static class ClienteNotFoundException extends RuntimeException {
        public ClienteNotFoundException(String id) { super("Cliente nao encontrado: " + id); }
    }

    public static class CpfDuplicadoException extends RuntimeException {
        public CpfDuplicadoException() { super("CPF ja cadastrado"); }
    }

    public static class CpfInvalidoException extends RuntimeException {
        public CpfInvalidoException() { super("CPF invalido"); }
    }
}