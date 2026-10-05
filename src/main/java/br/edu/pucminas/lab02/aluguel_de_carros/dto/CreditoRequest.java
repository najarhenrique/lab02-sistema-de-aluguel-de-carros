package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreditoRequest(
    @NotNull(message = "Valor financiado e obrigatorio")
    @Positive(message = "Valor financiado deve ser maior que zero")
    Double valorFinanciado,

    @NotNull(message = "Taxa de juros e obrigatoria")
    @PositiveOrZero(message = "Taxa de juros nao pode ser negativa")
    Double taxaJuros
) {}
