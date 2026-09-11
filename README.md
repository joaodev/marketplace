# Marketplace

Projeto Spring Boot que demonstra uma arquitetura orientada a domínios com módulos de **Registration**, **Catalog** e **Ticketing**, cada um com persistência e integrações próprias, simulando um cenário de microserviços dentro de um único deploy.

## Visão geral da arquitetura

Embora rode como uma aplicação única (`MarketplaceApplication`), o projeto está organizado por contexto de negócio, com fronteiras claras entre módulos:

1. **registration**: cadastro de clientes.
2. **catalog**: vitrine de eventos e metadados para consulta.
3. **ticketing**: seleção de assentos com controle de concorrência.

Cada módulo segue separação por camadas:

- `domain`: entidades e regras de negócio.
- `application`: casos de uso.
- `infrastructure`: HTTP, persistência e integração por eventos.

### Bancos e infraestrutura por módulo

O `compose.yml` sobe os componentes necessários para cada contexto:

| Contexto | Banco principal | Porta local | Apoio |
|---|---|---:|---|
| Registration | MySQL (`registration-database`) | `3307` | - |
| Catalog | MySQL (`catalog-database`) | `3308` | MongoDB (`27018`) para metadados e Redis (`6380`) para cache |
| Ticketing | PostgreSQL (`ticketing-database`) | `5433` | Redis (`6381`) para locking de assentos |

Também há Actuator habilitado para healthcheck detalhado.

## Tecnologias principais

- Java 25
- Spring Boot 4.1.1
- Spring Web
- Spring Data JPA
- Spring Data MongoDB
- Spring Data Redis (Jedis)
- Docker Compose
- Gradle

## Como executar localmente

### Pré-requisitos

- Java 25
- Docker e Docker Compose

### Subindo a aplicação

1. Inicie a aplicação com Gradle:

```bash
./gradlew bootRun
```

Com `spring-boot-docker-compose` e `spring.docker.compose.lifecycle-management=start_only`, os containers do `compose.yml` são inicializados automaticamente no `bootRun`.

Se preferir, você pode subir a infra manualmente antes:

```bash
docker compose up -d
```

## Endpoints HTTP disponíveis

### 1) Catálogo (Showcase)

**Endpoint**

```http
GET /showcase
```

**Descrição**

Retorna a lista de eventos disponíveis para vitrine (`List<EventOutput>`).

**Exemplo com curl**

```bash
curl -X GET http://localhost:8080/showcase
```

---

### 2) Ticketing (Seleção de assento)

**Endpoint**

```http
POST /ticketing/events/{eventId}/seats/select
```

**Headers obrigatórios**

- `Content-Type: application/json`
- `X-CUSTOMER-ID: <uuid-ou-id-do-cliente>`

**Body**

Estrutura definida por `SeatSelectionRequest` (JSON com os dados de seleção do assento).

**Exemplo com curl**

```bash
curl -X POST "http://localhost:8080/ticketing/events/<eventId>/seats/select" \
  -H "Content-Type: application/json" \
  -H "X-CUSTOMER-ID: <customerId>" \
  -d '{
    "sectorId": "<sectorId>",
    "seatId": "<seatId>"
  }'
```

**Resposta esperada**

- `201 Created` quando a seleção é realizada com sucesso.

## Como testar os endpoints

### Teste rápido de saúde da aplicação

```bash
curl -X GET http://localhost:8080/actuator/health
```

### Fluxo sugerido de validação manual

1. Subir a aplicação (`./gradlew bootRun`).
2. Validar healthcheck (`/actuator/health`).
3. Consultar vitrine (`GET /showcase`).
4. Executar seleção de assento (`POST /ticketing/events/{eventId}/seats/select`) com IDs válidos.

## Testes automatizados

Para executar os testes existentes:

```bash
./gradlew test
```

## Organização de pacotes

```text
src/main/java/com/joaodev/marketplace
├── catalog
│   ├── application
│   ├── domain
│   └── infrastructure
├── registration
│   ├── domain
│   └── infrastructure
├── ticketing
│   ├── application
│   ├── domain
│   └── infrastructure
└── common
    └── infrastructure/event/dto
```

## Observações

- O projeto adota uma abordagem modular para representar microserviços, facilitando evolução para deploys independentes no futuro.
- A configuração atual usa `hibernate.hbm2ddl.auto=update`, adequada para laboratório/desenvolvimento.
