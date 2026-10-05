import { useState } from 'react'

export default function CreditoForm({ onSubmit }) {
  const [form, setForm] = useState({ valorFinanciado: '', taxaJuros: '' })
  const [erro, setErro] = useState('')

  const campo = (nome) => ({
    value: form[nome],
    onChange: (e) => setForm({ ...form, [nome]: e.target.value }),
  })

  async function enviar(event) {
    event.preventDefault()
    setErro('')
    try {
      await onSubmit({ valorFinanciado: Number(form.valorFinanciado), taxaJuros: Number(form.taxaJuros) })
    } catch (e) {
      setErro(e.message)
    }
  }

  return (
    <form className="formulario" onSubmit={enviar}>
      <h3>Conceder crédito (leasing)</h3>
      <div className="grade">
        <label>Valor financiado (R$)
          <input type="number" min="0.01" step="0.01" {...campo('valorFinanciado')} required />
        </label>
        <label>Taxa de juros (%)
          <input type="number" min="0" step="0.01" {...campo('taxaJuros')} required />
        </label>
      </div>
      {erro && <p className="erro">{erro}</p>}
      <button type="submit">Conceder crédito</button>
    </form>
  )
}
