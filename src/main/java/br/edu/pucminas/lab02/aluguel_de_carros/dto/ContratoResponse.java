package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Contrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusContrato;
import java.time.LocalDate;

public record ContratoResponse(
    double valorMensal,
    LocalDate dataInicio,
    LocalDate dataFim,
    boolean assinadoCliente,
    StatusContrato status,
    CreditoResponse credito
) {
    public static ContratoResponse from(Contrato contrato) {
        if (contrato == null) {
            return null;
        }
        return new ContratoResponse(contrato.getValorMensal(), contrato.getDataInicio(), contrato.getDataFim(),
                contrato.isAssinadoCliente(), contrato.getStatus(), CreditoResponse.from(contrato.getCredito()));
    }
}
