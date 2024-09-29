package utcn.demo.services.demoservices.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import utcn.demo.services.demoservices.entitie.EventTicket;

public interface EventTicketRepository extends PagingAndSortingRepository<EventTicket, Long>, CrudRepository<EventTicket, Long>{
}
