# 🥋 Ossu Tech

Sistema de controle de presença e graduação para escolas de Jiu-Jitsu.

## 📋 Sobre o Projeto

Ossu Tech é uma aplicação backend desenvolvida em Java com Spring Boot que permite:
- Controle de presença via QR Code
- Gestão de graduações e faixas
- Aprovação de check-ins por professores
- Acompanhamento de evolução dos alunos

## 🚀 Tecnologias

- **Java 17+**
- **Spring Boot 3.5.8**
- **PostgreSQL 16**
- **Spring Data JPA**
- **Spring Security + JWT**
- **Flyway** (migrations)
- **Docker & Docker Compose**
- **Lombok**
- **MapStruct**

## 🏗️ Arquitetura

O projeto segue os princípios de **Domain-Driven Design (DDD)**, organizado em:

- **Domain Layer**: Entidades, Value Objects, Aggregates
- **Application Layer**: Use Cases, DTOs, Mappers
- **Infrastructure Layer**: JPA, Security, Persistence
- **Interface Layer**: Controllers REST, WebSocket

## 📦 Pré-requisitos

- Java 17 ou superior
- Maven 3.8+
- Docker & Docker Compose
- Git

## 🔧 Como executar

### 1. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/ossu-tech.git
cd ossu-tech
```

### 2. Subir o banco de dados
```bash
docker-compose up -d
```

### 3. Executar a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## 🗄️ Banco de Dados

O projeto usa PostgreSQL com Flyway para versionamento do schema.

As migrations estão em: `src/main/resources/db/migration/`

## 📚 Documentação da API

(Em desenvolvimento - será adicionada documentação Swagger)

## 🧪 Testes
```bash
mvn test
```

## 📝 Licença

Este projeto é um projeto pessoal de estudos.

## 👤 Autor

Desenvolvido como projeto de aprendizado em DDD e Spring Boot.