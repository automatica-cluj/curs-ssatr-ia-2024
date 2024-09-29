/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.curs3;
import java.sql.*;

/**
 *
 * @author mihai.hulea
 */
public class TestConnection {
    public static void main(String[] args) {
        try {
                //incarcare driver petru baza de date
                Class.forName("com.mysql.cj.jdbc.Driver");
 
                //conectare la baza de date            
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo1","root","root");
                System.out.println("Conexiune la baza de date realizata.");
 
                //inchide cnexiune la baza de date
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
 
}
