package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "agentes")
public abstract class Agente extends Usuario {

    @NotBlank(message = "CNPJ e obrigatorio")
    private String cnpj;

    @NotBlank(message = "Razao social e obrigatoria")
    private String razaoSocial;

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
}
