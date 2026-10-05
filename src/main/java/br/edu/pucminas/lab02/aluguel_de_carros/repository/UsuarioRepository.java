package br.edu.pucminas.lab02.aluguel_de_carros.repository;

import br.edu.pucminas.lab02.aluguel_de_carros.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
