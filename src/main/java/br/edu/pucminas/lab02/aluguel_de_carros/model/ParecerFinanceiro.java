package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "pareceres_financeiros")
public class ParecerFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private boolean aprovado;

    @Column(length = 1000)
    private String parecer;

    private LocalDateTime dataAvaliacao = LocalDateTime.now();

    private boolean necessitaCredito;

    @ManyToOne(optional = false)
    private Agente agente;

    public String getId() { return id; }
    public boolean isAprovado() { return aprovado; }
    public void setAprovado(boolean aprovado) { this.aprovado = aprovado; }
    public String getParecer() { return parecer; }
    public void setParecer(String parecer) { this.parecer = parecer; }
    public LocalDateTime getDataAvaliacao() { return dataAvaliacao; }
    public boolean isNecessitaCredito() { return necessitaCredito; }
    public void setNecessitaCredito(boolean necessitaCredito) { this.necessitaCredito = necessitaCredito; }
    public Agente getAgente() { return agente; }
    public void setAgente(Agente agente) { this.agente = agente; }
}
