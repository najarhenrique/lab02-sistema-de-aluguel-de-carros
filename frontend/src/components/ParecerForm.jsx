import { useState } from 'react'

const VAZIO = { aprovado: 'true', parecer: '', valorMensal: '', dataInicio: '', dataFim: '', necessitaCredito: false }

export default function ParecerForm({ modalidade, onSubmit }) {
  const [form, setForm] = useState(VAZIO)
  const [erro, setErro] = useState('')
  const aprovado = form.aprovado === 'true'

  const campo = (nome) => ({
    value: form[nome],
    onChange: (e) => setForm({ ...form, [nome]: e.target.value }),
  })

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    const corpo = { aprovado, parecer: form.parecer }
    if (aprovado) {
      Object.assign(corpo, {
        valorMensal: Number(form.valorMensal),
        dataInicio: form.dataInicio,
        dataFim: form.dataFim,
        necessitaCredito: modalidade === 'LEASING' && form.necessitaCredito,
      })
    }
    try {
      await onSubmit(corpo)
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <form className="formulario" onSubmit={enviar}>
      <h3>Emitir parecer</h3>
      <label>Resultado
        <select {...campo('aprovado')}>
          <option value="true">Aprovar</option>
          <option value="false">Reprovar</option>
        </select>
      </label>
      <label>Justificativa<textarea {...campo('parecer')} rows="2" required /></label>

      {aprovado && (
        <>
          <div className="grade">
            <label>Valor mensal (R$)
              <input type="number" min="0.01" step="0.01" {...campo('valorMensal')} required />
            </label>
            <label>Início<input type="date" {...campo('dataInicio')} required /></label>
            <label>Fim<input type="date" {...campo('dataFim')} required /></label>
          </div>
          {modalidade === 'LEASING' && (
            <label className="opcao">
              <input type="checkbox" checked={form.necessitaCredito}
                onChange={(e) => setForm({ ...form, necessitaCredito: e.target.checked })} />
              Requer contrato de crédito do banco
            </label>
          )}
        </>
      )}

      {erro && <p className="erro">{erro}</p>}
      <button type="submit">Registrar parecer</button>
    </form>
  )
}
