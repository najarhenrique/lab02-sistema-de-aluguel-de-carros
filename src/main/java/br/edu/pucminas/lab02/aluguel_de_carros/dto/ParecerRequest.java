package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ParecerRequest(
    @NotNull(message = "Informe se o pedido foi aprovado")
    Boolean aprovado,

    @NotBlank(message = "Parecer e obrigatorio")
    String parecer,

    boolean necessitaCredito,

    Double valorMensal,
    LocalDate dataInicio,
    LocalDate dataFim
) {}
