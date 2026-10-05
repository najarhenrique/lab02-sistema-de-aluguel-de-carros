package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.ModalidadeContrato;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PedidoCreateRequest(
    @NotBlank(message = "Placa e obrigatoria")
    String placa,

    @NotNull(message = "Ano e obrigatorio")
    @Min(value = 1900, message = "Ano deve ser a partir de 1900")
    Integer ano,

    @NotBlank(message = "Marca e obrigatoria")
    String marca,

    @NotBlank(message = "Modelo e obrigatorio")
    String modelo,

    @NotNull(message = "Modalidade e obrigatoria")
    ModalidadeContrato modalidade,

    @NotBlank(message = "Proprietario do automovel e obrigatorio")
    String proprietarioId
) {}
