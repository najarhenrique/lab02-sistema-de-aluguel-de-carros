package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.ContratoCredito;

public record CreditoResponse(
    double valorFinanciado,
    double taxaJuros,
    String banco
) {
    public static CreditoResponse from(ContratoCredito credito) {
        if (credito == null) {
            return null;
        }
        return new CreditoResponse(credito.getValorFinanciado(), credito.getTaxaJuros(), credito.getBanco().getNome());
    }
}
