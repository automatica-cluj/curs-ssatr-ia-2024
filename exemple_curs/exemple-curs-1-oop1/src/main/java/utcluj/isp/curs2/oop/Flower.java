/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utcluj.isp.curs2.oop;

/**
 * Demonstrates the use of static variables and methods.
 */
public class Flower {
    
    private static int count = 0;
    
    public Flower() {
        count++;
    }
    
    public static int getCount() {
        return count;
    }
    
    public static void main(String[] args) {
        Flower flower1 = new Flower();
        Flower flower2 = new Flower();
        Flower flower3 = new Flower();
        System.out.println("Number of flowers created: " + Flower.getCount());
    }
}
