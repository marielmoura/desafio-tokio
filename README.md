# Financial Transfer Scheduler

## Descrição
Este projeto é uma aplicação para agendamento de transferências financeiras. O objetivo é fornecer uma solução simples e eficiente para gerenciar transferências programadas, com foco em boas práticas de desenvolvimento e arquitetura.

## Decisões Arquiteturais
- **Arquitetura baseada em camadas**: A aplicação foi estruturada em camadas (ex.: domínio, aplicação, infraestrutura) para garantir separação de responsabilidades e facilitar a manutenção.
- **Uso de padrões de design**: Foram aplicados padrões como Repository e Service para organizar o código e promover reutilização.
- **Banco de dados em memória**: Utilizamos o banco de dados H2 em memória para simplificar o desenvolvimento e os testes, garantindo rapidez e facilidade de configuração.
- **API RESTful**: A comunicação com o cliente é feita por meio de uma API RESTful, seguindo boas práticas de design de APIs.

## Ferramentas e Tecnologias
- **Linguagem**: Java
- **Framework**: Spring
- **Banco de Dados**: H2
- **Gerenciador de Dependências**: Maven
- **Outras Ferramentas**: Docker, Docker Compose, Swagger

## Instruções para Subida do Projeto
1. Clone o repositório:
	```bash
	git clone https://github.com/seu-usuario/financial-transfer-scheduler.git
	cd financial-transfer-scheduler
	```

2. Suba o ambiente com Docker Compose:
	```bash
	docker-compose up --build
	```

3. Acesse a aplicação:
	- A API estará disponível em: `http://localhost:8080`

4. Token
	- Use o token gerado pelo endpoint de autenticação para acessar os endpoints

## Testes
Para executar os testes, utilize o comando:
```bash
mvn test
```

## Endpoints

### POST /auth/login
Realiza a autenticação do usuário.

- **Descrição**: Permite que um usuário faça login e receba um token JWT para autenticação.
- **Request Body**:
	```json
	{
		"username": "string", //admin para teste
		"password": "string" //admin para teste
	}
	```
- **Response**:
	- **Status 200 (OK)**:
		```json
		"string" // Token JWT gerado
		```
	- **Status 401 (Unauthorized)**:
		```json
		"Invalid credentials"
		```

### POST /transfers
Agenda uma transferência financeira.

### Exemplo de Requisição com cURL

```bash
curl --location 'http://localhost:8080/transfers' \
--header 'Authorization: Bearer <seu_token_jwt>' \
--header 'Content-Type: application/json' \
--data-raw '{
	"sourceAccount": "123456",
	"destinationAccount": "654321",
	"amount": 100.50,
	"transferDate": "2023-12-01"
}'
```

- **Nota**: Substitua `<seu_token_jwt>` pelo token JWT obtido no endpoint de autenticação.
- **Descrição**: Permite agendar uma nova transferência financeira.
- **Request Body**:
	```json
	{
		"sourceAccount": "string",
		"destinationAccount": "string",
		"amount": "number",
		"transferDate": "yyyy-MM-dd"
	}
	```
- **Response**:
	- **Status 201 (Created)**:
		```json
		{
			"id": "string",
			"sourceAccount": "string",
			"destinationAccount": "string",
			"amount": "number",
			"transferDate": "yyyy-MM-dd",
			"creationDate": "yyyy-MM-dd'T'HH:mm:ss"
		}
		```

### GET /transfers
Lista todas as transferências agendadas.

### Exemplo de Requisição com cURL

```bash
curl --location 'http://localhost:8080/transfers' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <seu_token_jwt>'
```

- **Nota**: Substitua `<seu_token_jwt>` pelo token JWT obtido no endpoint de autenticação.
- **Descrição**: Permite agendar uma nova transferência financeira.
- **Request Body**:
	```json
	{
		"sourceAccount": "string",
		"destinationAccount": "string",
		"amount": "number",
		"transferDate": "yyyy-MM-dd"
	}
	```
- **Response**:
	- **Status 201 (Created)**:
		```json
		{
			"id": "string",
			"sourceAccount": "string",
			"destinationAccount": "string",
			"amount": "number",
			"transferDate": "yyyy-MM-dd",
			"creationDate": "yyyy-MM-dd'T'HH:mm:ss"
		}
		```

- **Descrição**: Retorna uma lista paginada de transferências financeiras agendadas.
- **Query Parameters**:
	- `page` (opcional): Número da página.
	- `size` (opcional): Tamanho da página.
- **Response**:
	- **Status 200 (OK)**:
		```json
		{
			"content": [
				{
					"id": "string",
					"sourceAccount": "string",
					"destinationAccount": "string",
					"amount": "number",
					"transferDate": "yyyy-MM-dd",
					"creationDate": "yyyy-MM-dd'T'HH:mm:ss"
				}
			],
			"pageable": {
				"pageNumber": "number",
				"pageSize": "number"
			},
			"totalElements": "number",
			"totalPages": "number"
		}
		```
