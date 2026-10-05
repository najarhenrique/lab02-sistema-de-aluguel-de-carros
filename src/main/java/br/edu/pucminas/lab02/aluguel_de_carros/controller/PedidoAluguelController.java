package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.PedidoCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.PedidoResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.service.PedidoAluguelService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoAluguelController {

    static final String CLIENTE_HEADER = "X-Cliente-Id";

    private final PedidoAluguelService service;

    public PedidoAluguelController(PedidoAluguelService service) { this.service = service; }

    @GetMapping
    public List<PedidoResponse> listar(@RequestHeader(CLIENTE_HEADER) String clienteId) {
        return service.listarDoCliente(clienteId).stream().map(PedidoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public PedidoResponse buscar(@RequestHeader(CLIENTE_HEADER) String clienteId, @PathVariable String id) {
        return PedidoResponse.from(service.buscarDoCliente(id, clienteId));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(@RequestHeader(CLIENTE_HEADER) String clienteId,
                                                @Valid @RequestBody PedidoCreateRequest request) {
        PedidoResponse pedido = PedidoResponse.from(service.criar(clienteId, request));
        return ResponseEntity.created(URI.create("/api/pedidos/" + pedido.id())).body(pedido);
    }

    @PutMapping("/{id}")
    public PedidoResponse alterar(@RequestHeader(CLIENTE_HEADER) String clienteId, @PathVariable String id,
                                  @Valid @RequestBody PedidoCreateRequest request) {
        return PedidoResponse.from(service.alterar(id, clienteId, request));
    }

    @PostMapping("/{id}/cancelar")
    public PedidoResponse cancelar(@RequestHeader(CLIENTE_HEADER) String clienteId, @PathVariable String id) {
        return PedidoResponse.from(service.cancelar(id, clienteId));
    }

    @PostMapping("/{id}/contrato/aceitar")
    public PedidoResponse aceitarContrato(@RequestHeader(CLIENTE_HEADER) String clienteId, @PathVariable String id) {
        return PedidoResponse.from(service.aceitarContrato(id, clienteId));
    }

    @PostMapping("/{id}/contrato/recusar")
    public PedidoResponse recusarContrato(@RequestHeader(CLIENTE_HEADER) String clienteId, @PathVariable String id) {
        return PedidoResponse.from(service.recusarContrato(id, clienteId));
    }
}
