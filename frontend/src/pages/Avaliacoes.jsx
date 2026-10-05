import { useEffect, useState } from 'react'
import { api } from '../api.js'
import CreditoForm from '../components/CreditoForm.jsx'
import ParecerForm from '../components/ParecerForm.jsx'
import PedidoInfo from '../components/PedidoInfo.jsx'
import { STATUS, moeda } from '../constants.js'

function DadosCliente({ cliente }) {
  const renda = cliente.rendimentos.reduce((total, r) => total + r.rendimento, 0)
  return (
    <div className="detalhes">
      <h3>Cliente</h3>
      <p>{cliente.nome} · CPF {cliente.cpf} · RG {cliente.rg}</p>
      <p>{cliente.endereco}{cliente.profissao && ` · ${cliente.profissao}`}</p>
      {cliente.rendimentos.length === 0 ? (
        <p>Nenhum rendimento informado.</p>
      ) : (
        <p>
          {cliente.rendimentos.map((r) => `${r.nomeEmpregadora}: ${moeda(r.rendimento)}`).join(' · ')}
          {' '}(total {moeda(renda)})
        </p>
      )}
    </div>
  )
}

export default function Avaliacoes({ usuario }) {
  const [itens, setItens] = useState([])
  const [filtro, setFiltro] = useState('PENDENTE')
  const [versao, setVersao] = useState(0)
  const [erro, setErro] = useState('')

  useEffect(() => {
    api.listarPedidosAgente(usuario.id, filtro).then(setItens).catch((e) => setErro(e.message))
  }, [usuario.id, filtro, versao])

  async function enviar(acao) {
    await acao()
    setVersao(versao + 1)
  }

  return (
    <section className="card">
      <h2>Pedidos de aluguel</h2>
      <label>Status
        <select value={filtro} onChange={(e) => setFiltro(e.target.value)}>
          <option value="">Todos</option>
          {Object.entries(STATUS).map(([valor, nome]) => <option key={valor} value={valor}>{nome}</option>)}
        </select>
      </label>
      {erro && <p className="erro">{erro}</p>}

      {itens.length === 0 ? (
        <p className="vazio">Nenhum pedido encontrado.</p>
      ) : (
        itens.map(({ pedido, cliente }) => {
          const aguardaCredito = usuario.tipo === 'BANCO' && pedido.status === 'AVALIADO_APROVADO'
            && pedido.contrato?.status === 'RASCUNHO' && pedido.parecer?.necessitaCredito && !pedido.contrato.credito
          return (
            <article className="pedido" key={pedido.id}>
              <PedidoInfo pedido={pedido} />
              <DadosCliente cliente={cliente} />
              {pedido.status === 'PENDENTE' && (
                <ParecerForm
                  modalidade={pedido.modalidade}
                  onSubmit={(parecer) => enviar(() => api.emitirParecer(usuario.id, pedido.id, parecer))}
                />
              )}
              {aguardaCredito && (
                <CreditoForm onSubmit={(credito) => enviar(() => api.concederCredito(usuario.id, pedido.id, credito))} />
              )}
            </article>
          )
        })
      )}
    </section>
  )
}
