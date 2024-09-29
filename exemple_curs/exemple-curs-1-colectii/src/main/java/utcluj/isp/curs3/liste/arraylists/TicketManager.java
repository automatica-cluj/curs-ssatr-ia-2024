package utcluj.isp.curs3.liste.arraylists;

import java.util.ArrayList;

public class TicketManager {

    // ArrayList to store AirplaneTicket objects
    private ArrayList<AirplaneTicket> tickets;

    // Constructor
    public TicketManager() {
        tickets = new ArrayList<AirplaneTicket>();
    }

    // Method to add a new AirplaneTicket object to the ArrayList
    public void addTicket(AirplaneTicket ticket) {
        tickets.add(ticket);
    }

    // Method to search for an AirplaneTicket object by flight number
    public AirplaneTicket searchTicketByFlightNumber(String flightNumber) {
        for (AirplaneTicket ticket : tickets) {
            if (ticket.getFlightNumber().equals(flightNumber)) {
                return ticket;
            }
        }
        return null;
    }

    // Method to get all AirplaneTicket objects for a given flight number
    public ArrayList<AirplaneTicket> getAllTicketsForFlightNumber(String flightNumber) {
        ArrayList<AirplaneTicket> result = new ArrayList<AirplaneTicket>();
        for (AirplaneTicket ticket : tickets) {
            if (ticket.getFlightNumber().equals(flightNumber)) {
                result.add(ticket);
            }
        }
        return result;
    }

    // Method to calculate and return the average price of all AirplaneTicket objects for a given flight number
    public double getAverageTicketPriceForFlightNumber(String flightNumber) {
        ArrayList<AirplaneTicket> flightTickets = getAllTicketsForFlightNumber(flightNumber);
        double totalPrice = 0;
        for (AirplaneTicket ticket : flightTickets) {
            totalPrice += ticket.getTicketPrice();
        }
        if (flightTickets.size() == 0) {
            return 0;
        } else {
            return totalPrice / flightTickets.size();
        }
    }
}
