# API - Serviços sem Kafka
- REST Ofertas Cartao Credito
- REST Seguro de Vida
- REST Emprestimos
- REST HintService
- Contratos

# Worker
- Ordenador (REST / Kafka)

# Filas
- request
- response
- hintin
- hintout
- ccin
- ccout
- segvidain
- segvidaout
- empresin
- empresout

# Roundtrip

- Receber uma chamada REST -> Ordenador
- Produzir para fila "input" HintService
- Consumir fila "output" HintService
- Produzir quais filas vai processar (3)
- Consumir filas de "input" das API (3)
- Chamar backend (3)
- Produzir filas de "output" das API (3)
- Agregar resultados de 3 filas para uma fila (responder na ordem do HintService)
- Responder REST <- Ordenador

# Docker
- Chamada do Kafka já cria as filas

```shell script
docker run -d --rm -p 2181:2181 --name zookeeper -e KAFKA_ADVERTISED_HOST_NAME=zookeeper wurstmeister/zookeeper
docker run -d --rm -p 9092:9092 --name kafka --link zookeeper -e KAFKA_ADVERTISED_HOST_NAME=192.168.99.100 -e KAFKA_ADVERTISED_PORT=9092 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_CREATE_TOPICS=request:1:1:delete,response:1:1:delete,hintin:1:1:delete,hintout:1:1:delete,ccin:1:1:delete,ccout:1:1:delete,segvidain:1:1:delete,segvidaout:1:1:delete,empresin:1:1:delete,empresout:1:1:delete wurstmeister/kafka
```

```shell script
mvn install -f contratos/pom.xml

mvn spring-boot:run -f hintservice/pom.xml &
mvn spring-boot:run -f emprestimos/pom.xml &
mvn spring-boot:run -f ofertacc/pom.xml &
mvn spring-boot:run -f segurovida/pom.xml &
mvn spring-boot:run -f orquestrador/pom.xml &

```

# Chamada Orquestrador
```http request
POST http://localhost:8080/rs/orquestrador
Accept: application/json
Content-Type: application/json

{
  "id": 1
}

```
