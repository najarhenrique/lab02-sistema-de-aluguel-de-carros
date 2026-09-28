package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.ClienteResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.LoginRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClienteService service;

    public AuthController(ClienteService service) { this.service = service; }

    @PostMapping("/login")
    public ClienteResponse login(@Valid @RequestBody LoginRequest request) {
        return ClienteResponse.from(service.autenticar(request.email(), request.senha()));
    }
}
