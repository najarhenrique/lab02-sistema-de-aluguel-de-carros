import { useEffect, useState } from 'react'
import { api } from '../api.js'
import PedidoForm from '../components/PedidoForm.jsx'
import PedidoInfo from '../components/PedidoInfo.jsx'

export default function Pedidos({ usuario }) {
  const [pedidos, setPedidos] = useState([])
  const [editando, setEditando] = useState(null)
  const [erro, setErro] = useState('')

  useEffect(() => {
    api.listarPedidos(usuario.id).then(setPedidos).catch((e) => setErro(e.message))
  }, [usuario.id])

  const substituir = (atualizado) =>
    setPedidos(pedidos.map((p) => (p.id === atualizado.id ? atualizado : p)))

  async function executar(acao, confirmacao) {
    if (confirmacao && !window.confirm(confirmacao)) return
    setErro('')
    try {
      substituir(await acao())
    } catch (e) {
      setErro(e.message)
    }
  }

  async function criar(dados) {
    const novo = await api.criarPedido(usuario.id, dados)
    setPedidos([novo, ...pedidos])
  }

  async function alterar(pedido, dados) {
    substituir(await api.alterarPedido(usuario.id, pedido.id, dados))
    setEditando(null)
  }

  function acoes(p) {
    if (p.status === 'PENDENTE') {
      return (
        <div className="acoes">
          <button className="secundario" onClick={() => setEditando(p.id)}>Alterar</button>
          <button className="perigo" onClick={() =>
            executar(() => api.cancelarPedido(usuario.id, p.id), 'Cancelar este pedido?')}>Cancelar</button>
        </div>
      )
    }
    if (p.status === 'AVALIADO_APROVADO' && p.contrato?.status === 'RASCUNHO') {
      const semCredito = p.parecer.necessitaCredito && !p.contrato.credito
      return (
        <div className="acoes">
          <button disabled={semCredito} onClick={() =>
            executar(() => api.aceitarContrato(usuario.id, p.id), 'Aceitar o contrato?')}>Aceitar contrato</button>
          <button className="perigo" onClick={() =>
            executar(() => api.recusarContrato(usuario.id, p.id), 'Recusar o contrato? O pedido será cancelado.')}>
            Recusar contrato
          </button>
        </div>
      )
    }
    return null
  }

  return (
    <>
      <section className="card">
        <PedidoForm usuario={usuario} titulo="Novo pedido de aluguel" rotulo="Criar pedido" limpar onSubmit={criar} />
      </section>

      <section className="card">
        <h2>Meus pedidos</h2>
        {erro && <p className="erro">{erro}</p>}
        {pedidos.length === 0 ? (
          <p className="vazio">Nenhum pedido ainda.</p>
        ) : (
          pedidos.map((p) => (
            <article className="pedido" key={p.id}>
              {editando === p.id ? (
                <PedidoForm
                  usuario={usuario}
                  rotulo="Salvar alterações"
                  inicial={{ ...p.automovel, modalidade: p.modalidade }}
                  onSubmit={(dados) => alterar(p, dados)}
                  onCancelar={() => setEditando(null)}
                />
              ) : (
                <>
                  <PedidoInfo pedido={p} />
                  {acoes(p)}
                </>
              )}
            </article>
          ))
        )}
      </section>
    </>
  )
}
