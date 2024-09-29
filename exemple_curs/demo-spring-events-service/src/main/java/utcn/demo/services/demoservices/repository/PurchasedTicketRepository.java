package utcn.demo.services.demoservices.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import utcn.demo.services.demoservices.entitie.PurchasedTicket;

import java.util.List;

public interface PurchasedTicketRepository extends PagingAndSortingRepository<PurchasedTicket, Long>, CrudRepository<PurchasedTicket, Long>{
    List<PurchasedTicket> findByEventTicketId(Long eventTicketId);
}