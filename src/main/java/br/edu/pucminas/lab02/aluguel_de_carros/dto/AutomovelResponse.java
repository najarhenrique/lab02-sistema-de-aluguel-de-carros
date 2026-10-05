package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Automovel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.TipoUsuario;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Usuario;

public record AutomovelResponse(
    String placa,
    int ano,
    String marca,
    String modelo,
    String proprietarioId,
    String proprietarioNome,
    TipoUsuario proprietarioTipo
) {
    public static AutomovelResponse from(Automovel automovel) {
        Usuario proprietario = automovel.getProprietario();
        return new AutomovelResponse(automovel.getPlaca(), automovel.getAno(), automovel.getMarca(),
                automovel.getModelo(),
                proprietario == null ? null : proprietario.getId(),
                proprietario == null ? null : proprietario.getNome(),
                proprietario == null ? null : proprietario.getTipo());
    }
}
