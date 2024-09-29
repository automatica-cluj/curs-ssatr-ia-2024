package utcn.demo.services.demoservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utcn.demo.services.demoservices.entitie.EventTicket;
import utcn.demo.services.demoservices.entitie.PurchasedTicket;
import utcn.demo.services.demoservices.repository.EventTicketRepository;
import utcn.demo.services.demoservices.repository.PurchasedTicketRepository;

@Service
public class TicketsService {

    @Autowired
    private EventTicketRepository eventTicketRepository;

    @Autowired
    private PurchasedTicketRepository purchasedTicketRepository;

    public PurchasedTicket buyTicket(Long eventId, String buyerName) {
        EventTicket eventTicket = eventTicketRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        PurchasedTicket purchasedTicket = new PurchasedTicket();
        purchasedTicket.setEventTicketId(eventTicket.getId());
        purchasedTicket.setBuyerName(buyerName);
        purchasedTicket.setIsCheckedIn(false);

        return purchasedTicketRepository.save(purchasedTicket);
    }

    //create checkin method
    public PurchasedTicket checkIn(Long ticketId) {
        PurchasedTicket purchasedTicket = purchasedTicketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        purchasedTicket.setIsCheckedIn(true);
        return purchasedTicketRepository.save(purchasedTicket);
    }
}