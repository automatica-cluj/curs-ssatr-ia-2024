/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.repository;

import ro.utcluj.ssatr.airticketreservationapp.model.FlightInformation;
import ro.utcluj.ssatr.airticketreservationapp.model.FlightReservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ro.utcluj.ssatr.airticketreservationapp.model.User;

/**
 *
 * @author mihai.hulea
 */
public class DBAccess {

    private Connection connection;

    public DBAccess() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        //conectare la baza de date            
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/flights", "root", "root");
    }

    public void insertFlight(FlightInformation f) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO FLIGHTS(FLIGHTNUMBER, NOOFSEATS, DEPARTUREDATE) VALUES(?,?,?)");
            ps.setString(1, f.getFlightNumber());
            ps.setInt(2, f.getNumberOfSeats());
            ps.setString(3, f.getDepartureDate());
            ps.executeUpdate();
            //          s.executeUpdate("INSERT INTO FLIGHTS(FLIGHTNUMBER, NOOFSEATS, DEPARTUREDATE) VALUES('" + f.getFlightNumber() + "'," + f.getNumberOfSeats() + ",'" + f.getDepartureDate() + "')");
        }
    }

    private void insertReservation(FlightReservation reservation) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO RESERVATIONS(FLIGHTNUMBER, NOOFTICKETS) VALUES('" + reservation.getFlightNumber() + "'," + reservation.getNoOfTickets() + ")");
        }

    }

    public FlightInformation findFlight(String flightNumber) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT * FROM FLIGHTS WHERE FLIGHTNUMBER='" + flightNumber + "'");
            if (rs.next()) {
                return new FlightInformation(rs.getString("FLIGHTNUMBER"), rs.getInt("NOOFSEATS"), rs.getString("DEPARTUREDATE"));
            } else {
                return null;
            }
        }
    }

    public void deleteFlight(String flightNumber) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("DELETE FROM FLIGHTS WHERE FLIGHTNUMBER='" + flightNumber + "'");
        }
    }

    public List<FlightInformation> findAll() throws SQLException {
        try ( Statement s = connection.createStatement()) {
            ArrayList<FlightInformation> list = new ArrayList<>();

            ResultSet rs = s.executeQuery("SELECT * FROM FLIGHTS");
            while (rs.next()) {
                list.add(new FlightInformation(rs.getString("FLIGHTNUMBER"), rs.getInt("NOOFSEATS"), rs.getString("DEPARTUREDATE")));
            }
            return list;
        }

    }
    
     public List<User> findAllUsers() throws SQLException {
        try ( Statement s = connection.createStatement()) {
            ArrayList<User> list = new ArrayList<>();

            ResultSet rs = s.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                list.add(new User(rs.getString("IDUSERS"), rs.getString("NAME")));
            }
            return list;
        }

    }

    public void makeReservation(FlightReservation reservation) {
        Statement statement = null;
        ResultSet resultSet = null;
        System.out.println("PREPARE TO MAKE RESERVATION");
        try {
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            //read about SELECT FOR UPDATE gere https://www.cockroachlabs.com/blog/select-for-update/
            String lockQuery = "SELECT * FROM reservations FOR UPDATE";
            resultSet = statement.executeQuery(lockQuery);

            // Perform your updates on the table here
            FlightInformation f = findFlight(reservation.getFlightNumber());
            if (f.getNumberOfSeats() - reservation.getNoOfTickets() >= 0) {
                updateSeats(reservation.getFlightNumber(), f.getNumberOfSeats() - reservation.getNoOfTickets());
                insertReservation(reservation);
                System.out.println("RESERVATION COMPLETE");
                connection.commit();
            }else{
                throw new SQLException("Error reservation, no seats available.");
            }

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
    
//    public void insertUser(String userId, String userName){
//        try ( Statement s = connection.createStatement()) {
//            s.executeUpdate("INSERT INTO USERS(IDUSERS, NAME) VALUES('" + userId + "','" + userName + "')");
//        } catch (SQLException ex) {
//            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void cancelReservation(int reservationId) {

    }

    void updateSeats(String flightNumber, int noOfTikets) throws SQLException {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("UPDATE flights SET NOOFSEATS=" + noOfTikets + " WHERE FLIGHTNUMBER='" + flightNumber + "'");
        }
    }

    public void insertUser(User u) {
        try ( Statement s = connection.createStatement()) {
            s.executeUpdate("INSERT INTO USERS(IDUSERS, NAME) VALUES('" + u.getUsedId() + "','" + u.getName() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DBAccess db = new DBAccess();
        System.out.println("Connection ok...");
        db.insertUser(new User("PrimulUser","Nume User"));
        //db.insertFlight(new FlightInformation("SM0101", 140, "19-01-2023"));
        //db.insertUser("u1", "Demo User");
        //db.updateSeats("CJB01", 199);
    }


}
