

# Usage example

CURL command for creating a new ticket:

```sh
curl -X POST http://localhost:8080/addTicket -H "Content-Type: application/json" -d "{\"eventName\":\"Concert\",\"eventDate\":\"2023-12-01\"}"
```

```sh
curl -X GET http://localhost:8080/getAllTickets      
```