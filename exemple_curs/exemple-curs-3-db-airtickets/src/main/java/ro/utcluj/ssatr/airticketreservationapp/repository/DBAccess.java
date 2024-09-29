/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.repository;

import ro.utcluj.ssatr.airticketreservationapp.model.FlightInformation;
import ro.utcluj.ssatr.airticketreservationapp.model.FlightReservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihai.hulea
 */
public class DBAccess {
     private Connection connection;
    
    public DBAccess() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //conectare la baza de date            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flights","root","root");
       }
    
    public void insertFlight(FlightInformation f) throws SQLException{
        try(Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO FLIGHTS(FLIGHTNUMBER, NOOFSEATS, DEPARTUREDATE) VALUES('" + f.getFlightNumber() + "'," + f.getNumberOfSeats() + ",'" + f.getDepartureDate() + "')");
        }
        
//        Statement s = null;
//        try{
//            s = connection.createStatement();
//            s.executeUpdate("INSERT INTO FLIGHTS(FLIGHTNUMBER, NOOFSEATS, DEPARTUREDATE) VALUES('" + f.getFlightNumber() + "'," + f.getNumberOfSeats() + ",'" + f.getDepartureDate() + "')");
//        }finally{
//            if(s!=null)
//                s.close();
//        }
        
    }

    public void insertReservation(FlightReservation reservation) throws SQLException {
        try(Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO RESERVATIONS(FLIGHTNUMBER, NOOFTICKETS) VALUES('" + reservation.getFlightNumber() + "'," + reservation.getNoOfTickets() + ")");
        }
    }
   

    public FlightInformation findFlight(String flightNumber) throws SQLException{
        try(Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT * FROM FLIGHTS WHERE FLIGHTNUMBER='" + flightNumber + "'");
            if (rs.next()) {
                return new FlightInformation(
                        rs.getString("FLIGHTNUMBER"), 
                        rs.getInt("NOOFSEATS"), 
                        rs.getString("DEPARTUREDATE"));
            } else {
                return null;
            }
        }
    }

    public List<FlightInformation> findAll() throws SQLException{
        try(Statement s = connection.createStatement()) {
            ArrayList<FlightInformation> list = new ArrayList<>();

            ResultSet rs = s.executeQuery("SELECT * FROM FLIGHTS");
            while (rs.next()) {
                list.add(new FlightInformation(rs.getString("FLIGHTNUMBER"), rs.getInt("NOOFSEATS"), rs.getString("DEPARTUREDATE")));
            }
            return list;
        }

    }

   
    public void makeReservation(FlightReservation reservation){
        Statement statement = null;
        ResultSet resultSet = null;
        System.out.println("PREPARE TO MAKE RESERVATION");
        try {
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String lockQuery = "SELECT * FROM reservations FOR UPDATE";
            resultSet = statement.executeQuery(lockQuery);

            // Perform your updates on the table here
            FlightInformation f = findFlight(reservation.getFlightNumber());
            updateSeats(reservation.getFlightNumber(), f.getNumberOfSeats()-reservation.getNoOfTickets());
            insertReservation(reservation);
            System.out.println("RESERVATION COMPLETE");

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    // Handle rollback failure
                    ex.printStackTrace();
                }
            }
            // Handle the exception
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle result set closure failure
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle statement closure failure
                }
            }
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle connection closure failure
                }
            }
        }

    }

    public void cancelReservation(int reservationId){

    }


    void updateSeats(String flightNumber, int noOfTikets) throws SQLException {
        try(Statement s = connection.createStatement()) {
            s.executeUpdate("UPDATE flights SET NOOFSEATS=" + noOfTikets + " WHERE FLIGHTNUMBER='" + flightNumber + "'");
        }
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBAccess db = new DBAccess();
        //db.insertFlight(new FlightInformation("CJB01", 140, "19-01-2023"));
        db.updateSeats("CJB02", 199);
    }
}
