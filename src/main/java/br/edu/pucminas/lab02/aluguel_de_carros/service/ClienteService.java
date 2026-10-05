package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.ClienteCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.ClienteUpdateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.RendimentoUpdateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Cliente;
import br.edu.pucminas.lab02.aluguel_de_carros.model.RendimentoEmpregadora;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.ClienteRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.PedidoAluguelRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final PedidoAluguelRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository repository, PedidoAluguelRepository pedidoRepository,
                          UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Cliente> listar() { return repository.findAll(); }

    @Transactional(readOnly = true)
    public Cliente buscar(String id) { return buscarEntidade(id); }

    @Transactional
    public Cliente salvar(ClienteCreateRequest dados) {
        String cpf = normalizarCpf(dados.cpf());
        if (repository.existsByCpf(cpf)) {
            throw new CpfDuplicadoException();
        }
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new EmailDuplicadoException();
        }
        Cliente cliente = new Cliente();
        cliente.setNome(dados.nome());
        cliente.setEmail(dados.email());
        cliente.setSenha(dados.senha());
        cliente.setRg(dados.rg());
        cliente.setCpf(cpf);
        cliente.setEndereco(dados.endereco());
        cliente.setProfissao(dados.profissao());
        cliente.setRendimentos(converterRendimentos(dados.rendimentos()));
        return repository.save(cliente);
    }

    @Transactional
    public Cliente atualizar(String id, ClienteUpdateRequest dados) {
        Cliente cliente = buscarEntidade(id);
        cliente.setEndereco(dados.endereco());
        cliente.setProfissao(dados.profissao());
        cliente.setRendimentos(converterRendimentos(dados.rendimentos()));
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(String id) {
        Cliente cliente = buscarEntidade(id);
        pedidoRepository.deleteByClienteId(id);
        repository.delete(cliente);
    }

    private Cliente buscarEntidade(String id) {
        return repository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
    }

    private List<RendimentoEmpregadora> converterRendimentos(List<RendimentoUpdateRequest> rendimentos) {
        List<RendimentoEmpregadora> resultado = new ArrayList<>();
        if (rendimentos == null) {
            return resultado;
        }
        for (RendimentoUpdateRequest rendimento : rendimentos) {
            if (rendimento.nomeEmpregadora() != null && !rendimento.nomeEmpregadora().isBlank()) {
                resultado.add(new RendimentoEmpregadora(rendimento.nomeEmpregadora(), rendimento.rendimento()));
            }
        }
        return resultado;
    }

    private String normalizarCpf(String cpf) {
        return cpf == null ? null : cpf.replaceAll("[.\\-\\s]", "");
    }

    public static class ClienteNotFoundException extends RuntimeException {
        public ClienteNotFoundException(String id) { super("Cliente nao encontrado: " + id); }
    }

    public static class CpfDuplicadoException extends RuntimeException {
        public CpfDuplicadoException() { super("CPF ja cadastrado"); }
    }

    public static class EmailDuplicadoException extends RuntimeException {
        public EmailDuplicadoException() { super("E-mail ja cadastrado"); }
    }
}
