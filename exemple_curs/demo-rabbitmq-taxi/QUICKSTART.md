# Quick Start Guide

## Fast Setup (5 minutes)

### 1. Start RabbitMQ
```bash
cd exemple_curs/demo-rabbitmq-taxi
docker-compose up -d
```

### 2. Build Project
```bash
mvn clean package
```

### 3. Open 3 Terminals

**Terminal 1 - Driver:**
```bash
java -cp target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar taxi.TaxiDriver
```

**Terminal 2 - Another Driver:**
```bash
java -cp target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar taxi.TaxiDriver
```

**Terminal 3 - Client:**
```bash
java -cp target/demo-rabbitmq-taxi-1.0-SNAPSHOT.jar taxi.TaxiClient
```

### 4. Test the Flow

**In Client Terminal:**
```
> request:Airport
```

**In Driver1 Terminal:**
```
> accept:1
```

**Result:**
- Client sees: "✓ TAXI CONFIRMED by DriverXXXX"
- Driver2 sees: "⚠ Order taken by DriverXXXX"

## Common Commands

### Client:
- `request:Downtown` - Request taxi to Downtown
- `cancel` - Cancel current request
- `quit` - Exit

### Driver:
- `accept:1` - Accept order #1
- `list` - Show available orders
- `quit` - Exit

## Verify RabbitMQ
Open browser: http://localhost:15672
- Username: `guest`
- Password: `guest`

## Troubleshooting

**Can't connect to RabbitMQ?**
```bash
docker-compose ps  # Check if running
docker-compose up -d  # Start if needed
```

**Build failed?**
- Check internet connection
- Try: `mvn clean package -U`

**Orders not appearing?**
- Make sure all applications are running
- Check RabbitMQ is up
- Verify exchange `taxi_requests` exists in RabbitMQ UI

## Fun Scenarios to Try

1. **Race Condition**: Start 3 drivers, 1 client, all drivers try to accept same order
2. **Multiple Requests**: Start 1 driver, 3 clients, all request taxis simultaneously
3. **Cancellation**: Request taxi, cancel before driver accepts
4. **Busy Driver**: Accept one order, new requests still appear
