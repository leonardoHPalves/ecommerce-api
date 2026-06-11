# 🛒 Ecommerce API

API RESTful de e-commerce desenvolvida com Java e Spring Boot, com autenticação JWT, documentação Swagger e banco de dados PostgreSQL.

-----

## 🚀 Tecnologias utilizadas

- *Java 21*
- *Spring Boot*
- *Spring Security*
- *JWT (JSON Web Token)*
- *Spring Data JPA / Hibernate*
- *PostgreSQL*
- *Lombok*
- *Swagger / OpenAPI 3*
- *Maven*

-----

## 📋 Funcionalidades

- ✅ Cadastro e autenticação de usuários com JWT
- ✅ CRUD completo de produtos
- ✅ Criação e listagem de pedidos
- ✅ Paginação na listagem de produtos
- ✅ Validação de dados nas requisições
- ✅ Tratamento global de erros
- ✅ Documentação interativa com Swagger UI

-----

## 🏗️ Estrutura do projeto


src/main/java/com/leo/ecommerce_api/
├── config/
│   ├── ConfigEcommerce.java       # Configuração do Spring Security
│   └── SwaggerConfig.java         # Configuração do Swagger
├── controller/
│   ├── AuthController.java        # Endpoints de autenticação
│   ├── ControllerEcommerce.java   # Endpoints de produtos
│   └── PedidoController.java      # Endpoints de pedidos
├── dto/
│   ├── DtoEcommerce.java          # DTO de produto
│   ├── LoginDto.java              # DTO de login
│   ├── RegisterDto.java           # DTO de cadastro
│   └── PedidoDto.java             # DTO de pedido
├── entity/
│   ├── EntityEcommerce.java       # Entidade Produto
│   ├── Usuario.java               # Entidade Usuário
│   └── Pedido.java                # Entidade Pedido
├── exception/
│   └── GlobalExceptionHandler.java # Tratamento global de erros
├── repository/
│   ├── RepositoryEcommerce.java   # Repositório de produtos
│   ├── UsuarioRepository.java     # Repositório de usuários
│   └── PedidoRepository.java      # Repositório de pedidos
├── security/
│   ├── JwtUtil.java               # Geração e validação de tokens JWT
│   └── JwtFilter.java             # Filtro de autenticação JWT
└── service/
├── ServiceEcommerce.java      # Serviço de produtos
├── AuthService.java           # Serviço de autenticação
└── PedidoService.java         # Serviço de pedidos


-----

## ⚙️ Como rodar o projeto

### Pré-requisitos

- Java 21+
- Maven
- PostgreSQL

### 1. Clone o repositório

bash
git clone https://github.com/leonardoHPalves/ecommerce-api
cd ecommerce-api


### 2. Configure o banco de dados

Crie um banco de dados no PostgreSQL:

sql
CREATE DATABASE ecommerce;


### 3. Configure o application.properties

Copie o arquivo de exemplo e preencha com seus dados:

bash
cp src/main/resources/application.properties.example src/main/resources/application.properties


Edite o arquivo com suas configurações:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
jwt.secret=sua_chave_secreta_com_no_minimo_32_caracteres


### 4. Rode a aplicação

bash
./mvnw spring-boot:run


A aplicação estará disponível em http://localhost:8080

-----

## 📖 Documentação

Acesse a documentação interativa pelo Swagger UI:


http://localhost:8080/swagger-ui/index.html


-----

## 🔐 Autenticação

A API usa JWT para autenticação. Para acessar endpoints protegidos:

### 1. Registre um usuário

http
POST /auth/register
Content-Type: application/json

{
"nome": "Seu Nome",
"email": "seu@email.com",
"senha": "sua_senha"
}


### 2. Faça login e obtenha o token

http
POST /auth/login
Content-Type: application/json

{
"email": "seu@email.com",
"senha": "sua_senha"
}


### 3. Use o token nas requisições

http
GET /produtos
Authorization: Bearer SEU_TOKEN_AQUI


-----

## 📌 Endpoints

### Autenticação

|Método|Endpoint        |Descrição               |
|------|----------------|------------------------|
|POST  |/auth/register|Cadastrar usuário       |
|POST  |/auth/login   |Login e geração de token|

### Produtos

|Método|Endpoint        |Descrição                 |
|------|----------------|--------------------------|
|GET   |/produtos     |Listar produtos (paginado)|
|GET   |/produtos/{id}|Buscar produto por ID     |
|POST  |/produtos     |Criar produto             |
|PUT   |/produtos/{id}|Atualizar produto         |
|DELETE|/produtos/{id}|Deletar produto           |

### Pedidos

|Método|Endpoint               |Descrição                 |
|------|-----------------------|--------------------------|
|GET   |/pedidos             |Listar todos os pedidos   |
|GET   |/pedidos/{id}        |Buscar pedido por ID      |
|GET   |/pedidos/usuario/{id}|Listar pedidos por usuário|
|POST  |/pedidos             |Criar pedido              |

-----

## 📦 Exemplo de requisições

### Criar produto

http
POST /produtos
Authorization: Bearer SEU_TOKEN
Content-Type: application/json

{
"nome": "Notebook",
"descricao": "Notebook Gamer i7",
"preco": 4500.00,
"estoque": 10
}


### Criar pedido

http
POST /pedidos
Authorization: Bearer SEU_TOKEN
Content-Type: application/json

{
"usuarioId": 1,
"produtoIds": [1, 2]
}


-----

## 👨‍💻 Autor

Feito por *Leonardo* — projeto desenvolvido para portfólio.

https://www.linkedin.com/in/leonardo-h-p-alves-a5308b331/
https://github.com/leonardoHPalves
