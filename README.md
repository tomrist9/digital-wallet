# Digital Wallet — Microservices Platform (Spring Boot + Spring Cloud)

A production-grade **digital wallet** platform built with **Spring Boot** and **Spring Cloud**, featuring **event-driven architecture** with **Apache Kafka**, **CQRS with Elasticsearch**, **Keycloak (OAuth2/OIDC + JWT)** security, **API Gateway**, **service discovery**, **observability (Prometheus/Grafana)**, **centralized logging (ELK)**, and **distributed tracing (Zipkin/Sleuth or Micrometer Tracing)**.

> Goal: Learn + apply real-world microservices patterns while building a realistic wallet system (accounts, transfers, payments, transactions, notifications).

---

## ✨ Key Features

- **Microservices architecture** with Spring Boot
- **Spring Cloud Stack**
  - Config Server (externalized configuration)
  - Netflix Eureka (service discovery)
  - Spring Cloud Gateway (API Gateway)
  - Spring Cloud LoadBalancer (client-side load balancing)
  - Resilience4j (circuit breaker, retries, timeouts)
  - Rate limiting (Redis-backed)
- **Event-driven microservices** with **Apache Kafka**
  - producers/consumers, partitions, consumer groups
  - optional: schema registry + Avro
  - optional: Kafka Streams + state store
- **CQRS with Kafka + Elasticsearch**
  - Write model in PostgreSQL
  - Read model in Elasticsearch (indexed + query APIs)
- **Security**: Keycloak with OAuth 2.0 + OpenID Connect + JWT
- **Observability**
  - Spring Boot Actuator + Micrometer
  - Prometheus + Grafana dashboards
  - Distributed tracing via Zipkin (or newer Micrometer Tracing stack)
- **Logging**
  - MDC correlation (request-id)
  - ELK stack: Elasticsearch + Logstash + Kibana
- **API documentation**: OpenAPI v3 (Swagger)
- **HATEOAS** (optional for resource navigation)
- **Basic UI** (optional): Thymeleaf + Bootstrap
- **Reactive** (optional): WebFlux/WebClient for async reads to Elasticsearch
- **Database per service** pattern

---

## 🧱 Architecture Overview

**Flow**
1. Client calls `api-gateway`
2. Gateway routes to services through Eureka discovery
3. Services persist to **PostgreSQL** (write model)
4. Services publish domain events to **Kafka**
5. `query-service` (or read-model projector) consumes events and updates **Elasticsearch**
6. Monitoring & tracing via Prometheus/Grafana/Zipkin, logs via ELK

**High-level diagram**
```text
Client
  |
  v
[API Gateway] -> (RateLimit + CircuitBreaker + Auth)
  |
  v
[Eureka Discovery]
  |
  +--> account-service  ---> Postgres (account_db) ---> Kafka events
  |
  +--> wallet-service   ---> Postgres (wallet_db)  ---> Kafka events
  |
  +--> transfer-service ---> Postgres (transfer_db)-> Kafka events
  |
  +--> notification-service <--- Kafka events (email/sms/push)
  |
  +--> query-service <--- Kafka events ---> Elasticsearch (read model)
