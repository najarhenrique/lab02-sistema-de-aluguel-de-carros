package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Agente;
import br.edu.pucminas.lab02.aluguel_de_carros.model.TipoUsuario;

public record AgenteResponse(
    String id,
    String nome,
    TipoUsuario tipo
) {
    public static AgenteResponse from(Agente agente) {
        return new AgenteResponse(agente.getId(), agente.getNome(), agente.getTipo());
    }
}
