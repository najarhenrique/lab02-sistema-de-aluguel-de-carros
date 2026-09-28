import { useEffect, useState } from 'react'
import { api } from '../api.js'

const MODALIDADES = { LOCACAO: 'Locação', ASSINATURA: 'Assinatura', LEASING: 'Leasing' }
const STATUS = {
  PENDENTE: 'Pendente',
  AVALIADO_APROVADO: 'Aprovado',
  AVALIADO_REPROVADO: 'Reprovado',
  CANCELADO: 'Cancelado',
}
const VAZIO = { placa: '', ano: '', marca: '', modelo: '', modalidade: 'LOCACAO' }

export default function Pedidos({ cliente }) {
  const [pedidos, setPedidos] = useState([])
  const [form, setForm] = useState(VAZIO)
  const [erro, setErro] = useState('')

  useEffect(() => {
    api.listarPedidos(cliente.id).then(setPedidos).catch((e) => setErro(e.message))
  }, [cliente.id])

  const campo = (nome) => ({
    value: form[nome],
    onChange: (e) => setForm({ ...form, [nome]: e.target.value }),
  })

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    try {
      const novo = await api.criarPedido(cliente.id, { ...form, ano: Number(form.ano) })
      setPedidos([novo, ...pedidos])
      setForm(VAZIO)
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <>
      <form className="card" onSubmit={enviar}>
        <h2>Novo pedido de aluguel</h2>
        <div className="grade">
          <label>Placa<input {...campo('placa')} placeholder="ABC1D23" required /></label>
          <label>Ano<input type="number" min="1900" {...campo('ano')} required /></label>
          <label>Marca<input {...campo('marca')} required /></label>
          <label>Modelo<input {...campo('modelo')} required /></label>
        </div>
        <label>Modalidade
          <select {...campo('modalidade')}>
            {Object.entries(MODALIDADES).map(([valor, rotulo]) => (
              <option key={valor} value={valor}>{rotulo}</option>
            ))}
          </select>
        </label>
        {erro && <p className="erro">{erro}</p>}
        <button type="submit">Criar pedido</button>
      </form>

      <section className="card">
        <h2>Meus pedidos</h2>
        {pedidos.length === 0 ? (
          <p className="vazio">Nenhum pedido ainda.</p>
        ) : (
          <div className="tabela">
            <table>
              <thead>
                <tr><th>Data</th><th>Automóvel</th><th>Placa</th><th>Modalidade</th><th>Status</th></tr>
              </thead>
              <tbody>
                {pedidos.map((p) => (
                  <tr key={p.id}>
                    <td>{new Date(p.dataPedido).toLocaleString('pt-BR')}</td>
                    <td>{p.automovel.marca} {p.automovel.modelo} ({p.automovel.ano})</td>
                    <td>{p.automovel.placa}</td>
                    <td>{MODALIDADES[p.modalidade]}</td>
                    <td><span className={`status ${p.status}`}>{STATUS[p.status]}</span></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>
    </>
  )
}
