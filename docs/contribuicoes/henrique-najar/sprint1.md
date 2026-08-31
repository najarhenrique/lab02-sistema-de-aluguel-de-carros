# Registro de Contribuição — Sprint 1 (Lab02)

**Integrante:** Henrique Najar  
**Caminho no Repositório:** `docs/contribuicoes/henrique-najar/sprint1.md`

---

## Atividades Desenvolvidas na Sprint 1

**Contribuição:** Estruturação inicial da arquitetura do repositório, criação da modelagem conceitual UML (Casos de Uso, Classes e Pacotes) e documentação das Histórias do Usuário do sistema de aluguel de carros.

**Detalhamento das Entregas:**
- **Estruturação do Repositório:** Inicialização das pastas do projeto seguindo o padrão MVC enxuto (`aluguelcarros/model`, `controller`, `service`, `repository`, além da estrutura web `templates` e `static/css`), além da configuração do `.gitignore` para ignorar arquivos `.DS_Store`.
- **Diagrama de Casos de Uso (`casos-de-uso.puml`):** Mapeamento dos atores (Cliente, Agente, Empresa, Banco) e identificação explicita dos casos de uso (`UC01` a `UC08`).
- **Diagrama de Classes (`diagrama-de-classes.puml`):** Definição das entidades de domínio (`Cliente`, `Usuario`, `Agente`, `Empresa`, `Banco`, `Automovel`, `PedidoAluguel`, `ParecerFinanceiro`, `Contrato`, `ContratoCredito`), atributos, tipos e cardinalidades de relacionamentos.
- **Diagrama de Pacotes (`diagrama-de-pacotes.puml`):** Representação da visão lógica da aplicação detalhando a dependência entre a camada visual (HTML/CSS), controllers, serviços e persistência.
- **Histórias do Usuário (`historias-de-usuario.md`):** Redação das histórias de usuário (`US01` a `US05`) com seus respectivos critérios de aceite alinhados ao enunciado do laboratório.

**Decisões de Projeto:**
- Organização do pacote Java de forma simplificada (`aluguelcarros.*`), removendo subníveis desnecessários para facilitar o desenvolvimento.
- Separação clara entre a modalidade comum de aluguel e a concessão de crédito em *Leasing* exclusiva para entidades do tipo `Banco`.