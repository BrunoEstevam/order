<h1 align="center">
  Order 
</h1>

<p align="center">
  <img src="https://img.shields.io/static/v1?label=Tipo&message=Desafio&color=8257E5&labelColor=000000" alt="Desafio" />
</p>

---

## Tecnologias

- **Java 21**
- **Spring Boot**
- **Banco de dados Postgres**
- **Cache com Redis**
- **RabbitMQ**
- **Swagger**
- **Design Pattern Strategy**
- **Design Pattern Factory Method**
- **Princípios SOLID**
- **Docker**
- **Idempotência**

---

## Como Executar

1. **Clonar o repositório**:
    ```bash
    git clone https://github.com/BrunoEstevam/order.git
    ```

2. **Subir as dependências do projeto** (Banco de dados, Redis e RabbitMQ) utilizando Docker Compose:
    ```bash
    docker compose -f 'docker-compose.yml' up -d --build
    ```

3. **Executar a aplicação Spring Boot**.

4. **Acessar a aplicação** em `http://localhost:8080/*`.

---

### Descrição das soluções

A aplicação utiliza o padrão de design Strategy, onde a classe responsável pelo processamento do pedido é selecionada com base no seu status. Isso permite que a lógica de processamento seja facilmente alterada ou estendida, possibilitando a adição de novas etapas, como Expedição, Cancelamento, Pagamento, entre outras, ou a modificação das etapas existentes no fluxo do pedido, sem causar impactos significativos no código. Essa abordagem proporciona maior flexibilidade, facilitando a manutenção e adaptação da aplicação a novas demandas ou requisitos no futuro.

Optei por uma arquitetura em camadas (Controller, Service e Repository) por oferecer organização e modularidade. A separação em camadas define claramente as responsabilidades de cada uma, evitando abstrações desnecessárias. Como a aplicação é relativamente simples, essa estrutura agiliza o desenvolvimento e facilita a manutenção, além de permitir uma migração fácil para abordagens mais robustas, como a Clean Architecture, conforme a necessidade.

<h4> Banco de dados </h4>

O desafio consistia em desenvolver uma aplicação capaz de suportar até 200 mil pedidos por dia.

Foi considerado um volume de 400 mil pedidos diários, o que resultaria em um total de aproximadamente 4,63 transações por segundo (TPS). Com essa quantidade de transações, não há necessidade de utilizar um banco de dados não relacional.

O serviço utiliza de índices em bancos de dados para melhorar a performance das consultas, permitindo que o sistema encontre dados mais rapidamente sem precisar escanear todas as linhas das tabelas.

Dessa forma, a escolha por um banco relacional foi preferida devido aos seguintes fatores:

<table border="1">
  <thead>
    <tr>
      <th>Benefício</th>
      <th>Descrição</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Estrutura de Dados Estruturada</strong></td>
      <td>Bancos relacionais organizam dados em tabelas com esquemas predefinidos, facilitando a estruturação e organização.</td>
    </tr>
    <tr>
      <td><strong>Integridade dos Dados</strong></td>
      <td>Garantem a integridade dos dados por meio de restrições, como chaves primárias e estrangeiras, e regras de normalização.</td>
    </tr>
    <tr>
      <td><strong>Suporte a Transações ACID</strong></td>
      <td>Garantem propriedades ACID (Atomicidade, Consistência, Isolamento e Durabilidade) para transações, garantindo alta confiabilidade.</td>
    </tr>
    <tr>
      <td><strong>Consultas Complexas</strong></td>
      <td>Oferecem suporte avançado para consultas complexas com SQL, incluindo junções (JOINs), agregações e subconsultas.</td>
    </tr>
    <tr>
      <td><strong>Segurança e Controle de Acesso</strong></td>
      <td>Possuem robustos mecanismos de controle de acesso e segurança, com suporte a permissões detalhadas para diferentes níveis de usuário.</td>
    </tr>
    <tr>
      <td><strong>Maturidade e Estabilidade</strong></td>
      <td>Bancos relacionais são amplamente utilizados, com décadas de desenvolvimento, garantindo uma plataforma confiável e estável.</td>
    </tr>
    <tr>
      <td><strong>Ferramentas e Suporte</strong></td>
      <td>Existem muitas ferramentas e recursos de suporte para bancos de dados relacionais, como backups, ferramentas de administração e otimização de consultas.</td>
    </tr>
  </tbody>
</table>

<h4> Comunicação </h4>

Quando a aplicação recebe uma requisição POST /order de um serviço externo, ela primeiro verifica no Redis se aquele pedido já foi processado para evitar duplicidade. Caso seja um novo pedido, a aplicação o salva no banco de dados PostgreSQL e, em seguida, o coloca em uma fila do RabbitMQ para processamento assíncrono. Um consumidor chamado "Order Processor" lê essa fila, processa o pedido e pode se comunicar com um serviço externo através do RabbitMQ. Após o processamento, a aplicação atualiza o banco de dados com o resultado, garantindo que o status do pedido reflita corretamente o que aconteceu no fluxo.

Para a comunicação com o serviço B, foi pensado em uma mensageria com RabbitMQ, proporcionando mais segurança para reprocessamentos e tolerância a falhas.

<h4> Demais pontos </h4>

Para resolver o problema de duplicidade de pedidos, foi aplicado o conceito de Idempotência e utilizado @Version para controle de concorrência

Para garantir a alta disponibilidade da aplicação, podemos optar por um serviço em cloud com auto scaling. Esse recurso permite que a aplicação se ajuste automaticamente à demanda, escalando os recursos para cima ou para baixo conforme necessário, garantindo desempenho ideal mesmo em picos de tráfego, e otimizando os custos durante períodos de baixa demanda.

O cache Redis foi pensado para compartilhamento entre diversas instâncias.

## Arquitetura
![Desenho de Arquitetura](./src/main/resources/arch.png)



### Observações

- Certifique-se de ter o **Docker** instalado e configurado corretamente.
- Dentro da pasta de resource terá uma collection do postman com todas as chamadas REST
- O Swagger estará disponível para documentação da API em `http://localhost:8080/swagger-ui/index.html#/`.
