import { useState } from 'react'
import Login from './pages/Login.jsx'
import Cadastro from './pages/Cadastro.jsx'
import Pedidos from './pages/Pedidos.jsx'

const STORAGE_KEY = 'cliente'

function carregarCliente() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY))
  } catch {
    return null
  }
}

export default function App() {
  const [cliente, setCliente] = useState(carregarCliente)
  const [tela, setTela] = useState('login')

  function entrar(dados) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(dados))
    setCliente(dados)
  }

  function sair() {
    localStorage.removeItem(STORAGE_KEY)
    setCliente(null)
    setTela('login')
  }

  return (
    <main className="container">
      <header className="topo">
        <h1>Aluguel de Carros</h1>
        {cliente && (
          <div>
            <span>{cliente.nome}</span>
            <button className="link" onClick={sair}>Sair</button>
          </div>
        )}
      </header>

      {cliente ? (
        <Pedidos cliente={cliente} />
      ) : tela === 'login' ? (
        <Login onLogin={entrar} onCadastrar={() => setTela('cadastro')} />
      ) : (
        <Cadastro onCadastrado={entrar} onVoltar={() => setTela('login')} />
      )}
    </main>
  )
}
