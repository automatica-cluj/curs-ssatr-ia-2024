# Demo RabbitMQ and Fanout Exchange Pattern

## Description

This project demonstrates the RabbitMQ **fanout exchange pattern**, a pub/sub messaging pattern where a producer broadcasts messages to multiple consumers simultaneously. Each consumer receives a copy of every message sent to the exchange.

## How It Works

### Components

1. **FanoutProducer** - Publishes messages to the fanout exchange
   - Reads `data.json` from the project root
   - Sends 100 messages with 1-second intervals
   - Each message has an updated timestamp and modified procedureName field
   - Uses persistent message delivery to survive broker restarts

2. **FanoutConsumer** - Subscribes to queue `demo_ssatr1`
   - Binds to the `fanout_exchange`
   - Continuously listens for and prints incoming messages
   - Queue is durable (survives RabbitMQ restarts)

3. **FanoutConsumer2** - Subscribes to queue `demo_ssatr2`
   - Independent consumer with its own queue
   - Receives the same messages as FanoutConsumer (fanout pattern)

### Message Flow

```
Producer → fanout_exchange → demo_ssatr1 → FanoutConsumer
                           ↘ demo_ssatr2 → FanoutConsumer2
```

**Key Behavior:**
- Each message sent by the producer is delivered to ALL queues bound to the exchange
- Consumers operate independently - if one is offline, messages queue up for later delivery
- Messages are persistent and survive RabbitMQ server restarts
- Queues are automatically created when consumers first connect

## Prerequisites

- **Java 8 or higher** installed
- **Maven** installed (or use IntelliJ IDEA's built-in Maven)
- **Docker** installed

## Setup

### 1. Install RabbitMQ using Docker

Pull the official RabbitMQ image with management console:
```bash
docker pull rabbitmq:3-management
```

Run the RabbitMQ container:
```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 5672:5672 rabbitmq:3-management
```

Or if the container was previously created and stopped:
```bash
docker start some-rabbit
```

**Verify RabbitMQ is running:**
- Access the management console at http://localhost:8080/
- Login with username: `guest`, password: `guest`
- You should see the RabbitMQ dashboard


### 2. Build the Project

Navigate to the project root and build:

```bash
mvn clean package
```

This creates an executable JAR with all dependencies: `target/demo-rabbitmq-1.0-SNAPSHOT.jar`

**Alternative:** In IntelliJ IDEA, use `Build` → `Build Project`

## Running the Demo

### Step 1: Start Consumers

**Important:** Start consumers BEFORE the producer (first time only) so queues are created.

**Terminal 1 - Consumer 1:**
```bash
java -cp target/demo-rabbitmq-1.0-SNAPSHOT.jar demo.fanout.FanoutConsumer
```
Expected output:
```
[*] Waiting for messages on queue: demo_ssatr1
```

**Terminal 2 - Consumer 2:**
```bash
java -cp target/demo-rabbitmq-1.0-SNAPSHOT.jar demo.fanout.FanoutConsumer2
```
Expected output:
```
[*] Waiting for messages on queue: demo_ssatr2
```

### Step 2: Start Producer

**Terminal 3 - Producer:**
```bash
java -cp target/demo-rabbitmq-1.0-SNAPSHOT.jar demo.fanout.FanoutProducer
```

**What happens:**
- Reads `data.json` from the project root
- Sends 100 messages to `fanout_exchange` with 1-second intervals
- Each message has an updated timestamp and modified procedureName (appends `+0`, `+1`, `+2`, etc.)
- Producer exits after sending all messages

**Expected output (in producer terminal):**
```
[x] Sent updated JSON to exchange: {"procedureId":456,"procedureName":"Data Backup+0","status":"Pending","timestamp":"2025-10-25T..."}
[x] Sent updated JSON to exchange: {"procedureId":456,"procedureName":"Data Backup+1","status":"Pending","timestamp":"2025-10-25T..."}
...
```

**Expected output (in both consumer terminals):**
```
[x] Received in demo_ssatr1: '{"procedureId":456,"procedureName":"Data Backup+0",...}'
[x] Received in demo_ssatr1: '{"procedureId":456,"procedureName":"Data Backup+1",...}'
...
```

**Note:** Both consumers receive the SAME messages (fanout behavior).

### Step 3: Stop Consumers

Press `Ctrl+C` in each consumer terminal when done testing.

## Testing Scenarios

### Test 1: Basic Fanout Pattern
**Objective:** Verify that all consumers receive the same messages.

1. Start both consumers
2. Start the producer
3. **Verify:** Both consumer terminals display identical messages

**Expected Result:** ✓ Each of the 100 messages appears in both consumer outputs

---

### Test 2: Message Persistence
**Objective:** Verify messages are queued when consumers are offline.

1. Start consumers to create queues
2. Stop both consumers (`Ctrl+C`)
3. Start the producer (sends 100 messages)
4. Restart consumers

**Expected Result:** ✓ Consumers receive all 100 queued messages immediately upon reconnecting

---

### Test 3: Independent Consumer Operation
**Objective:** Verify consumers operate independently.

1. Start only FanoutConsumer (not FanoutConsumer2)
2. Start the producer
3. Start FanoutConsumer2 after 50 messages have been sent

**Expected Result:**
- ✓ FanoutConsumer receives all 100 messages
- ✓ FanoutConsumer2 receives only messages sent after it started (~50 messages)

---

### Test 4: RabbitMQ Management Console
**Objective:** Monitor the system through the web interface.

1. Open http://localhost:8080/ (guest/guest)
2. Navigate to **Exchanges** tab
3. Click on `fanout_exchange`
4. Navigate to **Queues** tab
5. Observe `demo_ssatr1` and `demo_ssatr2`

**What to verify:**
- ✓ Exchange type is "fanout"
- ✓ Both queues are bound to `fanout_exchange`
- ✓ Queue depths show message counts
- ✓ Message rates update during producer execution

---

### Test 5: Message Modification
**Objective:** Verify the producer modifies JSON content.

1. Check `data.json` contents before running
2. Start a consumer
3. Start the producer
4. Compare received messages with original `data.json`

**Expected Result:**
- ✓ `timestamp` field is updated with current time
- ✓ `procedureName` field has `+N` appended (where N = 0-99)
- ✓ Other fields remain unchanged

---

### Test 6: Queue Durability
**Objective:** Verify queues survive RabbitMQ restart.

1. Start consumers (creates queues)
2. Stop consumers
3. Restart RabbitMQ container: `docker restart some-rabbit`
4. Start consumers again

**Expected Result:** ✓ Consumers reconnect successfully without errors (queues persisted)

---

### Test 7: Re-running Producer
**Objective:** Verify producer can send messages multiple times.

1. Start both consumers
2. Run producer (wait for 100 messages to complete)
3. Run producer again immediately

**Expected Result:** ✓ Consumers receive another batch of 100 messages with updated timestamps

---

## Troubleshooting

**Problem:** `Connection refused` error
- **Solution:** Ensure RabbitMQ is running: `docker ps` should show `some-rabbit`
- Start it: `docker start some-rabbit`

**Problem:** Consumers don't receive messages
- **Solution:** Ensure consumers were started at least once before the producer (to create queues)

**Problem:** Messages received twice
- **Solution:** This is a known bug - line 46 in `FanoutProducer.java` has a duplicate `basicPublish` call

**Problem:** Can't access management console
- **Solution:** Verify port mapping: `docker port some-rabbit` should show `15672 → 8080`

## Project Structure

```
demo-rabbitmq/
├── src/main/java/demo/fanout/
│   ├── FanoutProducer.java      # Sends 100 messages to fanout_exchange
│   ├── FanoutConsumer.java      # Consumes from demo_ssatr1 queue
│   └── FanoutConsumer2.java     # Consumes from demo_ssatr2 queue
├── data.json                     # Message template
├── pom.xml                       # Maven configuration
└── README.md                     # This file
```

## Key Technologies

- **RabbitMQ AMQP Client 5.13.0** - Messaging protocol implementation
- **Jackson Databind 2.13.3** - JSON processing
- **Maven Shade Plugin** - Creates fat JAR with dependencies 



