# Registro de Contribuição — Sprint 3 (Lab02)

**Integrante:** Arthur Drumond  

---

## Atividades Desenvolvidas na Sprint 3

**Contribuição:** Conversão do sistema para uma API REST, implementação do fluxo de pedidos de aluguel (criação e consulta de status) e construção de um protótipo de frontend em React para uso pelos clientes cadastrados.

**Detalhamento das Entregas:**
- **DTOs (`dto/`):** Criação dos objetos de requisição e resposta de cliente (`ClienteCreateRequest`, `ClienteUpdateRequest`, `ClienteResponse`), rendimento (`RendimentoUpdateRequest`, `RendimentoResponse`), login (`LoginRequest`), pedido (`PedidoCreateRequest`, `PedidoResponse`), automóvel (`AutomovelResponse`) e erro (`ErroResponse`).
- **Conversão para API REST (`ClienteController.java`):** Substituição do controller MVC com Thymeleaf por um `@RestController` em `/api/clientes`, com os métodos `GET`, `POST`, `PUT` e `DELETE` trafegando JSON. A dependência do Thymeleaf e os templates foram removidos.
- **Autenticação (`AuthController.java`):** Criação do endpoint `POST /api/auth/login`, que autentica o cliente por e-mail e senha e retorna seus dados.
- **Modelos (`model/`):** Implementação de `Automovel`, `PedidoAluguel` e dos enums `StatusPedido` e `ModalidadeContrato`, conforme o diagrama de classes.
- **Repositories (`AutomovelRepository.java`, `PedidoAluguelRepository.java`):** Criação dos repositórios JPA para automóveis e pedidos, incluindo consultas por cliente e verificação de pedidos em andamento por automóvel.
- **Service de pedidos (`PedidoAluguelService.java`):** Implementação da criação, listagem e consulta de pedidos do cliente autenticado, com as regras de validação do pedido.
- **Controller de pedidos (`PedidoAluguelController.java`):** Criação dos endpoints `GET /api/pedidos`, `GET /api/pedidos/{id}` e `POST /api/pedidos`, identificando o cliente pelo cabeçalho `X-Cliente-Id`.
- **Tratamento de erros (`ApiExceptionHandler.java`):** Padronização das respostas de erro em JSON com os códigos HTTP adequados (`400`, `401`, `404` e `409`).
- **Frontend (`frontend/`):** Criação de um protótipo em React com Vite contendo as telas de login, cadastro de cliente (com até três empregadoras) e pedidos (criação e visualização do status).

**Decisões e Observações:**
- As validações foram concentradas na criação do pedido: placa no formato antigo (`ABC1234`) ou Mercosul (`ABC1D23`), ano entre 1900 e o ano seguinte ao atual, consistência dos dados do automóvel já cadastrado e bloqueio de automóveis com pedido `PENDENTE` ou `AVALIADO_APROVADO`.
- O cadastro de cliente foi simplificado: foram removidas a validação dos dígitos verificadores do CPF e a validação de formato do e-mail, mantendo apenas a obrigatoriedade dos campos e a unicidade de CPF e e-mail.
- Todo pedido é criado com status `PENDENTE`, e o cliente só pode visualizar os próprios pedidos.
- A autenticação é simplificada para o protótipo: não há token nem Spring Security, e o identificador do cliente é enviado no cabeçalho `X-Cliente-Id`.
- O frontend utiliza o proxy do Vite para encaminhar as chamadas `/api` ao backend, dispensando configuração de CORS.
- A construção do frontend contou com assistência de IA; todo o código gerado foi revisado por mim antes de ser incorporado ao projeto.
