package ro.utcluj.ssatr.airticketreservationapp.model;

import java.util.Objects;


public class FlightInformation {
    private String flightNumber;
    private int numberOfSeats;
    private String departureDate;

    //constructor 
    //settere si gettere 
    //tostring
    //equals 
    
    //ALT+Insert

    public FlightInformation(String flightNumber, int numberOfSeats, String departureDate) {
        this.flightNumber = flightNumber;
        this.numberOfSeats = numberOfSeats;
        this.departureDate = departureDate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    @Override
    public String toString() {
        return "FlightInformation{" + "flightNumber=" + flightNumber + ", numberOfSeats=" + numberOfSeats + ", departureDate=" + departureDate + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.flightNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FlightInformation other = (FlightInformation) obj;
        if (!Objects.equals(this.flightNumber, other.flightNumber)) {
            return false;
        }
        return true;
    }

    
}
