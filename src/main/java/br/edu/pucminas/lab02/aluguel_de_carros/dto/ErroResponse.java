package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import java.util.Map;

public record ErroResponse(
    String mensagem,
    Map<String, String> campos
) {
    public ErroResponse(String mensagem) { this(mensagem, Map.of()); }
}
