package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record RendimentoUpdateRequest(
    @NotBlank(message = "Nome da empregadora e obrigatorio")
    String nomeEmpregadora,

    @PositiveOrZero(message = "Rendimento nao pode ser negativo")
    double rendimento
) {}
