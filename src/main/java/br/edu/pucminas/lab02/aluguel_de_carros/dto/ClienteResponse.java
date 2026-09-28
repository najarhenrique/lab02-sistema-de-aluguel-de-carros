package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Cliente;
import java.util.List;

public record ClienteResponse(
    String id,
    String nome,
    String email,
    String rg,
    String cpf,
    String endereco,
    String profissao,
    List<RendimentoResponse> rendimentos
) {
    public static ClienteResponse from(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getRg(),
                cliente.getCpf(), cliente.getEndereco(), cliente.getProfissao(),
                cliente.getRendimentos().stream().map(RendimentoResponse::from).toList());
    }
}
