package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.ModalidadeContrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.PedidoAluguel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusPedido;
import java.time.LocalDateTime;

public record PedidoResponse(
    String id,
    LocalDateTime dataPedido,
    StatusPedido status,
    ModalidadeContrato modalidade,
    AutomovelResponse automovel,
    ParecerResponse parecer,
    ContratoResponse contrato
) {
    public static PedidoResponse from(PedidoAluguel pedido) {
        return new PedidoResponse(pedido.getId(), pedido.getDataPedido(), pedido.getStatus(),
                pedido.getModalidade(), AutomovelResponse.from(pedido.getAutomovel()),
                ParecerResponse.from(pedido.getParecer()), ContratoResponse.from(pedido.getContrato()));
    }
}
