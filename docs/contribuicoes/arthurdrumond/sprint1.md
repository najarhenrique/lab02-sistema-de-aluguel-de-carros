# Registro de Contribuição — Sprint 1 (Lab02)

**Integrante:** Arthur Drumond  

---

## Atividades Desenvolvidas na Sprint 1

**Contribuição:** Revisão e evolução da modelagem UML existente (casos de uso, classes e pacotes), ajuste nas histórias de usuário e atualização dos artefatos PlantUML gerados (PNG) para refletir mudanças.

**Detalhamento das Entregas:**
- **Diagrama de Casos de Uso (`docs/diagramas/casos-de-uso.puml`):** Adição do caso de uso de autenticação/login, notas de pré-condição (usuário cadastrado/autenticado) e ligação explícita entre avaliação do agente e decisão do cliente.
- **Diagrama de Classes (`docs/diagramas/diagrama-de-classes.puml`):** Introdução do enum `StatusContrato`, realocação do estado `EM_EXECUCAO` para o `Contrato`, e inclusão de notas sobre a propriedade do automóvel dependendo da `ModalidadeContrato` (Locação / Assinatura / Leasing).
- **Diagrama de Pacotes (`docs/diagramas/diagrama-de-pacotes.puml`):** Reorganização em dois subsistemas (`Gestão de Pedidos e Contratos` e `Construção Dinâmica de Páginas Web`), inclusão de artefatos de infraestrutura (Servidor Central e Clientes/Agentes) e ativação de `allowmixing` para mesclar artefatos.
- **Imagens atualizadas (`.png`):** Geração e atualização dos PNGs derivados dos PlantUML para manter os diagramas visuais sincronizados com o modelo.
- **Histórias do Usuário (`docs/historias-de-usuario.md`):** Inclusão de observação sobre autenticação como requisito, registro da propriedade do automóvel por modalidade e vínculo obrigatório do `ContratoCredito` quando aplicável (leasing).

**Decisões e Observações:**
- A autenticação é requisito transversal: todas as operações de gestão de pedidos exigem usuário cadastrado e sessão ativa.
- O status `EM_EXECUCAO` foi pensado como propriedade do `Contrato` para refletir melhor o ciclo de vida legal do aluguel.
- Em `LEASING`, o `ContratoCredito` deve ser criado por um agente do tipo `Banco` e vinculado ao `PedidoAluguel` e ao `Contrato`.

**Tempo estimado:** ~4 horas (revisão de modelos, edição de PlantUML, geração de imagens e testes locais).

**Observações finais:**
- Recomendo revisar os diagramas em conjunto para alinhar nomenclaturas antes da próxima sprint, especialmente as entidades relacionadas a contratos e status.

---

*Arquivo gerado automaticamente como registro de contribuição da sprint.*
