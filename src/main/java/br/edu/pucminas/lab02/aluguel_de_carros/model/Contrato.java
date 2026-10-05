package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "contratos")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private double valorMensal;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean assinadoCliente;

    @Enumerated(EnumType.STRING)
    private StatusContrato status = StatusContrato.RASCUNHO;

    @OneToOne(cascade = CascadeType.ALL)
    private ContratoCredito credito;

    public String getId() { return id; }
    public double getValorMensal() { return valorMensal; }
    public void setValorMensal(double valorMensal) { this.valorMensal = valorMensal; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public boolean isAssinadoCliente() { return assinadoCliente; }
    public void setAssinadoCliente(boolean assinadoCliente) { this.assinadoCliente = assinadoCliente; }
    public StatusContrato getStatus() { return status; }
    public void setStatus(StatusContrato status) { this.status = status; }
    public ContratoCredito getCredito() { return credito; }
    public void setCredito(ContratoCredito credito) { this.credito = credito; }
}
