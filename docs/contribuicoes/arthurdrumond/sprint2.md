# Registro de Contribuição — Sprint 2 (Lab02)

**Integrante:** Arthur Drumond  

---

## Atividades Desenvolvidas na Sprint 2

**Contribuição:** Implementação do CRUD completo de Cliente seguindo a arquitetura MVC, com persistência local em banco H2, validações de negócio e telas Thymeleaf.

**Detalhamento das Entregas:**
- **Configuração do projeto (`pom.xml`):** Criação da estrutura Maven com Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, Bean Validation e H2.
- **Inicialização da aplicação (`AluguelDeCarrosApplication.java`):** Criação da classe principal para execução da aplicação Spring Boot.
- **Modelos (`model/`):** Implementação de `Usuario`, `Cliente` e `RendimentoEmpregadora`, respeitando a herança e os atributos definidos no diagrama de classes.
- **Repository (`ClienteRepository.java`):** Criação do repositório JPA para operações de persistência, busca por CPF e verificação de duplicidade.
- **Service (`ClienteService.java`):** Implementação das operações de listar, buscar, cadastrar, atualizar e excluir clientes, além das regras de validação e normalização do CPF.
- **Controller MVC (`ClienteController.java`):** Implementação das rotas para cadastro, consulta, edição e exclusão de clientes, com uso dos métodos HTTP `GET`, `POST`, `PUT` e `DELETE`.
- **Telas web (`templates/clientes/`):** Criação das páginas Thymeleaf para listagem e formulário de clientes, incluindo o cadastro de até três empregadoras.
- **Banco local (`data/`):** Configuração do H2 persistido em arquivo no caminho `data/aluguel-carros`, permitindo manter os registros após o encerramento da aplicação.

**Decisões e Observações:**
- Foi adotada a arquitetura MVC, mantendo a responsabilidade de cada camada separada entre model, repository, service, controller e views.
- O banco H2 foi configurado no modo arquivo para persistir os dados localmente em `data/`.
- O CPF é normalizado antes da persistência e validado considerando seus dígitos verificadores.
- O sistema impede CPFs duplicados e limita o cadastro a três entidades empregadoras por cliente.
- A implementação permanece restrita ao CRUD de Cliente, sem incluir funcionalidades de pedidos, contratos, agentes ou autenticação.
- Não foi criado controller REST; as respostas do controller MVC são páginas Thymeleaf, conforme a arquitetura solicitada.



