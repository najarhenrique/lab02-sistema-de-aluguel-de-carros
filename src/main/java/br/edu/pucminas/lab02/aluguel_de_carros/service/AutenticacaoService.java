package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Usuario;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutenticacaoService {

    private final UsuarioRepository repository;

    public AutenticacaoService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Usuario autenticar(String email, String senha) {
        return repository.findByEmail(email)
                .filter(usuario -> usuario.getSenha().equals(senha))
                .orElseThrow(CredenciaisInvalidasException::new);
    }

    public static class CredenciaisInvalidasException extends RuntimeException {
        public CredenciaisInvalidasException() { super("E-mail ou senha invalidos"); }
    }
}
