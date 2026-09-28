package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.ClienteCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.ClienteResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.ClienteUpdateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.service.ClienteService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) { this.service = service; }

    @GetMapping
    public List<ClienteResponse> listar() {
        return service.listar().stream().map(ClienteResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ClienteResponse buscar(@PathVariable String id) {
        return ClienteResponse.from(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody ClienteCreateRequest request) {
        ClienteResponse cliente = ClienteResponse.from(service.salvar(request));
        return ResponseEntity.created(URI.create("/api/clientes/" + cliente.id())).body(cliente);
    }

    @PutMapping("/{id}")
    public ClienteResponse atualizar(@PathVariable String id, @Valid @RequestBody ClienteUpdateRequest request) {
        return ClienteResponse.from(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
