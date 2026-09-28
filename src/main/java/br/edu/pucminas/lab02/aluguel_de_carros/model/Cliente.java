package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @NotBlank(message = "RG e obrigatorio")
    private String rg;

    @NotBlank(message = "CPF e obrigatorio")
    private String cpf;

    @NotBlank(message = "Endereco e obrigatorio")
    private String endereco;

    private String profissao;

    @Valid
    @Size(max = 3, message = "Informe no maximo 3 empregadoras")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "rendimentos_empregadoras", joinColumns = @JoinColumn(name = "cliente_id"))
    private List<RendimentoEmpregadora> rendimentos = new ArrayList<>();

    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public List<RendimentoEmpregadora> getRendimentos() { return rendimentos; }
    public void setRendimentos(List<RendimentoEmpregadora> rendimentos) { this.rendimentos = rendimentos; }
}