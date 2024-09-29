package utcn.demo.services.demoservices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utcn.demo.services.demoservices.entitie.EventTicket;
import utcn.demo.services.demoservices.entitie.PurchasedTicket;
import utcn.demo.services.demoservices.repository.EventTicketRepository;
import utcn.demo.services.demoservices.repository.PurchasedTicketRepository;

import java.util.List;

@SpringBootTest
class DemoServicesApplicationTests {

    @Autowired
    private EventTicketRepository eventTicketRepository;

    @Autowired
    private PurchasedTicketRepository purchasedTicketRepository;

    @Test
    void contextLoads() {

    }

    @Test
    void testEventTicketRepository() {
        EventTicket eventTicket = new EventTicket();
        eventTicket.setId(2L);
        eventTicket.setEventName("Concert");
        eventTicket.setTicketPrice(100.0);
        eventTicketRepository.save(eventTicket);

        assert(eventTicketRepository.findById(2L).get().getEventName().equals("Concert"));
    }

    @Test
    void testPurchasedTicket() {
        EventTicket eventTicket = eventTicketRepository.findById(3L).get();
        PurchasedTicket purchasedTicket = new PurchasedTicket();
        purchasedTicket.setId(2L);
        purchasedTicket.setEventTicketId(eventTicket.getId());
        purchasedTicket.setBuyerName("John Doe");
        purchasedTicketRepository.save(purchasedTicket);
        assert(purchasedTicketRepository.findById(2L).get().getBuyerName().equals("John Doe"));
    }

    @Test
    void testFindPurchasedTicket() {
        EventTicket eventTicket = new EventTicket();
        eventTicket.setId(3L);
        eventTicket.setEventName("Concert");
        eventTicket.setTicketPrice(100.0);
        eventTicketRepository.save(eventTicket);

        PurchasedTicket purchasedTicket = new PurchasedTicket();
        purchasedTicket.setId(2L);
        purchasedTicket.setEventTicketId(eventTicket.getId());
        purchasedTicket.setBuyerName("John Doe");
        purchasedTicketRepository.save(purchasedTicket);


        List<PurchasedTicket> list = purchasedTicketRepository.findByEventTicketId(3L);
        assert(list.size() == 1);
    }

}
