package utcluj.aut.demoticketsserver;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/addTicket")
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.saveTicket(ticket);
    }

    @GetMapping("/getAllTickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

}
