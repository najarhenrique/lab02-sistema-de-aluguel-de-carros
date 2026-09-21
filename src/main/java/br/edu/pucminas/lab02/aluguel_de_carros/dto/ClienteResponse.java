package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import java.util.List;

public record ClienteResponse(
    String id,
    String rg,
    String cpf,
    String endereco,
    String profissao,
    List<RendimentoResponse> rendimentos
) {}
