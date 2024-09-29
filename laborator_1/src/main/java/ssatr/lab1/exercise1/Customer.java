/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ssatr.lab1.exercise1;

import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Customer {
   private Address address;
   private String customerId;

    public Customer(Address address, String customerId) {
        this.address = address;
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.customerId);
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
        final Customer other = (Customer) obj;
        return Objects.equals(this.customerId, other.customerId);
    }

    @Override
    public String toString() {
        return "Customer{" + "address=" + address + ", customerId=" + customerId + '}';
    }
    
    
   
   
}
