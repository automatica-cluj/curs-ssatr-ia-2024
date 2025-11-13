# Architecture and Message Flow

## System Components

### 1. RabbitMQ Exchange
- **Name**: `taxi_requests`
- **Type**: FANOUT
- **Durability**: Durable (survives RabbitMQ restart)

### 2. Queues

#### Driver Queues
- **Naming**: `{driverId}_queue` (e.g., `Driver3421_queue`)
- **Properties**: Non-durable, auto-delete on disconnect
- **Purpose**: Receive all broadcast messages
- **Bound to**: `taxi_requests` exchange

#### Client Queues
- **Naming**: `{clientId}` (e.g., `Client1234`)
- **Properties**: Durable, persistent
- **Purpose**: Receive confirmations
- **Bound to**: `taxi_requests` exchange

## Message Flow Diagrams

### Flow 1: Client Requests Taxi

```
┌─────────┐                                  ┌──────────────────┐
│ Client  │                                  │ FANOUT EXCHANGE  │
│  1234   │                                  │ (taxi_requests)  │
└────┬────┘                                  └────────┬─────────┘
     │                                                 │
     │ 1. Send: request:Client1234:Airport            │
     ├────────────────────────────────────────────────>│
     │                                                 │
     │                                    2. Broadcast to all queues
     │                                                 │
     │           ┌─────────────────────────────────────┼─────────────────────────┐
     │           │                                     │                         │
     │           ▼                                     ▼                         ▼
     │   ┌──────────────┐                    ┌──────────────┐          ┌──────────────┐
     │   │ Driver3421   │                    │ Driver7856   │          │  Client1234  │
     │   │   _queue     │                    │   _queue     │          │    queue     │
     │   └──────┬───────┘                    └──────┬───────┘          └──────────────┘
     │          │                                   │
     │   3. Receives request            3. Receives request
     │      Shows: "NEW REQUEST #1"        Shows: "NEW REQUEST #1"
     │
```

### Flow 2: Driver Accepts Order

```
┌──────────┐                                 ┌──────────────────┐
│ Driver   │                                 │ FANOUT EXCHANGE  │
│  3421    │                                 │ (taxi_requests)  │
└────┬─────┘                                 └────────┬─────────┘
     │                                                 │
     │ 1. Send: confirmed:Driver3421:Airport          │
     ├────────────────────────────────────────────────>│
     │                                                 │
     │                                    2. Broadcast to all queues
     │                                                 │
     │           ┌─────────────────────────────────────┼─────────────────────────┐
     │           │                                     │                         │
     │           ▼                                     ▼                         ▼
     │   ┌──────────────┐                    ┌──────────────┐          ┌──────────────┐
     │   │ Driver3421   │                    │ Driver7856   │          │  Client1234  │
     │   │   _queue     │                    │   _queue     │          │    queue     │
     │   └──────┬───────┘                    └──────┬───────┘          └──────┬───────┘
     │          │                                   │                         │
     │   3. Receives (ignores self)      3. Marks order taken      3. Shows confirmation
     │                                      "Order taken by          "TAXI CONFIRMED"
     │                                       Driver3421"
```

### Flow 3: Client Cancels Order

```
┌─────────┐                                  ┌──────────────────┐
│ Client  │                                  │ FANOUT EXCHANGE  │
│  1234   │                                  │ (taxi_requests)  │
└────┬────┘                                  └────────┬─────────┘
     │                                                 │
     │ 1. Send: cancel:Client1234:Airport             │
     ├────────────────────────────────────────────────>│
     │                                                 │
     │                                    2. Broadcast to all queues
     │                                                 │
     │                          ┌──────────────────────┴──────────────────────┐
     │                          │                                             │
     │                          ▼                                             ▼
     │                 ┌──────────────┐                              ┌──────────────┐
     │                 │ Driver3421   │                              │ Driver7856   │
     │                 │   _queue     │                              │   _queue     │
     │                 └──────┬───────┘                              └──────┬───────┘
     │                        │                                             │
     │               3. Removes order from list                3. Removes order from list
     │                  "Order cancelled by Client1234"          "Order cancelled"
```

## State Management

### Client States

```
┌─────────┐
│  IDLE   │  ◄──────────────────┐
└────┬────┘                     │
     │                          │
     │ request:<location>       │
     │                          │
     ▼                          │
┌──────────────┐                │
│   WAITING    │                │
│      FOR     │  ──────────────┤
│ CONFIRMATION │    confirmed   │
└──────┬───────┘    received    │
       │                        │
       │ cancel                 │
       └────────────────────────┘
```

### Driver States

```
┌──────────────┐
│  LISTENING   │  ◄────────────────────────┐
│     FOR      │                           │
│  REQUESTS    │                           │
└──────┬───────┘                           │
       │                                   │
       │ Receive request                  │
       │                                   │
       ▼                                   │
┌──────────────┐                           │
│   PENDING    │                           │
│    ORDER     │                           │
│  (in list)   │                           │
└──────┬───────┘                           │
       │                                   │
       │ accept:<order>                    │
       │                                   │
       ▼                                   │
┌──────────────┐                           │
│    SEND      │                           │
│ CONFIRMATION │  ─────────────────────────┘
└──────────────┘       Return to listening
```

## Message Processing Logic

### TaxiClient Message Handler

```
Receive message → Parse "type:sender:location"
                        │
                        ├─ type = "confirmed"
                        │    └─ location matches current request?
                        │         └─ YES: Show confirmation, clear waiting state
                        │         └─ NO: Ignore (different client's order)
                        │
                        ├─ type = "request"
                        │    └─ Ignore (clients don't process requests)
                        │
                        └─ type = "cancel"
                             └─ Ignore (other client's cancellation)
```

### TaxiDriver Message Handler

```
Receive message → Parse "type:sender:location"
                        │
                        ├─ type = "request"
                        │    └─ Already confirmed/cancelled?
                        │         └─ NO: Add to available orders, show notification
                        │         └─ YES: Ignore
                        │
                        ├─ type = "confirmed"
                        │    └─ sender is self?
                        │         └─ NO: Mark order taken, remove from list
                        │         └─ YES: Ignore own confirmation
                        │
                        └─ type = "cancel"
                             └─ Mark cancelled, remove from available orders
```

## Queue Bindings in RabbitMQ

```
Exchange: taxi_requests (fanout)
    │
    ├─── Binding ───> Queue: Driver3421_queue
    │                    └─ Consumer: TaxiDriver (driverId=Driver3421)
    │
    ├─── Binding ───> Queue: Driver7856_queue
    │                    └─ Consumer: TaxiDriver (driverId=Driver7856)
    │
    ├─── Binding ───> Queue: Client1234
    │                    └─ Consumer: TaxiClient (clientId=Client1234)
    │
    └─── Binding ───> Queue: Client5678
                         └─ Consumer: TaxiClient (clientId=Client5678)
```

## Concurrency and Race Conditions

### Scenario: Two Drivers Accept Same Order

```
Time    Driver3421                 Driver7856                  Exchange
─────────────────────────────────────────────────────────────────────────
T1      Receives request #1        Receives request #1         -
T2      Types: accept:1            Types: accept:1             -
T3      Sends confirmation ────────────────────────────────────> Received
T4      -                          Sends confirmation ─────────> Received
T5      Receives own confirm       -                           Broadcast
        (ignores)
T6      -                          Receives D3421's confirm    Broadcast
                                   (marks as taken)
T7      Receives D7856's confirm   Receives own confirm        Broadcast
        (marks as taken)           (ignores)
```

**Result**: Both confirmations sent, but:
- Client receives FIRST confirmation only (matched waiting state)
- Both drivers mark order as taken
- No actual conflict - client gets ONE taxi

### Scenario: Client Cancels During Driver Accept

```
Time    Client1234                 Driver3421                  Exchange
─────────────────────────────────────────────────────────────────────────
T1      Sends request ─────────────────────────────────────────> Broadcast
T2      -                          Receives request #1         -
T3      Types: cancel              Types: accept:1             -
T4      Sends cancel ──────────────────────────────────────────> Received
T5      -                          Sends confirmation ─────────> Received
T6      Receives cancel            Receives cancel             Broadcast
        (clears waiting)           (marks cancelled)
T7      Receives confirmation      Receives own confirm        Broadcast
        (ignored - not waiting)    (ignores)
```

**Result**: Order cancelled, confirmation ignored by client

## Data Structures

### TaxiClient
```java
- clientId: String (generated once)
- confirmationQueue: String (same as clientId)
- currentLocation: String (current pending request)
- waitingForConfirmation: boolean (state flag)
```

### TaxiDriver
```java
- driverId: String (generated once)
- driverQueue: String ({driverId}_queue)
- availableOrders: Map<Integer, String> (orderNum -> "clientId:location")
- confirmedOrders: Set<String> (set of "clientId:location")
- cancelledOrders: Set<String> (set of "clientId:location")
- orderCounter: int (auto-incrementing order number)
```

## Network Topology

```
┌──────────────────────────────────────────────────────────────┐
│                         DOCKER HOST                          │
│                                                              │
│  ┌────────────────────────────────────────────────────┐     │
│  │          RabbitMQ Container (taxi-rabbitmq)        │     │
│  │                                                    │     │
│  │  Port 5672  ◄─── AMQP Protocol                     │     │
│  │  Port 15672 ◄─── Management UI (HTTP)              │     │
│  │                                                    │     │
│  │  Volume: rabbitmq_data (persistent storage)        │     │
│  └────────────────────────────────────────────────────┘     │
│                    ▲                    ▲                    │
└────────────────────┼────────────────────┼────────────────────┘
                     │                    │
          ┌──────────┴────────┐   ┌───────┴──────────┐
          │   Java Client     │   │   Java Driver    │
          │   Application     │   │   Application    │
          │ (localhost:5672)  │   │ (localhost:5672) │
          └───────────────────┘   └──────────────────┘
```

## Security Considerations (Production)

⚠ This is a DEMO. For production:
1. Change default credentials (guest/guest)
2. Use TLS for AMQP connections
3. Implement authentication/authorization
4. Use virtual hosts for tenant isolation
5. Enable message encryption
6. Implement rate limiting
7. Add message signing for integrity
8. Use message TTL to prevent queue buildup
