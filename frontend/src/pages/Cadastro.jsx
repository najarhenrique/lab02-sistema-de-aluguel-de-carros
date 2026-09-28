import { useState } from 'react'
import { api } from '../api.js'

const VAZIO = { nome: '', email: '', senha: '', rg: '', cpf: '', endereco: '', profissao: '' }
const RENDIMENTOS_VAZIOS = [0, 1, 2].map(() => ({ nomeEmpregadora: '', rendimento: '' }))

export default function Cadastro({ onCadastrado, onVoltar }) {
  const [form, setForm] = useState(VAZIO)
  const [rendimentos, setRendimentos] = useState(RENDIMENTOS_VAZIOS)
  const [erro, setErro] = useState('')

  const campo = (nome) => ({
    value: form[nome],
    onChange: (e) => setForm({ ...form, [nome]: e.target.value }),
  })

  function alterarRendimento(indice, nome, valor) {
    setRendimentos(rendimentos.map((r, i) => (i === indice ? { ...r, [nome]: valor } : r)))
  }

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    try {
      const preenchidos = rendimentos
        .filter((r) => r.nomeEmpregadora.trim())
        .map((r) => ({ nomeEmpregadora: r.nomeEmpregadora, rendimento: Number(r.rendimento) || 0 }))
      onCadastrado(await api.cadastrar({ ...form, rendimentos: preenchidos }))
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <form className="card" onSubmit={enviar}>
      <h2>Cadastro de cliente</h2>
      <div className="grade">
        <label>Nome<input {...campo('nome')} required /></label>
        <label>E-mail<input {...campo('email')} required /></label>
        <label>Senha<input type="password" {...campo('senha')} required /></label>
        <label>RG<input {...campo('rg')} required /></label>
        <label>CPF<input {...campo('cpf')} placeholder="000.000.000-00" required /></label>
        <label>Profissão<input {...campo('profissao')} /></label>
      </div>
      <label>Endereço<input {...campo('endereco')} required /></label>

      <h3>Rendimentos (até 3)</h3>
      {rendimentos.map((r, i) => (
        <div className="grade" key={i}>
          <input placeholder="Empregadora" value={r.nomeEmpregadora}
            onChange={(e) => alterarRendimento(i, 'nomeEmpregadora', e.target.value)} />
          <input type="number" min="0" step="0.01" placeholder="Rendimento (R$)" value={r.rendimento}
            onChange={(e) => alterarRendimento(i, 'rendimento', e.target.value)} />
        </div>
      ))}

      {erro && <p className="erro">{erro}</p>}
      <button type="submit">Cadastrar</button>
      <button type="button" className="link" onClick={onVoltar}>Já tenho conta</button>
    </form>
  )
}
