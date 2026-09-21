package br.edu.pucminas.lab02.aluguel_de_carros.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClienteRequest (
    @NotBlank(message = "CPF e obrigatorio")
    String rg,

    @NotBlank 
    @Pattern(regexp = "[0-9.\\- ]+", message = "CPF deve conter apenas numeros") 
    String cpf,
    
    @NotBlank(message = "Endereco e obrigatorio")
    String endereco,

    String profissao
) {}
