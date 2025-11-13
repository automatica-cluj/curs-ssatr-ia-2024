# Drone Fleet Management System - Educational Demo

An educational Java application demonstrating **Apache Kafka** and **multi-threading** concepts through a realistic drone fleet simulation scenario.

## Overview

This application simulates a drone fleet management system where:
- **Multiple drones** (Kafka producers) run in separate threads, sending telemetry and alerts
- A **Coordination Station** (Kafka consumer) monitors all drones in real-time
- A **Command Center** (Kafka producer) can send commands to specific drones

## Educational Objectives

This demo teaches students about:

1. **Apache Kafka Fundamentals**
   - Producer/Consumer patterns
   - Topics and partitions
   - Message serialization (JSON)
   - Producer acknowledgments and retries

2. **Multi-Threading Concepts**
   - Creating and managing threads
   - Thread-safe data structures (`ConcurrentHashMap`, `AtomicBoolean`)
   - Proper thread lifecycle management
   - Graceful shutdown handling

3. **Real-World Architecture**
   - Event-driven systems
   - IoT-like communication patterns
   - Distributed systems basics
   - Microservices messaging

## Architecture

```
┌─────────────────┐
│   DRONE-001     │───┐
│  (Thread + P)   │   │
└─────────────────┘   │
                      │    ┌──────────────────┐
┌─────────────────┐   │    │  Kafka Topics    │
│   DRONE-002     │───┼───▶│  - telemetry     │
│  (Thread + P)   │   │    │  - alerts        │
└─────────────────┘   │    │  - commands      │
                      │    └──────────────────┘
┌─────────────────┐   │              │
│   DRONE-003     │───┘              │
│  (Thread + P)   │                  │
└─────────────────┘                  │
                                     │
        ┌────────────────────────────┼────────────────┐
        │                            │                │
        ▼                            ▼                ▼
┌──────────────────┐      ┌──────────────────┐   (future)
│ Coordination     │      │ Command Center   │
│   Station (C)    │      │    (P)           │
└──────────────────┘      └──────────────────┘

P = Producer, C = Consumer
```

## Components

### 1. Message Models (`model` package)
- **TelemetryMessage**: Position, altitude, battery, status
- **AlertMessage**: Critical events (low battery, mission complete, etc.)
- **CommandMessage**: Commands from station to drones
- **Enums**: DroneStatus, AlertType, AlertSeverity, CommandType

### 2. Drone Simulator (`drone` package)
- **DroneSimulator**: Runnable that simulates a drone's lifecycle
  - Takeoff → Flight → Return → Landing
  - Sends telemetry every 3 seconds
  - Sends alerts for critical events
  - Each drone runs in its own thread

### 3. Coordination Station (`station` package)
- **CoordinationStation**: Kafka consumer monitoring all drones
  - Consumes from telemetry and alert topics
  - Displays real-time fleet status
  - Tracks drone information in thread-safe structures
  - Runs in a separate thread

### 4. Command Center (`command` package)
- **CommandCenter**: Sends commands to drones
  - Return to base, emergency land, hover, etc.
  - Demonstrates targeted messaging via Kafka keys

### 5. Main Application
- **DroneFleetApplication**: Orchestrates all components
  - Manages thread lifecycle
  - Handles graceful shutdown
  - Demonstrates proper resource cleanup

## Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Docker & Docker Compose** (for Kafka)

## Quick Start

### 1. Start Kafka

```bash
cd demo-kafka-drones
docker-compose up -d
```

This starts:
- Zookeeper (port 2181)
- Kafka (port 9092)
- Kafka UI (optional, port 8080)

Verify Kafka is running:
```bash
docker-compose ps
```

### 2. Build the Application

```bash
mvn clean package
```

### 3. Run the Application

```bash
mvn exec:java
```

Or with compiled JAR:
```bash
java -jar target/demo-kafka-drones-1.0-SNAPSHOT.jar
```

### 4. Watch the Demo

The application will:
1. Start 3 drone simulators in separate threads
2. Each drone will takeoff, fly, and eventually land
3. The Coordination Station displays real-time fleet status
4. Alerts are shown for events (low battery, mission complete)
5. Command Center demonstrates sending commands

### 5. Monitor with Kafka UI (Optional)

Open http://localhost:8080 to see:
- Topics: `drone-telemetry`, `drone-alerts`, `drone-commands`
- Messages flowing in real-time
- Consumer groups

### 6. Stop the Application

Press `Ctrl+C` to trigger graceful shutdown.

Stop Kafka:
```bash
docker-compose down
```

## Configuration

Edit `DroneFleetApplication.java` to customize:
- `NUM_DRONES`: Number of drones to simulate (default: 3)
- `TELEMETRY_INTERVAL_MS`: How often drones send telemetry (default: 3000ms)

Edit `KafkaConfigUtil.java` for Kafka settings:
- Bootstrap servers
- Producer/consumer properties

## Understanding the Code

### Multi-Threading Example

Each drone runs in its own thread:

```java
DroneSimulator drone = new DroneSimulator(...);
Thread droneThread = new Thread(drone, "DRONE-001");
droneThread.start();
```

### Thread Safety

The Coordination Station uses thread-safe structures:

```java
private final Map<String, DroneInfo> droneFleet = new ConcurrentHashMap<>();
private final AtomicBoolean running = new AtomicBoolean(true);
```

### Kafka Producer

Drones send messages asynchronously:

```java
ProducerRecord<String, String> record =
    new ProducerRecord<>(telemetryTopic, droneId, json);

producer.send(record, (metadata, exception) -> {
    // Callback for send result
});
```

### Kafka Consumer

Station polls for messages:

```java
ConsumerRecords<String, String> records =
    consumer.poll(Duration.ofMillis(1000));

for (ConsumerRecord<String, String> record : records) {
    processMessage(record);
}
```

## Topics

| Topic | Producer | Consumer | Purpose |
|-------|----------|----------|---------|
| `drone-telemetry` | Drones | Coordination Station | Regular telemetry data |
| `drone-alerts` | Drones | Coordination Station | Critical alerts |
| `drone-commands` | Command Center | Drones (future) | Commands to drones |

## Extending the Demo

Ideas for students to enhance the application:

1. **Implement Command Listening**: Make drones consume from `drone-commands` topic
2. **Add More Drone Behaviors**: Weather response, collision avoidance
3. **Persistent Storage**: Save telemetry to database
4. **REST API**: Add HTTP endpoints to control drones
5. **Visualization**: Create a web dashboard showing drone positions
6. **Multiple Consumer Groups**: Add different monitoring stations
7. **Error Handling**: Simulate network failures, recovery
8. **Metrics**: Add performance monitoring (latency, throughput)

## Learning Exercises

### Exercise 1: Modify Drone Behavior
Change `DroneSimulator.java` to make drones:
- Fly in a specific pattern (circle, square)
- Respond to weather conditions
- Have different flight speeds

### Exercise 2: Add New Message Types
Create a new message type for:
- Mission waypoints
- Camera captures
- Sensor readings

### Exercise 3: Implement Command Response
Make drones listen to commands and:
- Parse command messages
- Update their behavior accordingly
- Send acknowledgment messages

### Exercise 4: Multiple Coordination Stations
Create multiple consumer groups to:
- Monitor different drone subsets
- Apply different filtering logic
- Demonstrate Kafka consumer groups

## Troubleshooting

### Connection Refused to Kafka
- Ensure Docker containers are running: `docker-compose ps`
- Wait 30 seconds after starting Kafka for it to initialize
- Check Kafka logs: `docker-compose logs kafka`

### OutOfMemoryError
- Reduce `NUM_DRONES` in `DroneFleetApplication.java`
- Increase Java heap: `java -Xmx2g -jar ...`

### Messages Not Appearing
- Check topic creation: `docker exec -it drone-kafka kafka-topics --list --bootstrap-server localhost:9092`
- Verify producer/consumer configs in `KafkaConfigUtil.java`

## Useful Kafka Commands

List topics:
```bash
docker exec -it drone-kafka kafka-topics --list --bootstrap-server localhost:9092
```

Describe a topic:
```bash
docker exec -it drone-kafka kafka-topics --describe --topic drone-telemetry --bootstrap-server localhost:9092
```

Console consumer (read messages):
```bash
docker exec -it drone-kafka kafka-console-consumer --topic drone-telemetry --from-beginning --bootstrap-server localhost:9092
```

## Architecture Patterns Demonstrated

1. **Producer-Consumer Pattern**: Decoupled message producers and consumers
2. **Pub-Sub Pattern**: Multiple consumers can listen to same topic
3. **Event Sourcing**: All events are logged in Kafka
4. **CQRS**: Separation of command (Command Center) and query (Station)
5. **Thread Pool Pattern**: Multiple worker threads processing messages

## References

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [Jackson JSON Documentation](https://github.com/FasterXML/jackson-docs)

## License

Educational use only. Feel free to modify and extend for learning purposes.

## Author

Created for SSATR course - Technical University of Cluj-Napoca

---

**Note**: This is a simulation for educational purposes. Real drone systems require additional safety protocols, communication standards (MAVLink), and regulatory compliance.
