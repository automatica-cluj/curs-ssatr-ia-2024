# Demo Kafka Application

This project demonstrates how to use kafka for sending and consuming messages. The application demonstrate a fan-out like pattern where multiple consumers are listening to the same Kafka topic and each consumer applies a different filter to the messages received from the Kafka topic. For this to work all consumers are in different consumer groups (test-group1, test-group2, test-group3). Name of the topic is `test-topic` and is defined in `application.properties` field `spring.kafka.template.default-topic`.

## Prerequisites

- Java 17
- Maven
- Docker (for running Kafka and Zookeeper)

## Building the Application

To build the application, run the following command in the project root directory:

```sh
mvn clean install
```

## Running Kafka and Zookeeper

To run Kafka and Zookeeper, run the following command in the project root directory:

```sh
docker-compose up
```
This will start Kafka and Zookeeper in Docker containers.

Other useful (optional) commands:

```sh
docker-compose logs kafka
docker-compose logs zookeeper

#login to kafka container and execute command
docker exec -it kafka bash
kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics.sh --list --bootstrap-server localhost:9092
```

To stop Kafka and Zookeeper, run the following command in the project root directory:

```sh
docker-compose down
```


## Running the Application

To run the application, run the following command in the project root directory:

```sh   
mvn spring-boot:run
```

This will start the 3 listeners in different consumer groups (test-group1, test-group2, test-group3) listening to the Kafka topic `test-topic`. An http controller is also started to allow sending messages to the Kafka topic  using a GET request.

## Sending Messages

To send messages to the Kafka topic, make a GET request to `http://localhost:8081` using following curl command:

```sh
curl -X GET "http://localhost:8081/publish?procedureId=456&procedureName=Data%20Backup&status=Confirmed&timestamp=2024-08-29T10:15:30Z"
curl -X GET "http://localhost:8081/publish?procedureId=456&procedureName=Data%20Backup&status=Pending&timestamp=2024-08-29T10:15:30Z"
```

Alternatively you can use the basic web interface to send messages to the Kafka topic. Open a browser and navigate to `http://localhost:8081`. Fill in the fields and click the `Send Message` button to send the message.

Listeners, apply filters on the messages received from the Kafka topic and print the messages on the console.

```
[2] Message processed by listener 2 in 'test-group2: {"procedureId":456,"procedureName":"Data Backup","status":"Confirmed","timestamp":"2024-08-29T10:15:30Z"}
[3] Message processed by listener 3 in 'test-group3': {"procedureId":456,"procedureName":"Data Backup","status":"Confirmed","timestamp":"2024-08-29T10:15:30Z"}
```

Optionaly when a message is sent a key can be provided to the message. This key can be used to partition the messages. The key in Kafka determines how messages are distributed across partitions. Messages with the same key always go to the same partition, ensuring ordering within that partition. Kafka guarantees that any consumer of a given topic-partition will always read that partition's events in exactly the same order as they were written. To send a message with a key, make a POST request to `http://localhost:8081` using following curl command:

```sh
curl -X GET "http://localhost:8081/publish?procedureId=456&procedureName=Data%20Backup&status=Confirmed&timestamp=2024-08-29T10:15:30Z&key=1"
```

More about Kafka keys, partitions and other concepts can be found [here](https://kafka.apache.org/documentation/#intro_topics).
