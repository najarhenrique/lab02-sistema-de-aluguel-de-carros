async function request(path, { method = 'GET', body, clienteId, agenteId } = {}) {
  const headers = { 'Content-Type': 'application/json' }
  if (clienteId) headers['X-Cliente-Id'] = clienteId
  if (agenteId) headers['X-Agente-Id'] = agenteId

  const response = await fetch(`/api${path}`, {
    method,
    headers,
    body: body ? JSON.stringify(body) : undefined,
  })
  const data = response.status === 204 ? null : await response.json().catch(() => null)

  if (!response.ok) {
    const campos = data?.campos ? Object.values(data.campos) : []
    throw new Error([data?.mensagem ?? 'Erro na requisição', ...campos].join(' — '))
  }
  return data
}

export const api = {
  login: (email, senha) => request('/auth/login', { method: 'POST', body: { email, senha } }),
  cadastrar: (cliente) => request('/clientes', { method: 'POST', body: cliente }),
  listarAgentes: () => request('/agentes'),
  cadastrarAgente: (agente) => request('/agentes', { method: 'POST', body: agente }),

  // cliente
  listarPedidos: (clienteId) => request('/pedidos', { clienteId }),
  criarPedido: (clienteId, pedido) => request('/pedidos', { method: 'POST', body: pedido, clienteId }),
  alterarPedido: (clienteId, id, pedido) => request(`/pedidos/${id}`, { method: 'PUT', body: pedido, clienteId }),
  cancelarPedido: (clienteId, id) => request(`/pedidos/${id}/cancelar`, { method: 'POST', clienteId }),
  aceitarContrato: (clienteId, id) => request(`/pedidos/${id}/contrato/aceitar`, { method: 'POST', clienteId }),
  recusarContrato: (clienteId, id) => request(`/pedidos/${id}/contrato/recusar`, { method: 'POST', clienteId }),

  // agente (empresa ou banco)
  listarPedidosAgente: (agenteId, status) =>
    request(`/agente/pedidos${status ? `?status=${status}` : ''}`, { agenteId }),
  emitirParecer: (agenteId, id, parecer) =>
    request(`/agente/pedidos/${id}/parecer`, { method: 'POST', body: parecer, agenteId }),
  concederCredito: (agenteId, id, credito) =>
    request(`/agente/pedidos/${id}/credito`, { method: 'POST', body: credito, agenteId }),
}
