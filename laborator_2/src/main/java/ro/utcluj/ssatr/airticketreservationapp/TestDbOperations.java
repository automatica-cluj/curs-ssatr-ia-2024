
package ro.utcluj.ssatr.airticketreservationapp;

import ro.utcluj.ssatr.airticketreservationapp.model.FlightInformation;
import ro.utcluj.ssatr.airticketreservationapp.service.FlightReservationService;

public class TestDbOperations {
    public static void main(String[] args) {
        System.out.println("Ticket reservation app.");
        
        FlightInformation f1 = new FlightInformation("AB01", 100, "11-11-2021");
        System.out.println(f1);
        f1.setNumberOfSeats(f1.getNumberOfSeats()-2);
        System.out.println(f1);
        FlightInformation f2 = new FlightInformation("AB01", 100, "11-11-2021");
        
        if(f1.equals(f2)){
            System.out.println("EQUALS");
        }else{
            System.out.println("NOT EQUALS");
        }
        
        
        /////////////////////////////////
        
        FlightReservationService frs = new FlightReservationService();
        frs.addNewFlight("ABC01", 10, "10-11-2020");
        System.out.println(frs.getAllFlights());
        frs.makeReservation("ABC01", 3);
        System.out.println(frs.getAllFlights());
       
        
        
    }
}
