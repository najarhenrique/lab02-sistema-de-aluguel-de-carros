package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Embeddable
public class RendimentoEmpregadora {

    @NotBlank(message = "Nome da empregadora e obrigatorio")
    private String nomeEmpregadora;

    @PositiveOrZero(message = "Rendimento nao pode ser negativo")
    private double rendimento;

    public RendimentoEmpregadora() {}

    public RendimentoEmpregadora(String nomeEmpregadora, double rendimento) {
        this.nomeEmpregadora = nomeEmpregadora;
        this.rendimento = rendimento;
    }

    public String getNomeEmpregadora() { return nomeEmpregadora; }
    public void setNomeEmpregadora(String nomeEmpregadora) { this.nomeEmpregadora = nomeEmpregadora; }
    public double getRendimento() { return rendimento; }
    public void setRendimento(double rendimento) { this.rendimento = rendimento; }
}