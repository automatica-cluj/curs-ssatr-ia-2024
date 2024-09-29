/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ssatr.lab1.exercise1;

/**
 *
 * @author mihai
 */
public class Product {
    private String productId;
    private ProductCategory productCategory;

    public Product(String productId, ProductCategory productCategory) {
        this.productId = productId;
        this.productCategory = productCategory;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productCategory=" + productCategory + '}';
    }



}
