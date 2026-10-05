package br.edu.pucminas.lab02.aluguel_de_carros.repository;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, String> {
}
