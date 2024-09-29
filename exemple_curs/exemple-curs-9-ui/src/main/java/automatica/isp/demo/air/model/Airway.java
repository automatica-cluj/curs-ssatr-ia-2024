/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatica.isp.demo.air.model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Airway extends AbstractListModel implements Serializable{
    private String flightId;
    private String departure;
    private String destination; 
    private ArrayList<Waypoint> waypoints;

    public Airway(String flightId, String departure, String destination, ArrayList<Waypoint> waypoints) {
        this.flightId = flightId;
        this.departure = departure;
        this.destination = destination;
        this.waypoints = waypoints;
    }

    public String getFlightId() {
        return flightId;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public ArrayList<Waypoint> getWaypoints() {
        return waypoints;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.flightId);
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
        final Airway other = (Airway) obj;
        return Objects.equals(this.flightId, other.flightId);
    }

    @Override
    public String toString() {
        return flightId;
    }

    @Override
    public int getSize() {
        return waypoints.size();
    }

    @Override
    public Object getElementAt(int index) {
        return waypoints.get(index);
    }

    public void printAll(){
        System.out.println("Airway: ");
        System.out.println(getFlightId());
        System.out.println(getDeparture());
        System.out.println(getDestination());
        for(Waypoint w : waypoints){
            System.out.println("Waypoint: ");
            System.out.println(w.getName());
            System.out.println(w.getAltitude());
        }
    }
}
