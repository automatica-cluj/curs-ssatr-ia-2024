package utcluj.isp.curs3.simpleapps.eventticketsystem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchasedTicket {
    private String name;
    private String email;
    private String phoneNumber;
    private EventTicket eventTicket;
    private String purchaseDate;

    public PurchasedTicket(String name, String email, String phoneNumber, EventTicket eventTicket, String purchaseDate) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.eventTicket = eventTicket;
        this.purchaseDate = purchaseDate;
    }

    public String toString() {
        return "Name: " + name + " | Email: " + email + " | Phone Number: " + phoneNumber + " | " + eventTicket + " | Purchase Date: " + purchaseDate;
    }
}