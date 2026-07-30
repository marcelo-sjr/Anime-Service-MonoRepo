# 🎌 Anime Service Monorepo

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Multi--Module-C71A36?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

Um **monorepo** contendo uma arquitetura modular baseada em **Spring Boot**, desenvolvida para fornecer serviços relacionados ao gerenciamento de animes e usuários, compartilhando código comum através de um módulo central.

---

# 📖 Visão Geral

O projeto utiliza o recurso de **Maven Multi Module**, permitindo separar responsabilidades em módulos independentes enquanto compartilham bibliotecas internas.

Atualmente o monorepo é composto por três módulos:

- 🎌 **Anime-Service**
- 👤 **User-Service**
- 📦 **Commons-Core**

Essa estrutura facilita manutenção, reutilização de código e escalabilidade do projeto.

---

# 🏗 Arquitetura

```text
Anime-Service-MonoRepo
│
├── Anime-Service
│   ├── Controllers
│   ├── Services
│   ├── Repository
│   ├── DTO
│   ├── Mapper
│   └── Model
│
├── User-Service
│   ├── Controllers
│   ├── Services
│   ├── Repository
│   └── Model
│
├── Commons-Core
│   ├── Exceptions
│   ├── Utils
│   ├── Configurations
│   └── Shared Classes
│
└── pg-init-scripts
```

---

# 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven Multi Module
- JUnit 5
- Mockito
- Docker (estrutura preparada)

---

# 📂 Estrutura do Projeto

```
.
├── Anime-Service/
├── Commons-Core/
├── User-Service/
├── pg-init-scripts/
├── pom.xml
└── .envTemplate
```

---

# 📦 Módulos

## 🎌 Anime-Service

Responsável pelo gerenciamento de animes.

### Funcionalidades

- Cadastro
- Atualização
- Exclusão
- Consulta
- Conversão entre DTOs
- Persistência utilizando Spring Data JPA

---

## 👤 User-Service

Responsável pelo gerenciamento dos usuários da aplicação.

Este módulo concentra toda a lógica relacionada aos usuários, mantendo sua implementação desacoplada dos demais serviços.

---

## 📦 Commons-Core

Biblioteca compartilhada utilizada pelos demais módulos.

Exemplos de responsabilidades:

- Classes utilitárias
- Exceções customizadas
- Configurações compartilhadas
- Componentes reutilizáveis

---

# ⚙️ Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL
- Git

---

# ▶️ Executando o projeto

Clone o repositório

```bash
git clone https://github.com/marcelo-sjr/Anime-Service-MonoRepo.git
```

Entre na pasta

```bash
cd Anime-Service-MonoRepo
```

Compile todos os módulos

```bash
mvn clean install
```

Ou execute um módulo específico.

Exemplo:

```bash
cd Anime-Service
mvn spring-boot:run
```

---

# 🛠 Banco de Dados

O projeto possui uma pasta

```
pg-init-scripts/
```

que pode ser utilizada para inicialização do PostgreSQL.

As configurações da aplicação podem ser ajustadas através do arquivo:

```
application.yml
```

e das variáveis de ambiente, o modelo a ser usado está em

```
.envTemplate
```

---

# 🧪 Testes

Executar todos os testes

```bash
mvn test
```

Ou executar somente um módulo

```bash
cd Anime-Service

mvn test
```

---

# 📚 Organização

O projeto segue uma arquitetura baseada em camadas.

```
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

Separando claramente:

- Controllers
- Services
- Repositories
- DTOs
- Entities
- Mappers

---

# 📈 Próximos Passos

- [ ] DockerFile
- [ ] API Gateway
- [ ] Spring Security
- [ ] JWT Authentication
- [ ] Swagger/OpenAPI
- [ ] Testcontainers
- [ ] CI/CD com GitHub Actions
- [ ] Observabilidade
- [ ] Status Check
- [ ] Métricas com Prometheus
- [ ] Redis Cache

---


# 👨‍💻 Autor

**Marcelo Junior**

GitHub

https://github.com/marcelo-sjr

---

# 📄 Licença

Este projeto está licenciado sob a licença MIT.
