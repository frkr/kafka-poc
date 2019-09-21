# 1 - Backend API
- 1.1 - Ofertas Cartao Credito
- 1.2 - Seguro de Vida
- 1.3 - Emprestimos

# 2 - Orquestrador
- 2.1 Consumir "input" Orquestrador
- 2.2 Produzir "output" Orquestrador

# 3 - Dealer (Roundtrip)

- 3.1 Receber uma chamada REST
- 3.2 Produzir para fila "input" Orquestrador
- 3.3 Consumir fila "output" Orquestrador
- 3.3.1 Produzir quais filas vai processar (3)
- 3.3.2 Consumir filas de "input" das API (3)
- 3.3.3 Chamar backend (3)
- 3.3.4 Produzir filas de "output" das API (3)
- 
- 3.3.5 Agregar resultados de 3 filas para uma fila (responder na ordem do Orquestrador)
- 
- 3.4 Responder REST

### Comandos

```shell script

docker rm -f zookeeper
docker rm -f kafka
docker run -d --rm -p 2181:2181 --name zookeeper -e KAFKA_ADVERTISED_HOST_NAME=zookeeper wurstmeister/zookeeper
docker run -d --rm -p 9092:9092 --name kafka --link zookeeper -e KAFKA_ADVERTISED_HOST_NAME=192.168.99.100 -e KAFKA_ADVERTISED_PORT=9092 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_CREATE_TOPICS="mytopic:1:1:delete" wurstmeister/kafka
docker logs -f kafka


```
