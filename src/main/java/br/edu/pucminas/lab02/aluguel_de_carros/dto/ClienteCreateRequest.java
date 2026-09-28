package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ClienteCreateRequest(
    @NotBlank(message = "Nome e obrigatorio")
    String nome,

    @NotBlank(message = "E-mail e obrigatorio")
    String email,

    @NotBlank(message = "Senha e obrigatoria")
    String senha,

    @NotBlank(message = "RG e obrigatorio")
    String rg,

    @NotBlank(message = "CPF e obrigatorio")
    String cpf,

    @NotBlank(message = "Endereco e obrigatorio")
    String endereco,

    String profissao,

    @Valid
    @Size(max = 3, message = "Informe no maximo 3 empregadoras")
    List<RendimentoUpdateRequest> rendimentos
) {}
