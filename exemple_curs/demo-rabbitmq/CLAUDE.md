# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a RabbitMQ fanout exchange pattern demonstration project. It implements a producer-consumer messaging system where one producer sends JSON messages to multiple consumers through a fanout exchange.

## Architecture

**Message Flow:**
```
Producer → Fanout Exchange → Multiple Queues → Multiple Consumers
```

- **FanoutProducer** (demo.fanout.FanoutProducer): Reads `data.json`, updates timestamp and procedureName fields, and publishes 100 JSON messages to `fanout_exchange` with 1-second intervals
- **FanoutConsumer** (demo.fanout.FanoutConsumer): Hardcoded queue name `demo_ssatr1` (command-line args currently commented out)
- **FanoutConsumer2** (demo.fanout.FanoutConsumer2): Hardcoded queue name `demo_ssatr2` (duplicate of FanoutConsumer with different queue)

**Key Design Decisions:**
- Exchange name is hardcoded as `fanout_exchange` in all components
- Queues are durable (persist across restarts) and created when consumer starts
- Messages are marked as persistent (`MessageProperties.PERSISTENT_TEXT_PLAIN`) to survive broker restarts
- **Note:** The producer currently has a duplicate `basicPublish` call on line 46 of FanoutProducer.java
- The `data.json` file in the project root serves as the message template
- Producer modifies the JSON by appending `+<iteration>` to `procedureName` and updating timestamp for each of the 100 messages

## Prerequisites

**RabbitMQ Setup:**
```bash
# Pull and run RabbitMQ with management console
docker pull rabbitmq:3-management
docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 5672:5672 rabbitmq:3-management

# Or restart existing container
docker start some-rabbit
```

Access management console at http://localhost:8080/ (credentials: guest/guest)

## Build Commands

```bash
# Build the project (creates executable JAR with dependencies)
mvn clean package

# Output: target/demo-rabbitmq-1.0-SNAPSHOT.jar
```

The Maven Shade plugin packages all dependencies into a single JAR for easy execution.

## Running the Demo

**Start Consumers (in separate terminals):**
```bash
# Consumer 1 (uses hardcoded queue: demo_ssatr1)
java -cp target/demo-rabbitmq-1.0-SNAPSHOT.jar demo.fanout.FanoutConsumer

# Consumer 2 (uses hardcoded queue: demo_ssatr2)
java -cp target/demo-rabbitmq-1.0-SNAPSHOT.jar demo.fanout.FanoutConsumer2
```

**Note:** The original design intended command-line queue name arguments, but the current implementation has this commented out in favor of hardcoded queue names. To use command-line args, uncomment lines 8-13 in FanoutConsumer.java and line 15 should use `argv[0]`.

**Start Producer:**
```bash
java -cp target/demo-rabbitmq-1.0-SNAPSHOT.jar demo.fanout.FanoutProducer
```

The producer sends 100 messages with 1-second delays and then exits. Consumers continue running until manually stopped.

**Important Notes:**
- Start at least one consumer before the producer to create queues (first-time setup)
- If consumers aren't running, messages persist in queues for later delivery
- Messages sent before any consumer has started will be lost (no queues exist yet)
- The producer sends 100 messages in a loop, each with an updated timestamp and modified procedureName

## Testing

```bash
mvn test
```

Note: Tests use JUnit 3.8.1 (legacy version).

## Dependencies

- **RabbitMQ AMQP Client 5.13.0**: Core messaging functionality
- **Jackson Databind 2.13.3**: JSON parsing and manipulation
- RabbitMQ server must be running on localhost:5672
