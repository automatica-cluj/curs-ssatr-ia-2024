package utcn.demo.services.demoservices.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utcn.demo.services.demoservices.entitie.PurchasedTicket;
import utcn.demo.services.demoservices.service.TicketsService;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    private TicketsService ticketService;

    @PostMapping("/buy")
    public ResponseEntity<PurchasedTicket> buyTicket(@RequestParam Long eventId, @RequestParam String buyerName) {
        PurchasedTicket purchasedTicket = ticketService.buyTicket(eventId, buyerName);
        return ResponseEntity.ok(purchasedTicket);
    }

    @PostMapping("/checkin")
    public ResponseEntity<PurchasedTicket> checkIn(@RequestParam Long ticketId) {
        PurchasedTicket purchasedTicket = ticketService.checkIn(ticketId);
        return ResponseEntity.ok(purchasedTicket);
    }
}