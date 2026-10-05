package br.edu.pucminas.lab02.aluguel_de_carros.service;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.CreditoRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.dto.ParecerRequest;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Agente;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Banco;
import br.edu.pucminas.lab02.aluguel_de_carros.model.Contrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.ContratoCredito;
import br.edu.pucminas.lab02.aluguel_de_carros.model.ModalidadeContrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.ParecerFinanceiro;
import br.edu.pucminas.lab02.aluguel_de_carros.model.PedidoAluguel;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusContrato;
import br.edu.pucminas.lab02.aluguel_de_carros.model.StatusPedido;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.AgenteRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.BancoRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.repository.PedidoAluguelRepository;
import br.edu.pucminas.lab02.aluguel_de_carros.service.PedidoAluguelService.OperacaoNaoPermitidaException;
import br.edu.pucminas.lab02.aluguel_de_carros.service.PedidoAluguelService.PedidoInvalidoException;
import br.edu.pucminas.lab02.aluguel_de_carros.service.PedidoAluguelService.PedidoNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    private final PedidoAluguelRepository repository;
    private final AgenteRepository agenteRepository;
    private final BancoRepository bancoRepository;

    public AvaliacaoService(PedidoAluguelRepository repository, AgenteRepository agenteRepository,
                            BancoRepository bancoRepository) {
        this.repository = repository;
        this.agenteRepository = agenteRepository;
        this.bancoRepository = bancoRepository;
    }

    @Transactional(readOnly = true)
    public List<PedidoAluguel> listar(String agenteId, StatusPedido status) {
        buscarAgente(agenteId);
        return status == null
                ? repository.findAllByOrderByDataPedidoDesc()
                : repository.findByStatusOrderByDataPedidoDesc(status);
    }

    @Transactional(readOnly = true)
    public PedidoAluguel buscar(String agenteId, String pedidoId) {
        buscarAgente(agenteId);
        return buscarPedido(pedidoId);
    }

    @Transactional
    public PedidoAluguel avaliar(String agenteId, String pedidoId, ParecerRequest dados) {
        Agente agente = buscarAgente(agenteId);
        PedidoAluguel pedido = buscarPedido(pedidoId);
        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new OperacaoNaoPermitidaException("Pedido so pode ser avaliado enquanto estiver pendente");
        }
        boolean aprovado = dados.aprovado();
        if (aprovado && dados.necessitaCredito() && pedido.getModalidade() != ModalidadeContrato.LEASING) {
            throw new PedidoInvalidoException("Credito so se aplica a pedidos de Leasing");
        }

        ParecerFinanceiro parecer = new ParecerFinanceiro();
        parecer.setAgente(agente);
        parecer.setAprovado(aprovado);
        parecer.setParecer(dados.parecer().trim());
        parecer.setNecessitaCredito(aprovado && dados.necessitaCredito());
        pedido.setParecer(parecer);

        if (aprovado) {
            pedido.setContrato(criarContrato(dados));
            pedido.setStatus(StatusPedido.AVALIADO_APROVADO);
        } else {
            pedido.setStatus(StatusPedido.AVALIADO_REPROVADO);
        }
        return repository.save(pedido);
    }

    @Transactional
    public PedidoAluguel concederCredito(String agenteId, String pedidoId, CreditoRequest dados) {
        Banco banco = bancoRepository.findById(agenteId)
                .orElseThrow(() -> new AcessoNegadoException("Apenas bancos podem conceder credito"));
        PedidoAluguel pedido = buscarPedido(pedidoId);
        Contrato contrato = pedido.getContrato();

        if (pedido.getModalidade() != ModalidadeContrato.LEASING) {
            throw new OperacaoNaoPermitidaException("Credito so pode ser concedido para pedidos de Leasing");
        }
        if (pedido.getStatus() != StatusPedido.AVALIADO_APROVADO || contrato == null
                || contrato.getStatus() != StatusContrato.RASCUNHO) {
            throw new OperacaoNaoPermitidaException("Pedido nao possui contrato aguardando decisao do cliente");
        }
        if (!pedido.getParecer().isNecessitaCredito()) {
            throw new OperacaoNaoPermitidaException("O parecer nao indica necessidade de credito");
        }
        if (contrato.getCredito() != null) {
            throw new OperacaoNaoPermitidaException("Credito ja concedido para este pedido");
        }

        ContratoCredito credito = new ContratoCredito();
        credito.setBanco(banco);
        credito.setValorFinanciado(dados.valorFinanciado());
        credito.setTaxaJuros(dados.taxaJuros());
        contrato.setCredito(credito);
        return repository.save(pedido);
    }

    private Contrato criarContrato(ParecerRequest dados) {
        if (dados.valorMensal() == null || dados.valorMensal() <= 0) {
            throw new PedidoInvalidoException("Valor mensal do contrato deve ser maior que zero");
        }
        if (dados.dataInicio() == null || dados.dataFim() == null) {
            throw new PedidoInvalidoException("Periodo do contrato (inicio e fim) e obrigatorio");
        }
        if (!dados.dataFim().isAfter(dados.dataInicio())) {
            throw new PedidoInvalidoException("Data de fim deve ser posterior a data de inicio");
        }
        Contrato contrato = new Contrato();
        contrato.setValorMensal(dados.valorMensal());
        contrato.setDataInicio(dados.dataInicio());
        contrato.setDataFim(dados.dataFim());
        return contrato;
    }

    private Agente buscarAgente(String agenteId) {
        return agenteRepository.findById(agenteId).orElseThrow(AgenteNaoAutenticadoException::new);
    }

    private PedidoAluguel buscarPedido(String id) {
        return repository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
    }

    public static class AgenteNaoAutenticadoException extends RuntimeException {
        public AgenteNaoAutenticadoException() { super("Agente nao autenticado"); }
    }

    public static class AcessoNegadoException extends RuntimeException {
        public AcessoNegadoException(String mensagem) { super(mensagem); }
    }
}
