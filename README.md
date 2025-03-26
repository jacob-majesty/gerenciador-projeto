
# Sistema de Gerenciamento de Portfólio de Projetos

Este projeto visa desenvolver um sistema para gerenciar o portfólio de projetos de uma empresa, permitindo o acompanhamento completo do ciclo de vida de cada projeto, desde a análise de viabilidade até a finalização. O sistema oferece funcionalidades para gerenciamento de equipe, orçamento e risco.

## Requisitos

- **Arquitetura MVC**
- **Spring Boot** como framework principal
- **JPA + Hibernate** para persistência de dados
- **Banco de Dados PostgreSQL**
- **Clean Code** e **Princípios SOLID** aplicados
- **DTOs** e **mapeamento** entre entidades
- **Swagger/OpenAPI** para documentação dos endpoints
- **Tratamento global de exceções**
- **Testes unitários** com cobertura mínima de 70% nas regras de negócio
- **Segurança básica** com **Spring Security** (usuário/senha hardcoded ou em memória)

## Funcionalidades

- CRUD completo de projetos com campos como: nome, data de início, previsão de término, orçamento, descrição, status, e gerente responsável.
- Cálculo dinâmico da classificação de risco do projeto, com base em orçamento e prazo.
- Status fixos do projeto, com transições controladas e sem possibilidade de pular etapas.
- Associação de membros ao projeto (somente membros com a atribuição “funcionário”).
- API REST externa mockada para cadastro e consulta de membros.
- Relatório resumido do portfólio com:
    - Quantidade de projetos por status
    - Total orçado por status
    - Média de duração dos projetos encerrados
    - Total de membros únicos alocados

## Tecnologias

- **Spring Boot**
- **JPA** / **Hibernate**
- **PostgreSQL**
- **Swagger/OpenAPI**
- **Spring Security**
- **Docker**

## Como Rodar o Projeto

### 1. Clonar o repositório:

```bash
git clone <url-do-repositorio>
cd <diretorio-do-repositorio>
```

### 2. Configurar o Banco de Dados:

Crie uma instância do PostgreSQL localmente ou utilize o Docker para configurar rapidamente:

```bash
docker run --name portfolio-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=portfolio -p 5432:5432 -d postgres
```

### 3. Rodar o projeto com Docker:

Execute o comando abaixo para rodar o aplicativo utilizando Docker:

```bash
docker-compose up --build
```

### 4. Acessar a API:

Após iniciar o projeto, você pode acessar os endpoints através de:

- **Swagger**: `http://localhost:8080/swagger-ui.html`
- **API**: `http://localhost:8080/api`
- 
![image](https://github.com/user-attachments/assets/96eb02d9-6873-4eb6-88c9-18ea39dedd3d)


## Testes

Os testes são realizados utilizando JUnit. Para rodá-los, basta executar o seguinte comando:

```bash
mvn test
```
---

