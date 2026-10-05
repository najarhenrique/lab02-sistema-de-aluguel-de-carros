import { useState } from 'react'
import { api } from '../api.js'

export default function Login({ onLogin, onCadastrar, onCadastrarAgente }) {
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [erro, setErro] = useState('')

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    try {
      onLogin(await api.login(email, senha))
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <form className="card" onSubmit={enviar}>
      <h2>Entrar</h2>
      <label>E-mail<input value={email} onChange={(e) => setEmail(e.target.value)} required /></label>
      <label>Senha<input type="password" value={senha} onChange={(e) => setSenha(e.target.value)} required /></label>
      {erro && <p className="erro">{erro}</p>}
      <button type="submit">Entrar</button>
      <button type="button" className="link" onClick={onCadastrar}>Sou cliente: cadastre-se</button>
      <button type="button" className="link" onClick={onCadastrarAgente}>Sou empresa ou banco: cadastre-se</button>
    </form>
  )
}
