/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.model;

/**
 *
 * @author mihai
 */
public class User {
    private String usedId;
    private String name;

    public User(String usedId, String name) {
        this.usedId = usedId;
        this.name = name;
    }

    public String getUsedId() {
        return usedId;
    }

    public String getName() {
        return name;
    }
    
    
}
