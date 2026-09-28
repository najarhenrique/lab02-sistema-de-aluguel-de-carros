package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Automovel;

public record AutomovelResponse(
    String placa,
    int ano,
    String marca,
    String modelo
) {
    public static AutomovelResponse from(Automovel automovel) {
        return new AutomovelResponse(automovel.getPlaca(), automovel.getAno(), automovel.getMarca(), automovel.getModelo());
    }
}
