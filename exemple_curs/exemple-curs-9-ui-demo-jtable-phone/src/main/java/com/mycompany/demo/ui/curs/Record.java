/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo.ui.curs;

import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Record {
    private String name;
    private String number;

    public Record(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Record{" + "name=" + name + ", number=" + number + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.number);
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
        final Record other = (Record) obj;
        return Objects.equals(this.number, other.number);
    }
    
    
}
