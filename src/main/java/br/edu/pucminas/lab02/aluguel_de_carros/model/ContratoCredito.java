package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contratos_credito")
public class ContratoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private double valorFinanciado;
    private double taxaJuros;

    @ManyToOne(optional = false)
    private Banco banco;

    public String getId() { return id; }
    public double getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(double valorFinanciado) { this.valorFinanciado = valorFinanciado; }
    public double getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(double taxaJuros) { this.taxaJuros = taxaJuros; }
    public Banco getBanco() { return banco; }
    public void setBanco(Banco banco) { this.banco = banco; }
}
