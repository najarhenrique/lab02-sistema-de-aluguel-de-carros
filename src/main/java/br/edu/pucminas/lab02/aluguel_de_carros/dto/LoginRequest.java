package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "E-mail e obrigatorio")
    String email,

    @NotBlank(message = "Senha e obrigatoria")
    String senha
) {}
