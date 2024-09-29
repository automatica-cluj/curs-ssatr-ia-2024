package utcn.demo.services.demoservices.entitie;

import jakarta.persistence.*;

@Entity
public class PurchasedTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "event_ticket_id", nullable = false)
    private Long eventTicketId;

    @Column(name = "buyer_name", nullable = false)
    private String buyerName;

    @Column(name = "is_checked_in", nullable = false)
    private boolean isCeckedIn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventTicketId() {
        return eventTicketId;
    }

    public void setEventTicketId(Long eventTicketId) {
        this.eventTicketId = eventTicketId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public boolean getIsCheckedIn() {
        return isCeckedIn;
    }

    public void setIsCheckedIn(boolean isCheckedIn) {
        this.isCeckedIn = isCheckedIn;
    }

    // getters and setters
}