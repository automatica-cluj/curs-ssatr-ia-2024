/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.curs3;

/**
 *
 * @author mihai.hulea
 */
public class Car {
    private String plateNumber;
    private long entryTime;
    private long exitTime;

    public Car(String platenumber, long entryTime) {
        this.plateNumber = platenumber;
        this.entryTime = entryTime;
    }
       

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlatenumber(String platenumber) {
        this.plateNumber = platenumber;
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
        return "Car{" + "plateNumber=" + plateNumber + ", entryTime=" + entryTime + ", exitTime=" + exitTime + '}';
    }
    
    
    
    
    
}
