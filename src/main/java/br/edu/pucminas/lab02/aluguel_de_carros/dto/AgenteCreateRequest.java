package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import br.edu.pucminas.lab02.aluguel_de_carros.model.TipoAgente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AgenteCreateRequest(
    @NotNull(message = "Tipo do agente e obrigatorio")
    TipoAgente tipo,

    @NotBlank(message = "Nome e obrigatorio")
    String nome,

    @NotBlank(message = "E-mail e obrigatorio")
    String email,

    @NotBlank(message = "Senha e obrigatoria")
    String senha,

    @NotBlank(message = "CNPJ e obrigatorio")
    String cnpj,

    @NotBlank(message = "Razao social e obrigatoria")
    String razaoSocial
) {}
