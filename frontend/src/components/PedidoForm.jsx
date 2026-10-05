import { useEffect, useState } from 'react'
import { api } from '../api.js'
import { MODALIDADES, TIPOS } from '../constants.js'

export default function PedidoForm({ usuario, titulo, rotulo, inicial, limpar = false, onSubmit, onCancelar }) {
  const vazio = { placa: '', ano: '', marca: '', modelo: '', modalidade: 'LOCACAO', proprietarioId: usuario.id }
  const [form, setForm] = useState(inicial ?? vazio)
  const [agentes, setAgentes] = useState([])
  const [erro, setErro] = useState('')

  useEffect(() => {
    api.listarAgentes().then(setAgentes).catch((e) => setErro(e.message))
  }, [])

  const campo = (nome) => ({
    value: form[nome],
    onChange: (e) => setForm({ ...form, [nome]: e.target.value }),
  })

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    try {
      await onSubmit({
        placa: form.placa,
        ano: Number(form.ano),
        marca: form.marca,
        modelo: form.modelo,
        modalidade: form.modalidade,
        proprietarioId: form.proprietarioId,
      })
      if (limpar) setForm(vazio)
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <form className="formulario" onSubmit={enviar}>
      {titulo && <h2>{titulo}</h2>}
      <div className="grade">
        <label>Placa<input {...campo('placa')} placeholder="ABC1D23" required /></label>
        <label>Ano<input type="number" min="1900" {...campo('ano')} required /></label>
        <label>Marca<input {...campo('marca')} required /></label>
        <label>Modelo<input {...campo('modelo')} required /></label>
      </div>
      <div className="grade">
        <label>Modalidade
          <select {...campo('modalidade')}>
            {Object.entries(MODALIDADES).map(([valor, nome]) => (
              <option key={valor} value={valor}>{nome}</option>
            ))}
          </select>
        </label>
        <label>Proprietário do automóvel
          <select {...campo('proprietarioId')}>
            <option value={usuario.id}>{usuario.nome} (Cliente)</option>
            {agentes.map((a) => (
              <option key={a.id} value={a.id}>{a.nome} ({TIPOS[a.tipo]})</option>
            ))}
          </select>
        </label>
      </div>
      {erro && <p className="erro">{erro}</p>}
      <div className="acoes">
        <button type="submit">{rotulo}</button>
        {onCancelar && <button type="button" className="secundario" onClick={onCancelar}>Voltar</button>}
      </div>
    </form>
  )
}
