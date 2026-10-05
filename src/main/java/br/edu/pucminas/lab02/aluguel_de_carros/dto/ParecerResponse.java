package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.ParecerFinanceiro;
import java.time.LocalDateTime;

public record ParecerResponse(
    boolean aprovado,
    String parecer,
    LocalDateTime dataAvaliacao,
    boolean necessitaCredito,
    String agente
) {
    public static ParecerResponse from(ParecerFinanceiro parecer) {
        if (parecer == null) {
            return null;
        }
        return new ParecerResponse(parecer.isAprovado(), parecer.getParecer(), parecer.getDataAvaliacao(),
                parecer.isNecessitaCredito(), parecer.getAgente().getNome());
    }
}
