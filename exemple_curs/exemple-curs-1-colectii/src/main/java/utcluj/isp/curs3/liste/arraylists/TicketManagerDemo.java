package utcluj.isp.curs3.liste.arraylists;

import java.util.ArrayList;

public class TicketManagerDemo {
    public static void main(String[] args) {

        // Create a new TicketManager object
        TicketManager ticketManager = new TicketManager();

        // Create some AirplaneTicket objects
        AirplaneTicket ticket1 = new AirplaneTicket("John Smith", "New York", "Los Angeles", "UA123", 350.0);
        AirplaneTicket ticket2 = new AirplaneTicket("Jane Doe", "Los Angeles", "New York", "UA123", 400.0);
        AirplaneTicket ticket3 = new AirplaneTicket("Bob Johnson", "Chicago", "Houston", "AA456", 250.0);
        AirplaneTicket ticket4 = new AirplaneTicket("Samantha Lee", "Houston", "Chicago", "AA456", 275.0);

        // Add the AirplaneTicket objects to the TicketManager
        ticketManager.addTicket(ticket1);
        ticketManager.addTicket(ticket2);
        ticketManager.addTicket(ticket3);
        ticketManager.addTicket(ticket4);

        // Search for an AirplaneTicket object by flight number
        AirplaneTicket foundTicket = ticketManager.searchTicketByFlightNumber("UA123");
        if (foundTicket != null) {
            System.out.println("Ticket found: \n" + foundTicket.toString());
        } else {
            System.out.println("No ticket found with flight number UA123");
        }

        // Get all AirplaneTicket objects for a given flight number
        ArrayList<AirplaneTicket> flightTickets = ticketManager.getAllTicketsForFlightNumber("AA456");
        if (!flightTickets.isEmpty()) {
            System.out.println("\nAll tickets for flight number AA456:");
            for (AirplaneTicket ticket : flightTickets) {
                System.out.println(ticket.toString());
            }
        } else {
            System.out.println("No tickets found for flight number AA456");
        }

        // Calculate and print the average ticket price for a given flight number
        double avgPrice = ticketManager.getAverageTicketPriceForFlightNumber("UA123");
        if (avgPrice == 0) {
            System.out.println("\nNo tickets found for flight number UA123");
        } else {
            System.out.println("\nAverage ticket price for flight number UA123: $" + avgPrice);
        }
    }
}

