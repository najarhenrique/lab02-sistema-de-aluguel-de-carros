package br.edu.pucminas.lab02.aluguel_de_carros.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "bancos")
public class Banco extends Agente {

    @Override
    public TipoUsuario getTipo() { return TipoUsuario.BANCO; }
}
