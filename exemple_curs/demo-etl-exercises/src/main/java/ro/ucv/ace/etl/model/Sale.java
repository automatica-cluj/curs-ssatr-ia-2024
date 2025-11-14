package ro.ucv.ace.etl.model;

import java.time.LocalDate;

/**
 * Sale transaction data model
 */
public class Sale {
    private int saleId;
    private int customerId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private LocalDate saleDate;
    private String category;

    public Sale() {
    }

    public Sale(int saleId, int customerId, String productName, int quantity, double unitPrice, LocalDate saleDate, String category) {
        this.saleId = saleId;
        this.customerId = customerId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.saleDate = saleDate;
        this.category = category;
    }

    // Getters and Setters
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTotalAmount() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", customerId=" + customerId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", saleDate=" + saleDate +
                ", category='" + category + '\'' +
                ", totalAmount=" + getTotalAmount() +
                '}';
    }
}
