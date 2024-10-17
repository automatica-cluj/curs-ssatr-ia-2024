/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utcluj.ssatr.curs2.TrucksManager;

/**
 *
 * @author mihai
 */
public class Truck {
    private int capacity;
    private String nr;

    public Truck(int capacity, String nr) {
        this.capacity = capacity;
        this.nr =nr;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getNr() {
        return nr;
    }
    

    public void display(){
        System.out.println("Truck capacity = "+capacity);
    }

    @Override
    public String toString() {
        return nr;
    }
    
    
    
}
