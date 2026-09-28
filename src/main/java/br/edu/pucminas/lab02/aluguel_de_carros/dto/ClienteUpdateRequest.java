package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ClienteUpdateRequest(
    @NotBlank(message = "Endereco e obrigatorio")
    String endereco,

    String profissao,

    @Valid
    @Size(max = 3, message = "Informe no maximo 3 empregadoras")
    List<RendimentoUpdateRequest> rendimentos
) {}
