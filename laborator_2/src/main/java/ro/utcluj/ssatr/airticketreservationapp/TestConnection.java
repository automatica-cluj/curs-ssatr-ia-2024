/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
                //incarcare driver petru baza de date
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                System.out.println("Connecting...");
                //conectare la baza de date            
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flights2","root","root");
                System.out.println("Connected!");    
                //inchide cnexiune la baza de date
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
 
}