package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.RendimentoEmpregadora;

public record RendimentoResponse(
    String nomeEmpregadora,
    double rendimento
) {
    public static RendimentoResponse from(RendimentoEmpregadora rendimento) {
        return new RendimentoResponse(rendimento.getNomeEmpregadora(), rendimento.getRendimento());
    }
}
