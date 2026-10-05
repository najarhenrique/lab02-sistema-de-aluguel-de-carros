package br.edu.pucminas.lab02.aluguel_de_carros.repository;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgenteRepository extends JpaRepository<Agente, String> {
    boolean existsByCnpj(String cnpj);
}
