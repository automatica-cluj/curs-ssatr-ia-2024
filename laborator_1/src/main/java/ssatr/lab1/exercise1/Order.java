/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ssatr.lab1.exercise1;

import java.util.ArrayList;

/**
 *
 * @author mihai
 */
public class Order {
    //private Product[] products = new Product[10];
    ArrayList<Product> products = new ArrayList<>();
    private String orderId;

    public Order(String orderId) {
        this.orderId = orderId;
    }
    
    public Order(String orderId, ArrayList<Product> products) {
        this.orderId = orderId;
        this.products = products;
    }
    
    public void addProduct(Product p){
        products.add(p);
    }
    
}
