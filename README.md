

# Payment Microservices - Arquitetura Hexagonal

Este repositório apresenta um projeto completo de **microserviços** para uma aplicação de transferência de dinheiro, que inclui um **API Gateway** para centralizar autenticação e roteamento. Desenvolvido com foco em **Arquitetura Hexagonal (Ports and Adapters)**, comunicação assíncrona via RabbitMQ e service discovery com Eureka Server, este sistema é um exemplo robusto e escalável para soluções financeiras distribuídas.

---

## 🚀 Visão Geral

* **Arquitetura:** Microserviços independentes seguindo **Arquitetura Hexagonal** com comunicação REST e RabbitMQ, descoberta dinâmica de serviços via Eureka e autenticação baseada em JWT.
* **Objetivo:** Criar um sistema financeiro distribuído que segue as melhores práticas do mercado, com alta modularidade, escalabilidade e facilidade de manutenção.
* **Tecnologias:** Java 17 (Spring Boot), RabbitMQ, Eureka, Docker, JWT, Swagger.

---

## 🏗️ Módulos/Microserviços

* **api-gateway:** Centraliza autenticação, roteamento e segurança das requisições.
* **eureka-server:** Serviço de discovery (Service Registry) para todos os micros.
* **user-service:** Gerencia usuários, registro, login e autenticação.
* **wallet-service:** Gerencia carteiras digitais e saldos.
* **payment-api-service:** Orquestra transações entre os micros.
* **payment-processor-service:** Processa efetivamente as transações via RabbitMQ.

---

## ⚡ Foco do Projeto

* Arquitetura de **microserviços desacoplados** seguindo **Arquitetura Hexagonal** para escalabilidade e testabilidade.
* Comunicação assíncrona com RabbitMQ para maior resiliência.
* **Hexagonal Architecture (Ports and Adapters)** para isolamento do domínio e independência de frameworks.
* Service discovery dinâmico com Eureka Server.
* API Gateway para autenticação centralizada e roteamento inteligente.

---

## 🎯 Arquitetura Hexagonal

Todos os microserviços seguem os princípios da **Arquitetura Hexagonal**:

### Estrutura dos Microserviços

```
service/
├── domain/
│   ├── entity/          # Entidades de domínio (regras de negócio)
│   └── service/         # Implementações dos use cases
├── application/
│   ├── dto/             # Data Transfer Objects
│   └── ports/
│       ├── input/       # Portas de entrada (interfaces dos use cases)
│       └── output/      # Portas de saída (interfaces de repositórios, clientes, etc)
├── adapters/
│   ├── input/
│   │   └── rest/        # Adaptadores REST (Controllers)
│   └── output/
│       ├── persistence/ # Adaptadores de persistência (JPA)
│       ├── messaging/   # Adaptadores de mensageria (RabbitMQ)
│       └── client/      # Adaptadores de clientes HTTP (Feign)
└── infrastructure/      # Configurações, segurança, beans
```

### Princípios Aplicados

1. **Separação de Responsabilidades**: O domínio está isolado de detalhes de infraestrutura
2. **Portas (Ports)**: Interfaces que definem os contratos de entrada e saída
3. **Adaptadores (Adapters)**: Implementações que conectam a aplicação com o mundo externo
4. **Inversão de Dependência**: O domínio não depende de frameworks ou bibliotecas externas
5. **Testabilidade**: Os use cases podem ser testados independentemente dos adapters

### Benefícios

- ✅ **Testabilidade**: Fácil criação de testes unitários e de integração
- ✅ **Manutenibilidade**: Mudanças em frameworks não afetam o domínio
- ✅ **Escalabilidade**: Fácil adição de novos adapters
- ✅ **Independência**: O domínio não conhece detalhes de implementação

---

## 🌐 Acessos Importantes

* **Eureka Server (Dashboard):**
  [http://localhost:8761](http://localhost:8761)

* **User Service (Swagger UI):**
  [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

* **Wallet Service (Swagger UI):**
  [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)

* **Payment API Service (Swagger UI):**
  [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)

---

## 📑 Documentação das APIs

Para facilitar seus testes e integrações, toda a documentação das rotas está disponível no Postman, com exemplos prontos para importação e execução.

Acesse a documentação completa da API aqui:
👉 [Postman Collection - Payment Microservices](https://documenter.getpostman.com/view/37902450/2sB34oAbpZ)

---

## 🧩 Como rodar o projeto

### Pré-requisitos

* Docker e Docker Compose instalados
* Java 17+

### Passos

```bash
git clone https://github.com/cauaemanuel/Payment-Microservices-Clean-Architecture.git
cd Payment-Microservices-Clean-Architecture
docker-compose up --build
```

Depois, acesse o Eureka e os Swaggers para explorar os serviços.

> ⚠️ **Importante:** Caso as tabelas do banco de dados PostgreSQL não sejam criadas automaticamente na primeira inicialização, execute manualmente o script `data.sql` para criar a estrutura necessária.

---

## 🚧 Status do Projeto

Este projeto ainda está em construção e pode receber atualizações frequentes. Sugestões e contribuições são muito bem-vindas!

---

## 📄 Licença

Projeto licenciado sob a **MIT License**.

---

> Feito com 💙 por [@cauaemanuel](https://github.com/cauaemanuel)


