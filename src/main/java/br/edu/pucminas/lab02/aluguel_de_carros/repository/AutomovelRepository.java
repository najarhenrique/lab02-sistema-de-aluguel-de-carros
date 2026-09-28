package br.edu.pucminas.lab02.aluguel_de_carros.repository;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Automovel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutomovelRepository extends JpaRepository<Automovel, String> {
    Optional<Automovel> findByPlaca(String placa);
}
