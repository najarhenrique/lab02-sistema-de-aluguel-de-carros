# Histórias do Usuário — Sistema de Aluguel de Carros (Lab 02)

---

## US01 – Cadastro de Cliente (UC01)
**Descrição:**  
Como **Cliente**, quero me cadastrar no sistema informando meus dados pessoais, endereço, profissão e fontes de renda, para que eu possa solicitar pedidos de aluguel de automóveis.

**Critérios de Aceite:**
- O sistema deve exigir o preenchimento de RG, CPF, Nome e Endereço.
- O sistema deve permitir o cadastro de até 3 entidades empregadoras com os respectivos rendimentos auferidos.
- O cadastro só é efetivado se o CPF for válido e não estiver duplicado no sistema.

---

## US02 – Gestão de Pedidos de Aluguel (UC02, UC03, UC04, UC05)
**Descrição:**  
Como **Cliente**, quero criar, consultar, alterar e cancelar meus pedidos de aluguel, para que eu possa gerenciar minhas solicitações antes da avaliação financeira.

**Critérios de Aceite:**
- O pedido deve conter a escolha do automóvel (placa, ano, marca, modelo) e a modalidade de contrato (Locação, Assinatura ou Leasing).
- A alteração e o cancelamento do pedido só são permitidos enquanto o pedido estiver com o status `PENDENTE` (antes do parecer do agente).
- O cliente só pode visualizar e manipular os seus próprios pedidos.

---

## US03 – Avaliação Financeira de Pedidos (UC06)
**Descrição:**  
Como **Agente** (Empresa ou Banco), quero analisar financeiramente os pedidos de aluguel recebidos e registrar um parecer, para aprovar ou reprovar a solicitação do cliente.

**Critérios de Aceite:**
- O agente deve visualizar os dados do cliente, seus rendimentos e os detalhes do pedido.
- O parecer deve registrar obrigatoriamente o resultado (Aprovado/Reprovado), uma justificativa/texto de parecer e a data da avaliação.
- Após o registro do parecer positivo, o pedido muda o status para `AVALIADO_APROVADO`.

---

## US04 – Formalização e Execução do Contrato (UC07)
**Descrição:**  
Como **Cliente**, quero visualizar o parecer positivo do meu pedido e decidir se avanço para a execução do contrato, para que a locação do veículo seja confirmada.

**Critérios de Aceite:**
- O cliente deve ter acesso às condições finais do contrato (valor mensal, período de início/fim).
- Se o cliente aceitar, o contrato passa para o status `EM_EXECUCAO`.
- Caso o cliente recuse, o pedido é marcado como `CANCELADO`.

---

## US05 – Concessão de Crédito em Contratos de Leasing (UC08)
**Descrição:**  
Como **Banco**, quero emitir e associar um contrato de crédito a um pedido na modalidade Leasing, para respaldar o financiamento do automóvel.

**Critérios de Aceite:**
- Apenas agentes do tipo **Banco** podem conceder crédito para a modalidade Leasing.
- O contrato de crédito deve estar obrigatoriamente vinculado ao pedido de aluguel e ao contrato correspondente.
- Deve conter informações sobre o valor financiado e as taxas de juros acordadas.