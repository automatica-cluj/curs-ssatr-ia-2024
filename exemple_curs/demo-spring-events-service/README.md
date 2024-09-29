# Demo Java Service ISP 2024

## Application description

This is a demo Java service for the ISP 2024 course. 
- It demonstrate creation of a simple HTTP API (see `TicketsController`).
- It demonstrate how to use REST repositories (see `EventTicketRepository` and `PurchasedTicketRepository`).
- Database access is done using Spring Data JPA. A MySQL database is used.
- Testing is done using JUnit and H2 in-memory database.
- The service is containerized using Docker.
- The service is deployed using Docker Compose.

For running the application, you need to have Docker installed on your machine. When application is run using Docker Compose, it will start a MySQL database and the Java service. Details of the configuraiotns could be found in the `compose.yaml` file.

### 

Testing the application with curl:

#### Create a new event ticket
```curl -X POST -H "Content-type: application/json" -d "{\"eventName\":\"Concert\",\"ticketPrice\":100.0}" http://localhost:8080/eventTickets```

#### Get all event tickets
```curl http://localhost:8080/eventTickets```

#### Get event ticket by id
```curl http://localhost:8080/eventTickets/1```

#### Buy a ticket
```curl -X POST -H "Content-type: application/json" -d "{\"eventTicketId\": 2, \"buyerName\": \"John Doe\", \"isCheckedIn\": false}" http://localhost:8080/purchasedTickets```

#### Get all purchased tickets
```curl http://localhost:8080/purchasedTickets```

#### Get purchased ticket by id
```curl http://localhost:8080/purchasedTickets/1```

#### Checkin a ticket
```curl -X POST -H "Content-type: application/json" -d "{\"ticketId\": \"your_ticket_id\"}" "http://localhost:8080/tickets/checkin?ticketId=1"```


### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Rest Repositories](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#howto.data-access.exposing-spring-data-repositories-as-rest)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#features.docker-compose)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing JPA Data with REST](https://spring.io/guides/gs/accessing-data-rest/)
* [Accessing Neo4j Data with REST](https://spring.io/guides/gs/accessing-neo4j-data-rest/)
* [Accessing MongoDB Data with REST](https://spring.io/guides/gs/accessing-mongodb-data-rest/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)



