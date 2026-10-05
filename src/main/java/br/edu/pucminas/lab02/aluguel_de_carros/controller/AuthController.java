package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.LoginRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.LoginResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.service.AutenticacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AutenticacaoService service;

    public AuthController(AutenticacaoService service) { this.service = service; }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return LoginResponse.from(service.autenticar(request.email(), request.senha()));
    }
}
