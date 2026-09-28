async function request(path, { method = 'GET', body, clienteId } = {}) {
  const headers = { 'Content-Type': 'application/json' }
  if (clienteId) headers['X-Cliente-Id'] = clienteId

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
  listarPedidos: (clienteId) => request('/pedidos', { clienteId }),
  criarPedido: (clienteId, pedido) => request('/pedidos', { method: 'POST', body: pedido, clienteId }),
}
