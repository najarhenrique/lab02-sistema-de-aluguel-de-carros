import { useState } from 'react'
import Login from './pages/Login.jsx'
import Cadastro from './pages/Cadastro.jsx'
import CadastroAgente from './pages/CadastroAgente.jsx'
import Pedidos from './pages/Pedidos.jsx'
import Avaliacoes from './pages/Avaliacoes.jsx'
import { TIPOS } from './constants.js'

const STORAGE_KEY = 'usuario'

function carregarUsuario() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE_KEY))
  } catch {
    return null
  }
}

export default function App() {
  const [usuario, setUsuario] = useState(carregarUsuario)
  const [tela, setTela] = useState('login')

  function entrar(dados) {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(dados))
    setUsuario(dados)
  }

  function sair() {
    localStorage.removeItem(STORAGE_KEY)
    setUsuario(null)
    setTela('login')
  }

  return (
    <main className="container">
      <header className="topo">
        <h1>Aluguel de Carros</h1>
        {usuario && (
          <div>
            <span>{usuario.nome} ({TIPOS[usuario.tipo]})</span>
            <button className="link" onClick={sair}>Sair</button>
          </div>
        )}
      </header>

      {usuario ? (
        usuario.tipo === 'CLIENTE' ? <Pedidos usuario={usuario} /> : <Avaliacoes usuario={usuario} />
      ) : tela === 'login' ? (
        <Login onLogin={entrar} onCadastrar={() => setTela('cadastro')} onCadastrarAgente={() => setTela('cadastro-agente')} />
      ) : tela === 'cadastro' ? (
        <Cadastro onCadastrado={(cliente) => entrar({ ...cliente, tipo: 'CLIENTE' })} onVoltar={() => setTela('login')} />
      ) : (
        <CadastroAgente onCadastrado={entrar} onVoltar={() => setTela('login')} />
      )}
    </main>
  )
}
