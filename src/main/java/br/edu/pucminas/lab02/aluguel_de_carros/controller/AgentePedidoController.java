package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.CreditoRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.ParecerRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.PedidoAgenteResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusPedido;
import br.edu.pucminas.lab02.aluguel_de_carros.service.AvaliacaoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agente/pedidos")
public class AgentePedidoController {

    static final String AGENTE_HEADER = "X-Agente-Id";

    private final AvaliacaoService service;

    public AgentePedidoController(AvaliacaoService service) { this.service = service; }

    @GetMapping
    public List<PedidoAgenteResponse> listar(@RequestHeader(AGENTE_HEADER) String agenteId,
                                             @RequestParam(required = false) StatusPedido status) {
        return service.listar(agenteId, status).stream().map(PedidoAgenteResponse::from).toList();
    }

    @GetMapping("/{id}")
    public PedidoAgenteResponse buscar(@RequestHeader(AGENTE_HEADER) String agenteId, @PathVariable String id) {
        return PedidoAgenteResponse.from(service.buscar(agenteId, id));
    }

    @PostMapping("/{id}/parecer")
    public PedidoAgenteResponse avaliar(@RequestHeader(AGENTE_HEADER) String agenteId, @PathVariable String id,
                                        @Valid @RequestBody ParecerRequest request) {
        return PedidoAgenteResponse.from(service.avaliar(agenteId, id, request));
    }

    @PostMapping("/{id}/credito")
    public PedidoAgenteResponse concederCredito(@RequestHeader(AGENTE_HEADER) String agenteId,
                                                @PathVariable String id,
                                                @Valid @RequestBody CreditoRequest request) {
        return PedidoAgenteResponse.from(service.concederCredito(agenteId, id, request));
    }
}
