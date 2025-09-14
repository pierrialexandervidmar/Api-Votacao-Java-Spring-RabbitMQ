# API de Votação (Spring Boot + RabbitMQ + PostgreSQL)

Sistema simples de votação para demonstrar um fluxo **sincrônico** (REST) produzindo mensagens para **RabbitMQ** e um **listener** consumindo e processando votos. Persistência via **JPA/Hibernate** em **PostgreSQL**.

> Stack: Java 21 • Spring Boot • Spring Web • Spring Data JPA • Spring AMQP • Flyway • RabbitMQ • PostgreSQL

---

## 📁 Estrutura do projeto

```
src/main/java/com/api/votacao
├─ config/
│  └─ RabbitConfiguration.java        # Exchange, fila, binding e conversor JSON
├─ dtos/
│  ├─ CandidatoRequest.java
│  ├─ CandidatoResponse.java
│  ├─ VotoRequest.java
│  └─ VotoResponse.java
├─ entities/
│  ├─ Candidato.java
│  └─ Voto.java
├─ listeners/
│  └─ ComputaVotoListener.java        # @RabbitListener delegando para service
├─ repositories/
│  ├─ CandidatoRepository.java
│  └─ VotoRepository.java
├─ resources/
│  ├─ CandidatoResource.java
│  └─ VotoResource.java               # Endpoints REST
└─ services/
   ├─ exceptions/
   │  └─ ResourceNotFoundException.java
   ├─ CandidatoService.java
   └─ VotoService.java                # Publica no Rabbit e valida domínio

src/main/resources
├─ application.properties
├─ db.migration/                      # (Opcional) scripts Flyway
├─ templates/, static/                # (se aplicável)
└─ data.sql                           # (Opcional) seeds iniciais
```

---

## 🧩 Domínio e fluxo

1. `POST /api/candidato` cadastra candidatos.
2. `POST /api/voto` recebe um voto, **valida** o candidato e **publica** uma mensagem na exchange do RabbitMQ.
3. `ComputaVotoListener` consome mensagens da fila `computar-voto.q` e delega o processamento para um service de domínio (persistência do voto, regras, idempotência, etc.).

> Boas práticas aplicadas: listener fino, regras de negócio nos services, DTOs de entrada/saída, validação de existência de candidato antes do publish.

---

## ⚙️ Requisitos

* Java 21+
* Maven 3.9+ (ou usar `./mvnw`)
* PostgreSQL 15+ (ou Docker)
* RabbitMQ 3.13+ (com plugin de management recomendado)

---

## 🚀 Subindo infraestrutura com Docker (sugestão)

Crie um `docker-compose.yml` (exemplo mínimo):

```yaml
services:
  postgres:
    image: postgres:17
    container_name: votacao-pg
    environment:
      POSTGRES_DB: votacao
      POSTGRES_USER: votacao
      POSTGRES_PASSWORD: votacao123
      TZ: America/Sao_Paulo
      PGTZ: America/Sao_Paulo
    ports: ["5432:5432"]
    volumes: ["pgdata:/var/lib/postgresql/data"]
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U votacao -d votacao"]
      interval: 5s
      timeout: 3s
      retries: 10

  rabbitmq:
    image: rabbitmq:3.13-management
    container_name: votacao-rabbit
    ports:
      - "5672:5672"     # AMQP
      - "15672:15672"   # Management UI
    healthcheck:
      test: ["CMD", "rabbitmq-diagnostics", "-q", "ping"]
      interval: 5s
      timeout: 3s
      retries: 10

volumes:
  pgdata:
```

> UI do RabbitMQ: [http://localhost:15672](http://localhost:15672) (user: `guest` / pass: `guest`)

---

## 🔧 Configuração da aplicação

`src/main/resources/application.properties` (exemplo):

```properties
spring.application.name=votacao

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/votacao
spring.datasource.username=votacao
spring.datasource.password=votacao123
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.jdbc.time_zone=UTC

# Flyway (se usar)
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Logs
logging.level.org.springframework.amqp=INFO
logging.level.com.api.votacao=DEBUG
```

A `RabbitConfiguration` define (exemplo recomendado):

* **Exchange**: `computar-voto.ex` (topic)
* **Fila**: `computar-voto.queue`
* **Routing key**: ``
* **Conversor**: `Jackson2JsonMessageConverter` para publicar/consumir JSON

No publish (service):

```java
rabbitTemplate.convertAndSend("computar-voto.ex", "", request);
```

No listener:

```java
@RabbitListener(queues = "computar-voto.queue")
public void onMessage(@Payload VotoRequest request) { ... }
```

---

## ▶️ Como rodar

1. Suba Postgres e RabbitMQ (Docker Compose acima).
2. Rode a aplicação:

```bash
# usando wrapper do Maven
./mvnw spring-boot:run

# ou build + run
./mvnw clean package
java -jar target/votacao-*.jar
```

A API sobe (por padrão) em `http://localhost:8080`.

---

## 🧪 Endpoints (exemplos)

### Criar candidato

```bash
curl -X POST http://localhost:8080/api/candidato \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Candidato A"
      }'
```

### Listar candidatos

```bash
curl http://localhost:8080/api/candidato
```

### Votar

```bash
curl -X POST http://localhost:8080/api/voto \
  -H "Content-Type: application/json" \
  -d '{
        "idCandidato": 1,
        "idEleitor": 123
      }'
```

Resposta esperada (`VotoResponse`) com dados do candidato. O processamento completo do voto é feito de forma **assíncrona** pelo listener.

---

## ✅ Regras importantes

* **Validação do candidato:** antes de publicar voto, o service busca `candidatoRepository.findById(...)` e lança `ResourceNotFoundException` se não existir.
* **Integridade referencial:** entidade `Voto` referencia `Candidato` via FK.
* **Idempotência (recomendado):** crie uma constraint única (ex.: `eleitor_id + candidato_id` ou um `voto_uuid`) para evitar votos duplicados em reprocessamentos.
* **DLQ/Retry (recomendado):** configure DLQ e política de reentrega para mensagens problemáticas.

---

## 🧱 Migrações e seed

* Use **Flyway** em `db.migration` (ex.: `V1__create_tables.sql`).
* Opcionalmente, um `data.sql` pode criar candidatos iniciais para testes rápidos.

---

## 🗺️ Roadmap

* [ ] Paginação e filtros nos endpoints
* [ ] Métricas e health checks (Actuator)
* [ ] Segurança (JWT)
* [ ] DLQ e política de retries no Rabbit
* [ ] Testes de integração (`@SpringBootTest`) e testes unitários (Mockito)

---

