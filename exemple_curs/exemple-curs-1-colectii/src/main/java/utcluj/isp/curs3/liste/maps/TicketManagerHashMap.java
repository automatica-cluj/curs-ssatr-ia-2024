package utcluj.isp.curs3.liste.maps;

import utcluj.isp.curs3.liste.arraylists.AirplaneTicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketManagerHashMap {

    // HashMap to store AirplaneTicket objects
    private HashMap<String, ArrayList<AirplaneTicket>> ticketsByFlightNumber;

    // Constructor
    public TicketManagerHashMap() {
        ticketsByFlightNumber = new HashMap<String, ArrayList<AirplaneTicket>>();
    }

    // Method to add a new AirplaneTicket object to the HashMap
    public void addTicket(AirplaneTicket ticket) {
        String flightNumber = ticket.getFlightNumber();
        if (!ticketsByFlightNumber.containsKey(flightNumber)) {
            ticketsByFlightNumber.put(flightNumber, new ArrayList<AirplaneTicket>());
        }
        ticketsByFlightNumber.get(flightNumber).add(ticket);
    }

    // Method to search for an AirplaneTicket object by flight number
    public AirplaneTicket searchTicketByFlightNumber(String flightNumber) {
        if (ticketsByFlightNumber.containsKey(flightNumber)) {
            ArrayList<AirplaneTicket> tickets = ticketsByFlightNumber.get(flightNumber);
            if (!tickets.isEmpty()) {
                return tickets.get(0);
            }
        }
        return null;
    }

    // Method to get all AirplaneTicket objects for a given flight number
    public ArrayList<AirplaneTicket> getAllTicketsForFlightNumber(String flightNumber) {
        if (ticketsByFlightNumber.containsKey(flightNumber)) {
            return ticketsByFlightNumber.get(flightNumber);
        }
        return new ArrayList<AirplaneTicket>();
    }

    // Method to calculate and return the average price of all AirplaneTicket objects for a given flight number
    public double getAverageTicketPriceForFlightNumber(String flightNumber) {
        if (ticketsByFlightNumber.containsKey(flightNumber)) {
            ArrayList<AirplaneTicket> flightTickets = ticketsByFlightNumber.get(flightNumber);
            double totalPrice = 0;
            for (AirplaneTicket ticket : flightTickets) {
                totalPrice += ticket.getTicketPrice();
            }
            if (flightTickets.size() == 0) {
                return 0;
            } else {
                return totalPrice / flightTickets.size();
            }
        } else {
            return 0;
        }
    }
}

