# RestaurantHub

Projeto desenvolvido para a disciplina de Desenvolvimento Backend.

O objetivo foi criar uma API REST para gerenciamento de um restaurante, permitindo cadastrar usuários, produtos, cardápio, estoque, pedidos e pagamentos.

Durante o desenvolvimento procurei aplicar os conceitos vistos na disciplina, utilizando uma arquitetura organizada e boas práticas do Spring Boot.

---

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring Security
- JWT
- BCrypt
- PostgreSQL
- Flyway
- Maven
- Swagger OpenAPI
- Bean Validation

---

## Estrutura do projeto

O projeto foi separado em algumas camadas para facilitar a organização:

```text
src
└── main
    ├── java
    │   └── br
    │       └── com
    │           └── restauranthub
    │               ├── api
    │               │   ├── controller
    │               │   ├── dto
    │               │   └── exception
    │               │
    │               ├── application
    │               │   └── service
    │               │
    │               ├── domain
    │               │   ├── entity
    │               │   ├── enums
    │               │   └── repository
    │               │
    │               └── infrastructure
    │                   ├── config
    │                   └── security
    │
    └── resources
        ├── db
        │   └── migration
        └── application.yaml
```

A ideia foi separar as responsabilidades para deixar o projeto mais organizado e facilitar futuras manutenções.

---

## Funcionalidades

### Usuários

- Cadastro de usuários
- Login utilizando JWT
- Senhas criptografadas com BCrypt
- Controle de acesso por perfil (ADMIN e CLIENTE)

### Produtos

- Cadastro
- Consulta
- Exclusão

### Unidades

- Cadastro
- Consulta
- Exclusão

### Cardápio

- Cadastro
- Consulta
- Exclusão

### Estoque

- Cadastro
- Consulta
- Exclusão

### Pedidos

- Cadastro de pedidos
- Associação com usuário
- Associação com unidade
- Canal do pedido
- Atualização do status
- Consulta por canal

### Itens do Pedido

- Inclusão de produtos no pedido
- Cálculo automático do subtotal

### Pagamentos

- Registro de pagamento
- Valor obtido automaticamente do pedido
- Controle de status
- Simulação de pagamentos aprovados e recusados

---

## Segurança

A autenticação da API é realizada utilizando JWT.

Depois de realizar o login, o token deve ser enviado nas requisições que precisam de autenticação.

```text
Authorization: Bearer SEU_TOKEN
```

O projeto possui dois perfis de usuário:

- `ADMIN`
- `CLIENTE`

Somente usuários com perfil `ADMIN` podem realizar operações de exclusão.

---

## Banco de Dados

Foi utilizado PostgreSQL como banco de dados.

As alterações da estrutura do banco são controladas utilizando Flyway.

As migrations ficam na pasta:

```text
src/main/resources/db/migration
```

Atualmente o projeto possui migrations para criação inicial do banco e alterações feitas durante o desenvolvimento.

---

## Alguns endpoints

### Autenticação

| Método | Endpoint      |
| ------ | ------------- |
| POST   | `/auth/login` |

### Usuários

| Método | Endpoint         |
| ------ | ---------------- |
| GET    | `/usuarios`      |
| GET    | `/usuarios/{id}` |
| POST   | `/usuarios`      |
| DELETE | `/usuarios/{id}` |

### Produtos

| Método | Endpoint         |
| ------ | ---------------- |
| GET    | `/produtos`      |
| GET    | `/produtos/{id}` |
| POST   | `/produtos`      |
| DELETE | `/produtos/{id}` |

### Pedidos

| Método | Endpoint                   |
| ------ | -------------------------- |
| GET    | `/pedidos`                 |
| GET    | `/pedidos/{id}`            |
| GET    | `/pedidos?canalPedido=APP` |
| POST   | `/pedidos`                 |
| PATCH  | `/pedidos/{id}/status`     |
| DELETE | `/pedidos/{id}`            |

### Pagamentos

| Método | Endpoint           |
| ------ | ------------------ |
| GET    | `/pagamentos`      |
| GET    | `/pagamentos/{id}` |
| POST   | `/pagamentos`      |
| DELETE | `/pagamentos/{id}` |

---

## Como executar

Clone o projeto:

```bash
git clone https://github.com/felipesbarbosadev/RestaurantHub.git
```

Entre na pasta do projeto:

```bash
cd RestaurantHub
```

É necessário ter o PostgreSQL instalado.

Crie o banco utilizado pela aplicação:

```sql
CREATE DATABASE restauranthub;
```

As configurações de acesso ao banco e a chave utilizada pelo JWT são definidas através de variáveis de ambiente.

No Linux podem ser configuradas, por exemplo, com:

```bash
export DB_USERNAME="postgres"
export DB_PASSWORD="sua-senha-do-postgres"
export JWT_SECRET="sua-chave-jwt-aqui"
```

Substitua os valores de exemplo de acordo com a configuração do seu ambiente.

Depois execute:

```bash
./mvnw spring-boot:run
```

A aplicação será iniciada por padrão na porta:

```text
http://localhost:8080
```

Para executar os testes do projeto:

```bash
./mvnw clean test
```

As variáveis de ambiente precisam estar configuradas no terminal antes da execução da aplicação ou dos testes.

---

## Swagger

A API também possui documentação utilizando Swagger/OpenAPI.

Depois de iniciar a aplicação, pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

Por ali é possível visualizar os endpoints disponíveis e os DTOs utilizados pela API.

---

## Testes da API

Os testes da API foram feitos utilizando o Postman.

A collection utilizada está disponível na pasta:

```text
postman/RestaurantHub API.postman_collection.json
```

Para executar:

1. Inicie a aplicação.
2. Importe a collection no Postman.
3. Abra a pasta `Testes do Edital`.
4. Execute os testes utilizando o Collection Runner.

Foram criados testes para os principais fluxos da aplicação, incluindo:

- login e geração do JWT;
- acesso a rotas autenticadas;
- criação de pedidos;
- inclusão de itens no pedido;
- pagamento aprovado;
- pagamento recusado;
- acesso sem autenticação retornando `401`;
- acesso sem permissão retornando `403`;
- validação de dados inválidos retornando `400`;
- busca de recurso inexistente retornando `404`.

Algumas requisições auxiliares também são executadas durante os testes para gerar os dados necessários para os próximos cenários.

O Collection Runner foi configurado para reaproveitar os IDs e tokens gerados durante a execução, assim não precisa ficar alterando esses valores manualmente a cada teste.

---

## O que aprendi durante o projeto

Durante esse projeto consegui praticar vários conceitos importantes do desenvolvimento backend, como:

- arquitetura em camadas;
- criação e utilização de DTOs;
- relacionamento entre entidades;
- repositories e services;
- Spring Security;
- autenticação utilizando JWT;
- criptografia de senhas;
- tratamento global de exceções;
- Bean Validation;
- migrations com Flyway;
- integração com PostgreSQL;
- documentação com Swagger;
- testes de API utilizando Postman.

Foi um projeto que me ajudou bastante a entender como essas tecnologias trabalham juntas em uma API REST e também entender melhor a separação de responsabilidades dentro do projeto.

---

## Autor

Felipe Barbosa

Projeto desenvolvido para fins de estudo na disciplina de Desenvolvimento Backend.