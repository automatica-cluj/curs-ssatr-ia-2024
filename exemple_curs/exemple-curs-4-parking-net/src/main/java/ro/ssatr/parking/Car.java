/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.ssatr.parking;

import java.util.Objects;

public class Car {
    private String plateNumber;
    private long entryTime; //JVM le initializeaza cu 0 deoarece sunt variabile de tip primitiv
    private long exitTime;
    private boolean freeParking;
    
    public Car(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Car(String plateNumber, long entryTime) {
        this.plateNumber = plateNumber;
        this.entryTime = entryTime;
    }

    public boolean isFreeParking() {
        return freeParking;
    }

    public void setFreeParking(boolean freeParking) {
        this.freeParking = freeParking;
    }
    
    

    public String getPlateNumber() {
        return plateNumber;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public void setExitTime(long exitTime) {
        this.exitTime = exitTime;
    }

    @Override
    public String toString() {
        return "Car{" + "plateNumber=" + plateNumber + ", entryTime=" + entryTime + ", exitTime=" + exitTime + ", freeParking=" + freeParking + '}';
    }
    
    //ALT INSERT

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.plateNumber);
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
        final Car other = (Car) obj;
        if (!Objects.equals(this.plateNumber, other.plateNumber)) {
            return false;
        }
        return true;
    }
    
    
}
