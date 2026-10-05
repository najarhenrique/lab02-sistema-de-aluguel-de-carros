package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.AgenteCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Agente;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Banco;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Empresa;
import br.edu.pucminas.lab02.aluguel_de_carros.model.TipoAgente;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.AgenteRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgenteService {

    private final AgenteRepository repository;
    private final UsuarioRepository usuarioRepository;

    public AgenteService(AgenteRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Agente> listar() { return repository.findAll(); }

    @Transactional
    public Agente salvar(AgenteCreateRequest dados) {
        String cnpj = dados.cnpj().replaceAll("[.\\-/\\s]", "");
        if (repository.existsByCnpj(cnpj)) {
            throw new CnpjDuplicadoException();
        }
        if (usuarioRepository.existsByEmail(dados.email())) {
            throw new ClienteService.EmailDuplicadoException();
        }
        Agente agente = dados.tipo() == TipoAgente.BANCO ? new Banco() : new Empresa();
        agente.setNome(dados.nome());
        agente.setEmail(dados.email());
        agente.setSenha(dados.senha());
        agente.setCnpj(cnpj);
        agente.setRazaoSocial(dados.razaoSocial());
        return repository.save(agente);
    }

    public static class CnpjDuplicadoException extends RuntimeException {
        public CnpjDuplicadoException() { super("CNPJ ja cadastrado"); }
    }
}
