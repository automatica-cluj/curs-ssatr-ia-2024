/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utcluj.isp.curs2.oop;

/**
 * Demonstrates the use of constructors.
 */
public class Car {
    
    private String make;
    private String model;
    private int year;
    private double price;
    
    public Car() {
        this("Dacia", "Spring");
    }
    
    public Car(String make, String model, int year, double price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }
    
    public Car(String make, String model, int year) {
        this(make, model, year, 0.0);
    }
    
    public Car(String make, String model, double price) {
        this(make, model, 0, price);
    }
    
    public Car(String make, String model) {
        this(make, model, 0, 0.0);
    }
    
    public void printInfo() {
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Price: $" + price);
    }

    public static void main(String[] args) {
        Car car1 = new Car("Ford", "Fiesta", 2015, 15000.0);
        Car car2 = new Car("Ford", "Fiesta", 2015);
        Car car3 = new Car("Ford", "Fiesta");
        Car car4 = new Car("Ford", "Fiesta", 15000.0);
        Car car5 = new Car("Ford", "Fiesta", 2015, 15000.0);

        car1.printInfo();
        car2.printInfo();
        car3.printInfo();
        car4.printInfo();
        car5.printInfo();
    }
    
}
