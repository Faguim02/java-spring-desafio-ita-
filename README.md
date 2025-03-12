# Sistema de Gerenciamento de Transações

Este é um sistema simples desenvolvido em **Java Spring Boot** para gerenciar transações. Ele permite:
- Armazenar transações com data e hora.
- Limpar todas as transações.
- Ver estatísticas das transações (como soma, média, mínimo, máximo e contagem).

## Funcionalidades

1. **Armazenar Transação**:
    - Recebe a data e hora da transação, além do valor.
    - Valida se a transação ocorreu nos últimos 60 segundos.

2. **Limpar Transações**:
    - Remove todas as transações armazenadas.

3. **Ver Estatísticas**:
    - Retorna estatísticas das transações dos últimos 60 segundos:
        - Soma dos valores.
        - Média dos valores.
        - Valor máximo.
        - Valor mínimo.
        - Contagem de transações.

## Pré-requisitos

Antes de começar, certifique-se de que você tem o seguinte instalado em sua máquina:

- **Java JDK 21**.
- **Maven** (para gerenciamento de dependências).
- **Git** (para clonar o repositório).
- Um navegador ou ferramenta como **Postman** ou **cURL** para testar as APIs.

## Como Executar o Projeto

Siga os passos abaixo para configurar e executar o projeto na sua máquina:

### 1. Clonar repositorio

```bash
git clone https://github.com/Faguim02/java-spring-desafio-ita-.git
cd java-spring-desafio-ita
```

### Executando com o Maven e o java

```bash
mvn clean install
mvn spring-boot:run
```

### Executando com o Docker

```bash
docker build -t gerenciador-de-transacao .
docker run -p 8080:8080 gerenciador-de-transacao
```

## Tecnologias utilizadas

- Java 21
- Spring Web
- Swagger
- JUnit
- Docker