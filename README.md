# modeler-service Project

Intelliflow backend support project. <br/>
This project uses Quarkus, the Supersonic Subatomic Java Framework.

Supporting frameworks added are:<br/>
RESTEasy Reactive <br/>
RESTEasy Reactive Jackson <br/>
SmallRye Reactive Messaging - Kafka Connector <br/>
DataStax Apache Cassandra client <br/>
Eclipse Vert.x <br/>


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

##Creating a local Cassandra Docker Instance

Use the following commands to create a Cassandra instance in your docker
```shell script
docker run --name local-cassandra-instance -p 9042:9042 -d cassandra
```
Create a keyspace and table
```shell script
docker exec -it local-cassandra-instance cqlsh -e "CREATE KEYSPACE IF NOT EXISTS k1 WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}"
docker exec -it local-cassandra-instance cqlsh -e "CREATE TABLE k1.file_store (
    name text,
    orderkey int,
    id uuid,
    partition blob,
    status text,
    PRIMARY KEY (name, orderkey)
) WITH CLUSTERING ORDER BY (orderkey ASC)"
```
