export const MODALIDADES = { LOCACAO: 'Locação', ASSINATURA: 'Assinatura', LEASING: 'Leasing' }

export const STATUS = {
  PENDENTE: 'Pendente',
  AVALIADO_APROVADO: 'Aprovado',
  AVALIADO_REPROVADO: 'Reprovado',
  CANCELADO: 'Cancelado',
}

export const STATUS_CONTRATO = {
  RASCUNHO: 'Aguardando cliente',
  EM_EXECUCAO: 'Em execução',
  ENCERRADO: 'Encerrado',
}

export const TIPOS = { CLIENTE: 'Cliente', EMPRESA: 'Empresa', BANCO: 'Banco' }

export const moeda = (valor) => valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
export const data = (valor) => new Date(valor).toLocaleDateString('pt-BR', { timeZone: 'UTC' })
export const dataHora = (valor) => new Date(valor).toLocaleString('pt-BR')
