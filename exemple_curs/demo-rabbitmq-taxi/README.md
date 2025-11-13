# RabbitMQ Taxi Service - Fanout Pattern Demo

## Overview

This project demonstrates the **RabbitMQ Fanout Exchange Pattern** through a practical taxi service application. It showcases how multiple clients can broadcast requests to multiple drivers, and how drivers can accept orders through a pub/sub messaging system.

### Key Concepts Demonstrated

- **Fanout Exchange Pattern**: Messages broadcast to all bound queues
- **Personal Queues**: Each client has a confirmation queue with the same name as their ID
- **Message Types**: Request, Cancel, and Confirmed messages
- **Concurrent Consumers**: Multiple clients and drivers can run simultaneously
- **Random ID Generation**: Simple algorithm for generating unique IDs

## Architecture

```
Client1 ──┐
Client2 ──┼──> [FANOUT_EXCHANGE] ──┬──> Driver1_queue ──> Driver1
Client3 ──┘      (taxi_requests)    ├──> Driver2_queue ──> Driver2
                                     ├──> Driver3_queue ──> Driver3
                                     ├──> Client1 (confirmation queue)
                                     ├──> Client2 (confirmation queue)
                                     └──> Client3 (confirmation queue)
```

### Message Flow

1. **Client requests taxi**: Broadcasts `request:clientId:location` to fanout exchange
2. **All drivers receive**: Each driver sees the request and can choose to accept
3. **Driver confirms**: Sends `confirmed:driverId:location` to fanout exchange
4. **Client receives confirmation**: Client sees which driver accepted
5. **Other drivers ignore**: Other drivers mark the order as taken

### Message Format

All messages follow the pattern: `type:sender:location`

- **Request**: `request:Client1234:Downtown`
- **Cancel**: `cancel:Client1234:Downtown`
- **Confirmed**: `confirmed:Driver5678:Downtown`

## Prerequisites

- **Java 8 or higher**
- **Maven 3.x**
- **Docker and Docker Compose**

## Setup Instructions

### 1. Start RabbitMQ with Docker Compose

Navigate to the project directory:

```bash
cd exemple_curs/demo-rabbitmq-taxi
```

Start RabbitMQ:

```bash
docker-compose up -d
```

Verify RabbitMQ is running:

```bash
docker-compose ps
```

You should see `taxi-rabbitmq` with status `Up`.

**Access RabbitMQ Management UI:**
- URL: http://localhost:15672
- Username: `guest`
- Password: `guest`

### 2. Build the Project

```bash
mvn clean package
```

This creates: `target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar`

## Running the Application

### Basic Scenario: One Client, Two Drivers

**Terminal 1 - Driver 1:**
```bash
java -cp target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar taxi.TaxiDriver
```

**Terminal 2 - Driver 2:**
```bash
java -cp target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar taxi.TaxiDriver
```

**Terminal 3 - Client:**
```bash
java -cp target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar taxi.TaxiClient
```

### Client Commands

In the client terminal:

```
> request:Downtown
```
Request a taxi to Downtown location.

```
> cancel
```
Cancel the current pending order.

```
> quit
```
Exit the application.

### Driver Commands

In any driver terminal:

```
> accept:1
```
Accept order number 1 (orders are numbered as they arrive).

```
> list
```
List all currently available orders.

```
> quit
```
Exit the application.

## Example Workflow

### Scenario 1: Successful Taxi Request

1. **Start 2 drivers** (Driver3421 and Driver7856)
2. **Start 1 client** (Client2341)
3. **Client requests taxi:**
   ```
   > request:Airport
   ✓ Taxi requested for location: Airport
     Waiting for driver confirmation...
   ```

4. **Both drivers see the request:**
   ```
   📱 NEW REQUEST #1
      Client: Client2341
      Location: Airport
      Type 'accept:1' to accept this order
   ```

5. **Driver3421 accepts:**
   ```
   > accept:1
   ✓ Order #1 ACCEPTED
     Client: Client2341
     Location: Airport
     Confirmation sent to client!
   ```

6. **Client receives confirmation:**
   ```
   ✓ TAXI CONFIRMED by Driver3421 for location: Airport
     Your taxi is on the way!
   ```

7. **Driver7856 sees the order is taken:**
   ```
   ⚠ Order taken by Driver3421 (Location: Airport)
   ```

### Scenario 2: Client Cancels Order

1. **Client requests taxi:**
   ```
   > request:Stadium
   ✓ Taxi requested for location: Stadium
     Waiting for driver confirmation...
   ```

2. **Drivers see the request** (order #2)

3. **Client cancels before any driver accepts:**
   ```
   > cancel
   ✓ Order cancelled for location: Stadium
   ```

4. **Drivers see the cancellation:**
   ```
   ✖ Order cancelled by Client2341 (Location: Stadium)
   ```

### Scenario 3: Multiple Concurrent Requests

1. **Start 3 drivers and 3 clients**
2. **Client1 requests**: `request:Mall`
3. **Client2 requests**: `request:Station`
4. **Client3 requests**: `request:Hospital`
5. **Drivers see all 3 requests** and can choose which to accept
6. **Different drivers accept different orders**
7. **Each client receives their specific confirmation**

## Testing Scenarios

### Test 1: Fanout Broadcasting

**Objective:** Verify all drivers receive broadcast messages.

**Steps:**
1. Start 3 drivers
2. Start 1 client
3. Client sends: `request:Park`
4. Verify all 3 driver terminals show the request

**Expected Result:** ✓ All drivers display the same request

---

### Test 2: First-Come-First-Served

**Objective:** Verify only one driver can accept an order.

**Steps:**
1. Start 2 drivers
2. Start 1 client
3. Client requests taxi
4. Driver1 accepts the order
5. Driver2 tries to accept the same order

**Expected Result:**
- ✓ Driver1 successfully accepts
- ✓ Driver2 sees "Order taken by Driver1"
- ✓ Client receives confirmation from Driver1

---

### Test 3: Order Cancellation

**Objective:** Verify cancellation removes orders from all drivers.

**Steps:**
1. Start 2 drivers
2. Start 1 client
3. Client requests taxi (creates order #1 for both drivers)
4. Client cancels before acceptance
5. Check both driver's available orders

**Expected Result:**
- ✓ Both drivers see cancellation message
- ✓ Order removed from available orders list
- ✓ Drivers cannot accept cancelled order

---

### Test 4: Personal Confirmation Queues

**Objective:** Verify clients only receive their own confirmations.

**Steps:**
1. Start 1 driver
2. Start 2 clients (Client1 and Client2)
3. Client1 requests: `request:Beach`
4. Client2 requests: `request:Mountains`
5. Driver accepts Client1's order
6. Verify only Client1 receives confirmation

**Expected Result:**
- ✓ Client1 receives confirmation
- ✓ Client2 does NOT receive Client1's confirmation
- ✓ Client2 still waiting for their order

---

### Test 5: Multiple Clients and Drivers

**Objective:** Test realistic scenario with multiple participants.

**Steps:**
1. Start 3 drivers
2. Start 3 clients
3. All clients request taxis simultaneously
4. Drivers accept different orders

**Expected Result:**
- ✓ All requests broadcast to all drivers
- ✓ Each client gets confirmation from specific driver
- ✓ No conflicts or duplicate acceptances

---

### Test 6: Driver List Command

**Objective:** Verify drivers can list available orders.

**Steps:**
1. Start 1 driver
2. Start 3 clients
3. All clients request taxis (don't accept yet)
4. Driver types: `list`

**Expected Result:**
```
=== AVAILABLE ORDERS ===
#1 - Client: Client1234, Location: Downtown
#2 - Client: Client5678, Location: Airport
#3 - Client: Client9012, Location: Mall
========================
```

---

### Test 7: Random ID Generation

**Objective:** Verify unique IDs are generated.

**Steps:**
1. Start 5 clients in different terminals
2. Start 5 drivers in different terminals
3. Note all generated IDs

**Expected Result:**
- ✓ All client IDs are unique (e.g., Client1234, Client5678, etc.)
- ✓ All driver IDs are unique (e.g., Driver3421, Driver7856, etc.)
- ✓ IDs follow format: Client/Driver + 4-digit number (1000-9999)

---

### Test 8: Queue Persistence

**Objective:** Verify personal queues persist in RabbitMQ.

**Steps:**
1. Start 1 client (creates personal queue)
2. Note client ID (e.g., Client1234)
3. Close client application
4. Check RabbitMQ Management UI → Queues tab

**Expected Result:**
- ✓ Queue named `Client1234` exists
- ✓ Queue is bound to `taxi_requests` exchange

---

### Test 9: RabbitMQ Management Monitoring

**Objective:** Monitor the system through RabbitMQ UI.

**Steps:**
1. Open http://localhost:15672 (guest/guest)
2. Navigate to **Exchanges** → `taxi_requests`
3. Verify exchange type is "fanout"
4. Navigate to **Queues**
5. Start 2 drivers and 2 clients
6. Observe queue list

**Expected Result:**
- ✓ Exchange `taxi_requests` exists with type "fanout"
- ✓ Driver queues visible (e.g., `Driver3421_queue`)
- ✓ Client queues visible (e.g., `Client1234`)
- ✓ All queues bound to `taxi_requests` exchange

---

### Test 10: Preventing Duplicate Requests

**Objective:** Verify clients cannot send duplicate requests.

**Steps:**
1. Start 1 driver
2. Start 1 client
3. Client sends: `request:Stadium`
4. Before driver accepts, client tries: `request:Airport`

**Expected Result:**
```
⚠ You already have a pending request. Cancel it first or wait for confirmation.
```

## Project Structure

```
demo-rabbitmq-taxi/
├── docker-compose.yml          # RabbitMQ infrastructure
├── pom.xml                     # Maven configuration
├── src/main/java/taxi/
│   ├── TaxiClient.java         # Client application
│   └── TaxiDriver.java         # Driver application
├── .gitignore
└── README.md                   # This file
```

## Key Implementation Details

### Random ID Generation Algorithm

```java
Random random = new Random();
int randomNum = 1000 + random.nextInt(9000); // Generates 1000-9999
String id = "Client" + randomNum;  // e.g., Client3421
```

### Queue Naming Convention

- **Driver queues**: `{driverId}_queue` (e.g., `Driver3421_queue`)
  - Auto-delete when driver disconnects
- **Client queues**: `{clientId}` (e.g., `Client1234`)
  - Durable, persist even after client disconnects

### Message Handling Logic

**Driver-side:**
```java
- Receives "request:Client1234:Airport"
  → Adds to available orders as #1

- Receives "confirmed:Driver5678:Airport"
  → If not self, marks order as taken and removes from list

- Receives "cancel:Client1234:Airport"
  → Marks as cancelled and removes from available orders
```

**Client-side:**
```java
- Sends "request:Client1234:Airport"
  → Sets waitingForConfirmation = true

- Receives "confirmed:Driver5678:Airport"
  → If location matches current request, displays confirmation
  → Sets waitingForConfirmation = false
```

## Troubleshooting

**Problem:** `Connection refused` error

**Solution:** Ensure RabbitMQ is running:
```bash
docker-compose ps
```
If not running:
```bash
docker-compose up -d
```

---

**Problem:** Client/Driver doesn't receive messages

**Solution:**
- Verify fanout exchange exists in RabbitMQ UI
- Check queue bindings in Queues tab
- Ensure correct exchange name: `taxi_requests`

---

**Problem:** "Invalid order number" when accepting

**Solution:**
- Use `list` command to see available order numbers
- Orders are numbered sequentially starting from 1
- Order may have been already accepted or cancelled

---

**Problem:** Duplicate confirmations received

**Solution:** This is expected behavior with fanout pattern - client receives confirmation via their personal queue (which is also bound to the fanout exchange)

---

**Problem:** Cannot access RabbitMQ Management UI

**Solution:**
- Verify port mapping: `docker-compose ps`
- Should show `0.0.0.0:15672->15672/tcp`
- Try: http://127.0.0.1:15672 instead of localhost

## Docker Compose Commands

```bash
# Start RabbitMQ
docker-compose up -d

# Stop RabbitMQ
docker-compose down

# View logs
docker-compose logs -f

# Restart RabbitMQ
docker-compose restart

# Stop and remove all data
docker-compose down -v
```

## Learning Objectives

After completing this exercise, you should understand:

✓ How fanout exchanges broadcast messages to all bound queues
✓ How to implement personal queues for direct responses
✓ Message format design for multi-type communication
✓ Handling concurrent consumers and race conditions
✓ Using RabbitMQ for real-time distributed applications
✓ Implementing interactive console applications in Java
✓ Docker Compose for infrastructure setup

## Potential Enhancements

1. **Add acknowledgment mechanism** (manual ack instead of auto-ack)
2. **Implement driver location tracking**
3. **Add priority queues** for premium clients
4. **Implement timeout** for requests (auto-cancel after X seconds)
5. **Add logging** with SLF4J
6. **Persist order history** to database
7. **Add driver ratings** system
8. **Implement estimated arrival time** calculation

## Dependencies

- **RabbitMQ AMQP Client 5.13.0** - Java client library
- **Docker** - For running RabbitMQ
- **Maven** - Build tool

## License

This is an educational project for demonstrating RabbitMQ patterns.
