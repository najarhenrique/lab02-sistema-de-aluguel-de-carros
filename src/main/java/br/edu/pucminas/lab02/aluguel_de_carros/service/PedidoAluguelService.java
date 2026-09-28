package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.PedidoCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Automovel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.PedidoAluguel;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.AutomovelRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.PedidoAluguelRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoAluguelService {

    private final PedidoAluguelRepository repository;
    private final AutomovelRepository automovelRepository;
    private final ClienteService clienteService;

    public PedidoAluguelService(PedidoAluguelRepository repository, AutomovelRepository automovelRepository,
                                ClienteService clienteService) {
        this.repository = repository;
        this.automovelRepository = automovelRepository;
        this.clienteService = clienteService;
    }

    @Transactional(readOnly = true)
    public List<PedidoAluguel> listarDoCliente(String clienteId) {
        return repository.findByClienteIdOrderByDataPedidoDesc(clienteId);
    }

    @Transactional(readOnly = true)
    public PedidoAluguel buscarDoCliente(String id, String clienteId) {
        return repository.findByIdAndClienteId(id, clienteId).orElseThrow(() -> new PedidoNotFoundException(id));
    }

    @Transactional
    public PedidoAluguel criar(String clienteId, PedidoCreateRequest dados) {
        PedidoAluguel pedido = new PedidoAluguel();
        pedido.setCliente(clienteService.buscar(clienteId));
        pedido.setAutomovel(buscarOuCriarAutomovel(dados));
        pedido.setModalidade(dados.modalidade());
        return repository.save(pedido);
    }

    private Automovel buscarOuCriarAutomovel(PedidoCreateRequest dados) {
        String placa = dados.placa().trim().toUpperCase();
        return automovelRepository.findByPlaca(placa).orElseGet(() -> {
            Automovel automovel = new Automovel();
            automovel.setPlaca(placa);
            automovel.setAno(dados.ano());
            automovel.setMarca(dados.marca());
            automovel.setModelo(dados.modelo());
            return automovelRepository.save(automovel);
        });
    }

    public static class PedidoNotFoundException extends RuntimeException {
        public PedidoNotFoundException(String id) { super("Pedido nao encontrado: " + id); }
    }
}
