package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.PedidoCreateRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Automovel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Contrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.PedidoAluguel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusContrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusPedido;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.AutomovelRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.PedidoAluguelRepository;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoAluguelService {

    private static final String FORMATO_PLACA = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}";
    private static final List<StatusPedido> STATUS_EM_ANDAMENTO =
            List.of(StatusPedido.PENDENTE, StatusPedido.AVALIADO_APROVADO);

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
        pedido.setAutomovel(buscarOuCriarAutomovel(dados, null));
        pedido.setModalidade(dados.modalidade());
        return repository.save(pedido);
    }

    @Transactional
    public PedidoAluguel alterar(String id, String clienteId, PedidoCreateRequest dados) {
        PedidoAluguel pedido = buscarDoCliente(id, clienteId);
        exigirPendente(pedido, "alterado");
        pedido.setAutomovel(buscarOuCriarAutomovel(dados, pedido.getId()));
        pedido.setModalidade(dados.modalidade());
        return repository.save(pedido);
    }

    @Transactional
    public PedidoAluguel cancelar(String id, String clienteId) {
        PedidoAluguel pedido = buscarDoCliente(id, clienteId);
        exigirPendente(pedido, "cancelado");
        pedido.setStatus(StatusPedido.CANCELADO);
        return repository.save(pedido);
    }

    @Transactional
    public PedidoAluguel aceitarContrato(String id, String clienteId) {
        PedidoAluguel pedido = buscarDoCliente(id, clienteId);
        Contrato contrato = contratoAguardandoDecisao(pedido);
        if (pedido.getParecer().isNecessitaCredito() && contrato.getCredito() == null) {
            throw new OperacaoNaoPermitidaException("Aguardando a concessao de credito pelo banco");
        }
        contrato.setAssinadoCliente(true);
        contrato.setStatus(StatusContrato.EM_EXECUCAO);
        return repository.save(pedido);
    }

    @Transactional
    public PedidoAluguel recusarContrato(String id, String clienteId) {
        PedidoAluguel pedido = buscarDoCliente(id, clienteId);
        Contrato contrato = contratoAguardandoDecisao(pedido);
        contrato.setStatus(StatusContrato.ENCERRADO);
        pedido.setStatus(StatusPedido.CANCELADO);
        return repository.save(pedido);
    }

    private void exigirPendente(PedidoAluguel pedido, String acao) {
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new OperacaoNaoPermitidaException("Pedido so pode ser " + acao + " enquanto estiver pendente");
        }
    }

    private Contrato contratoAguardandoDecisao(PedidoAluguel pedido) {
        Contrato contrato = pedido.getContrato();
        if (pedido.getStatus() != StatusPedido.AVALIADO_APROVADO || contrato == null
                || contrato.getStatus() != StatusContrato.RASCUNHO) {
            throw new OperacaoNaoPermitidaException("Pedido nao possui contrato aguardando decisao");
        }
        return contrato;
    }

    private Automovel buscarOuCriarAutomovel(PedidoCreateRequest dados, String pedidoIgnorado) {
        String placa = dados.placa().replaceAll("[-\\s]", "").toUpperCase();
        if (!placa.matches(FORMATO_PLACA)) {
            throw new PedidoInvalidoException("Placa invalida. Use o formato ABC1234 ou ABC1D23");
        }
        int anoMaximo = Year.now().getValue() + 1;
        if (dados.ano() > anoMaximo) {
            throw new PedidoInvalidoException("Ano do automovel nao pode ser maior que " + anoMaximo);
        }

        Optional<Automovel> existente = automovelRepository.findByPlaca(placa);
        if (existente.isPresent()) {
            Automovel automovel = existente.get();
            if (automovel.getAno() != dados.ano()
                    || !automovel.getMarca().equalsIgnoreCase(dados.marca().trim())
                    || !automovel.getModelo().equalsIgnoreCase(dados.modelo().trim())) {
                throw new PedidoInvalidoException("Placa " + placa + " ja cadastrada para "
                        + automovel.getMarca() + " " + automovel.getModelo() + " " + automovel.getAno());
            }
            boolean ocupado = pedidoIgnorado == null
                    ? repository.existsByAutomovelIdAndStatusIn(automovel.getId(), STATUS_EM_ANDAMENTO)
                    : repository.existsByAutomovelIdAndStatusInAndIdNot(automovel.getId(), STATUS_EM_ANDAMENTO,
                            pedidoIgnorado);
            if (ocupado) {
                throw new AutomovelIndisponivelException(placa);
            }
            return automovel;
        }

        Automovel automovel = new Automovel();
        automovel.setPlaca(placa);
        automovel.setAno(dados.ano());
        automovel.setMarca(dados.marca().trim());
        automovel.setModelo(dados.modelo().trim());
        return automovelRepository.save(automovel);
    }

    public static class PedidoNotFoundException extends RuntimeException {
        public PedidoNotFoundException(String id) { super("Pedido nao encontrado: " + id); }
    }

    public static class PedidoInvalidoException extends RuntimeException {
        public PedidoInvalidoException(String mensagem) { super(mensagem); }
    }

    public static class OperacaoNaoPermitidaException extends RuntimeException {
        public OperacaoNaoPermitidaException(String mensagem) { super(mensagem); }
    }

    public static class AutomovelIndisponivelException extends RuntimeException {
        public AutomovelIndisponivelException(String placa) {
            super("Automovel " + placa + " ja possui um pedido em andamento");
        }
    }
}
