package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.TipoUsuario;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Usuario;

public record LoginResponse(
    String id,
    String nome,
    String email,
    TipoUsuario tipo
) {
    public static LoginResponse from(Usuario usuario) {
        return new LoginResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipo());
    }
}
