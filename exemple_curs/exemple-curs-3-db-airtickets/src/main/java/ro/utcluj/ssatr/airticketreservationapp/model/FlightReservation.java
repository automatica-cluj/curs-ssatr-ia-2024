/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.model;

/**
 *
 * @author mihai
 */
public class FlightReservation {
    private int idreservation;
    private String flightNumber;
    private int noOfTickets;


    public FlightReservation(int idreservation, String flightNumber, int noOfTickets) {
        this.idreservation = idreservation;
        this.flightNumber = flightNumber;
        this.noOfTickets = noOfTickets;
    }

    public int getIdreservation() {
        return idreservation;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }
}
