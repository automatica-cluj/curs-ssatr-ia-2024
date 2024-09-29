package utcluj.isp.curs3.simpleapps.eventticketsystem;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventTicket {
    private String eventName;
    private LocalDateTime eventDate;
    private String ticketType;
    private double ticketPrice;

    public EventTicket(String eventName, LocalDateTime eventDate, String ticketType, double ticketPrice) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.ticketType = ticketType;
        this.ticketPrice = ticketPrice;
    }

    public String toString() {
        return "Event: " + eventName + " | Date: " + eventDate + " | Ticket Type: " + ticketType + " | Price: $" + ticketPrice;
    }
    
    
}
