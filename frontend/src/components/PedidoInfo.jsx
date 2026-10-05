import { MODALIDADES, STATUS, STATUS_CONTRATO, TIPOS, data, dataHora, moeda } from '../constants.js'

export default function PedidoInfo({ pedido }) {
  const { automovel, parecer, contrato } = pedido

  return (
    <>
      <div className="pedido-topo">
        <strong>{automovel.marca} {automovel.modelo} ({automovel.ano})</strong>
        <span className={`status ${pedido.status}`}>{STATUS[pedido.status]}</span>
      </div>
      <p className="vazio">
        {automovel.placa} · {MODALIDADES[pedido.modalidade]} · {dataHora(pedido.dataPedido)}
      </p>
      {automovel.proprietarioNome && (
        <p className="vazio">Proprietário: {automovel.proprietarioNome} ({TIPOS[automovel.proprietarioTipo]})</p>
      )}

      {parecer && (
        <div className="detalhes">
          <h3>Parecer financeiro</h3>
          <p>
            {parecer.aprovado ? 'Aprovado' : 'Reprovado'} por {parecer.agente} em {dataHora(parecer.dataAvaliacao)}
          </p>
          <p>{parecer.parecer}</p>
        </div>
      )}

      {contrato && (
        <div className="detalhes">
          <h3>
            Contrato <span className={`status ${contrato.status}`}>{STATUS_CONTRATO[contrato.status]}</span>
          </h3>
          <p>Valor mensal: {moeda(contrato.valorMensal)}</p>
          <p>Período: {data(contrato.dataInicio)} a {data(contrato.dataFim)}</p>
          {parecer?.necessitaCredito && !contrato.credito && <p>Aguardando concessão de crédito pelo banco.</p>}
          {contrato.credito && (
            <p>
              Crédito de {contrato.credito.banco}: {moeda(contrato.credito.valorFinanciado)} a{' '}
              {contrato.credito.taxaJuros}% de juros
            </p>
          )}
        </div>
      )}
    </>
  )
}
