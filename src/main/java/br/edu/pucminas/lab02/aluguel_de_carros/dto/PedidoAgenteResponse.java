package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.PedidoAluguel;

public record PedidoAgenteResponse(
    PedidoResponse pedido,
    ClienteResponse cliente
) {
    public static PedidoAgenteResponse from(PedidoAluguel pedido) {
        return new PedidoAgenteResponse(PedidoResponse.from(pedido), ClienteResponse.from(pedido.getCliente()));
    }
}
