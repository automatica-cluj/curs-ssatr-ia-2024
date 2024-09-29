/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatica.isp.demo.air.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Waypoint implements Serializable {
    private String name;
    private String altitude;

    public Waypoint(String name, String altitude) {
        this.name = name;
        this.altitude = altitude;
    }
    
    public String getName() {
        return name;
    }

    public String getAltitude() {
        return altitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Waypoint other = (Waypoint) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.altitude, other.altitude);
    }

    @Override
    public String toString() {
        return name;
    }
}
