package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.AgenteCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.AgenteResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.service.AgenteService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agentes")
public class AgenteController {

    private final AgenteService service;

    public AgenteController(AgenteService service) { this.service = service; }

    @GetMapping
    public List<AgenteResponse> listar() {
        return service.listar().stream().map(AgenteResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<AgenteResponse> criar(@Valid @RequestBody AgenteCreateRequest request) {
        AgenteResponse agente = AgenteResponse.from(service.salvar(request));
        return ResponseEntity.created(URI.create("/api/agentes/" + agente.id())).body(agente);
    }
}
