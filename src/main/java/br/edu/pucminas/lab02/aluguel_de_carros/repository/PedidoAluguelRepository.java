package br.edu.pucminas.lab02.aluguel_de_carros.repository;

import br.edu.pucminas.lab02.aluguel_de_carros.model.PedidoAluguel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusPedido;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoAluguelRepository extends JpaRepository<PedidoAluguel, String> {
    List<PedidoAluguel> findByClienteIdOrderByDataPedidoDesc(String clienteId);
    Optional<PedidoAluguel> findByIdAndClienteId(String id, String clienteId);
    void deleteByClienteId(String clienteId);
    boolean existsByAutomovelIdAndStatusIn(String automovelId, Collection<StatusPedido> status);
}
