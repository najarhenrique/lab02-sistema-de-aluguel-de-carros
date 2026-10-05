import { useState } from 'react'
import { api } from '../api.js'

const VAZIO = { tipo: 'EMPRESA', nome: '', email: '', senha: '', cnpj: '', razaoSocial: '' }

export default function CadastroAgente({ onCadastrado, onVoltar }) {
  const [form, setForm] = useState(VAZIO)
  const [erro, setErro] = useState('')

  const campo = (nome) => ({
    value: form[nome],
    onChange: (e) => setForm({ ...form, [nome]: e.target.value }),
  })

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    try {
      onCadastrado(await api.cadastrarAgente(form))
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <form className="card" onSubmit={enviar}>
      <h2>Cadastro de agente</h2>
      <label>Tipo
        <select {...campo('tipo')}>
          <option value="EMPRESA">Empresa</option>
          <option value="BANCO">Banco</option>
        </select>
      </label>
      <div className="grade">
        <label>Nome<input {...campo('nome')} required /></label>
        <label>Razão social<input {...campo('razaoSocial')} required /></label>
        <label>CNPJ<input {...campo('cnpj')} placeholder="00.000.000/0000-00" required /></label>
        <label>E-mail<input {...campo('email')} required /></label>
        <label>Senha<input type="password" {...campo('senha')} required /></label>
      </div>
      {erro && <p className="erro">{erro}</p>}
      <button type="submit">Cadastrar</button>
      <button type="button" className="link" onClick={onVoltar}>Já tenho conta</button>
    </form>
  )
}
