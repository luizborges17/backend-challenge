
# Backend Challenge - Validação de JWT

Este projeto é uma API RESTful construída com Spring Boot 3 para resolver um desafio técnico de backend. A aplicação foca em autenticação via JWT, boas práticas de engenharia de software e monitoramento.

## 🧠 Objetivo

O desafio proposto consiste na construção de uma API web que recebe como parâmetro um JWT (JSON Web Token) no formato de string e realiza sua validação com base nas seguintes regras:

1. O JWT deve ser válido (estrutura e assinatura corretas).
2. Deve conter **apenas 3 claims**: `Name`, `Role` e `Seed`.
3. A claim `Name`:
   - Não pode conter caracteres numéricos.
   - Deve ter no máximo **256 caracteres**.
4. A claim `Role` deve conter apenas **um dos seguintes valores**:
   - `Admin`
   - `Member`
   - `External`
5. A claim `Seed` deve ser um **número primo**.

### Entrada esperada:
- Um JWT (string).

### Saída esperada:
- Um **booleano** indicando se o token é válido ou não com base nas regras acima.

### Exemplos fornecidos:
- Um token com `"Name": "Toninho Araujo", "Role": "Admin", "Seed": "7841"` é considerado válido.
- Um token com caracteres numéricos na claim `Name`, mais de três claims, ou `Seed` que não seja número primo deve retornar falso.

---

A avaliação desta etapa também contempla:

- Qualidade e cobertura de testes (unitários e de integração).
- Boas práticas de abstração, coesão, acoplamento e extensibilidade.
- Qualidade do design da API e aplicação dos princípios SOLID.
- Clareza e completude da documentação (`README`).
- Histórico de commits bem estruturado e representativo.
- Implementação de observabilidade (logging, tracing e monitoring).
- Containerização da aplicação com Docker.
- Provisionamento de infraestrutura em nuvem (preferencialmente AWS).
- Deploy automatizado (CI/CD) e uso de ferramentas como Helm e Terraform.
- Disponibilização da API em algum provedor de nuvem.
- Uso de Engenharia de Prompt e organização geral do projeto.

Este projeto foi desenvolvido considerando todos esses critérios com o objetivo de demonstrar minha capacidade de entregar soluções de qualidade, escaláveis, seguras e bem documentadas, como seria esperado em ambientes de produção.


## ⚙️ Tecnologias Utilizadas e Justificativas

| Tecnologia                          | Justificativa                                                                                     |
|-----------------------------------|-------------------------------------------------------------------------------------------------|
| **Java 17 + Spring Boot 3**       | Ecossistema robusto, moderno, com suporte nativo a observabilidade via Micrometer e integração com OpenAPI. |
| **Spring Validation**              | Facilita a validação de payloads com anotações simples e reutilizáveis.                          |
| **JWT (JJWT + Auth0)**             | Libs consolidadas para geração e validação de tokens com suporte a claims customizados.          |
| **Spring Actuator**                | Coleta de métricas de saúde, uso de recursos e endpoints customizados.                          |
| **Prometheus**                    | Monitoramento e visualização de métricas em tempo real.                                        |
| **Micrometer**                    | Observabilidade com tracing de requisições.                                                    |
| **Swagger (SpringDoc OpenAPI)**  | Geração automática de documentação interativa da API.                                          |
| **Docker + Docker Compose**       | Facilita o provisionamento do ambiente local completo e portável.                              |
| **GitHub Actions (CI)**           | Automação de integração contínua através de workflows configuráveis para garantir qualidade do código. |
| **Render (CD)**                   | Plataforma gratuita para deploy contínuo, simplificando o processo de entrega da aplicação.     |
| **JUnit + MockMvc (Testes Unitários e Integrados)** | Ferramentas consolidadas para garantir qualidade e cobertura de testes da API.                 |
| **Padrões de Projeto: Factory e Strategy** | Facilita a organização, manutenção e escalabilidade do código com boas práticas de design.      |
| **Insomnia**                     | Ferramenta para consumir e testar a API de forma rápida e eficiente.                            |
| **Conventional Commits**          | Padronização dos commits para melhor organização e legibilidade. |

---

## 🔄 CI/CD Detalhado

### Integração Contínua (CI) com GitHub Actions

- **Workflow configurado para:**  
  1 . Realizar checkout do código.  
  2 . Configurar JDK 17 (Temurin).  
  3 . Compilar o projeto com Maven, rodando os testes automaticamente.  
  4 . Executar testes unitários e de integração com JUnit e MockMvc.  
  5 . Construir a imagem Docker com tag baseada no commit.  
  6 . Preparar o ambiente para deploy contínuo.


  
### Deploy Contínuo (CD) com Render

- **Deploy automático acionado por webhook** logo após a pipeline CI finalizar com sucesso.  
- Facilita a entrega contínua da aplicação sem intervenção manual.  
- Garante que a versão em produção esteja sempre atualizada com o branch principal.
---

## 📦 Requisitos

- Docker Desktop
- Java 17
- Maven
- Insomnia/Postman
- Desenvolvimento realizado em ambiente Windows

---

## ▶️ Como executar com Docker

### 1. Clone o repositório

```bash
git clone https://github.com/luizborges17/backend-challenge.git
```

### 2. Compile o projeto

```bash
./mvnw clean package -DskipTests
```

### 3. Suba os containers

```bash
docker-compose up --build
```

> Isso iniciará:
- API na porta `8080`
- Prometheus na `http://localhost:9090`

---

## 🔐 JWT - Validação

A API valida tokens JWT passados no body da requisição. Caso o token seja inválido, expirado ou malformado, a API retorna um boolean false.

> 🔹 Para testar e validar facilmente, basta importar o arquivo `backend-challenge-insomnia.json`, que está na raiz do projeto, no Insomnia ou Postman para consumir a API localmente.

---

## 🧪 Endpoints

### `POST /api/validate`

Valida o JWT enviado no corpo da requisição no formato JSON:

**Request body:**

```json
{
  "jwt": "<token_jwt_aqui>"
}
```

---

## 📊 Observabilidade

### 📈 Métricas com Prometheus

- Expostas automaticamente em `/actuator/prometheus`
- Incluem métricas que garantem a observabilidade.
Acesse: [http://localhost:9090](http://localhost:9090)

### 🔍 Tracing com Micrometer

- Cada requisição HTTP gera **traceId** e **spanId** para rastreamento detalhado.
- Gera **spans distribuídos** que representam unidades de trabalho dentro da requisição.
- Permite integração via OTLP.
- Permite rastrear requisições ponta-a-ponta, facilitando a observabilidade em sistemas distribuídos.
  
---

## 📄 Documentação da API

- Geração automática com Swagger ao subir o projeto com Dokcer
- Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧱 Estrutura do Projeto MVC

```
src/
├── model/ # Modelos de dados e entidades
├── controller/ # Camada Controller (Endpoints REST)
├── service/ # Camada Service (Regras de negócio e validação de tokens)
├── util/ # Utilitários diversos (ex: validação de número primo)
└── Application.java # Classe principal para inicialização da aplicação
```

---

## 🤔 Por que essa arquitetura?

1. **Separação de responsabilidades**: a estrutura clara entre `model`, `controller`, `service` e `util` facilita testes, manutenção e escalabilidade do projeto.
2. **Modelos de dados bem definidos**: as entidades no pacote `model` garantem organização e integridade das informações manipuladas.
3. **Utilitários dedicados**: funções auxiliares, como a validação de número primo, ficam isoladas no pacote `util`, melhorando reutilização e clareza.
4. **Observabilidade nativa**: integração com ferramentas de monitoramento e tracing para maior visibilidade em ambientes produtivos.
5. **Uso de boas práticas REST**: aplicação padronizada, com versionamento e mensagens claras para melhor comunicação com clientes da API.

## 🧩 Descrição dos Métodos e Padrões de Projeto

### Padrão Strategy e Factory na Validação de Claims do JWT

#### ClaimValidationStrategy (Interface)
- Define o contrato para todas as estratégias de validação de claims.
- Métodos principais:
  - `boolean validate(String value)`: valida o valor da claim conforme regras específicas.
  - `String getClaimName()`: retorna o nome da claim que a estratégia valida.

#### Implementações Concretas das Estratégias
- **NameClaimValidation**:  
  Valida se o nome é uma string com até 256 caracteres, contendo apenas letras e sem espaços internos.
- **RoleClaimValidation**:  
  Verifica se o valor está dentro de um conjunto fixo de papéis permitidos ("Admin", "Member", "External").
- **SeedClaimValidation**:  
  Confirma se o valor é um número primo, garantindo que o seed seja válido.

Essas classes implementam a interface `ClaimValidationStrategy`, encapsulando diferentes regras de validação para claims específicas.

#### ClaimValidationFactory
- Atua como uma **Factory** que gerencia o registro e fornecimento das estratégias de validação.
- Mantém um mapa estático associando o nome da claim à sua respectiva estratégia.
- Métodos principais:
  - `register(ClaimValidationStrategy strategy)`: registra uma nova estratégia no mapa.
  - `getStrategy(String claimName)`: retorna a estratégia para o nome da claim solicitada.

Essa fábrica abstrai a criação e localização da estratégia correta, permitindo adicionar facilmente novas validações sem alterar o serviço principal.

#### JwtValidationService
- Serviço que orquestra a validação do JWT:
  1. Decodifica o token e obtém suas claims.
  2. Verifica se todas as claims obrigatórias estão presentes e se não há extras.
  3. Para cada claim obrigatória, obtém a estratégia de validação via `ClaimValidationFactory`.
  4. Executa a validação usando a estratégia correspondente.
  5. Retorna `true` se todas as validações passarem, caso contrário `false`.

Dessa forma, o serviço utiliza o **padrão Strategy** para aplicar regras diferentes de forma flexível, e a **Factory** para gerenciar essas estratégias, garantindo código aberto para extensão e fechado para modificação (Princípio Open/Closed).

---

### Benefícios da abordagem

- **Extensibilidade:** Novas estratégias podem ser adicionadas sem mexer no código existente do serviço.
- **Separação de responsabilidades:** Cada estratégia encapsula sua lógica específica de validação.
- **Manutenção facilitada:** Validações complexas ficam isoladas, facilitando testes e ajustes.
- **Flexibilidade:** A factory pode ser facilmente modificada para carregar estratégias dinamicamente, se necessário.

---

### Fluxo resumido de validação do JWT

1. O token JWT é decodificado e suas claims extraídas.
2. Confere se as claims obrigatórias estão presentes.
3. Para cada claim, busca a estratégia no `ClaimValidationFactory`.
4. Executa a validação usando a estratégia encontrada.
5. Retorna o resultado geral da validação.

