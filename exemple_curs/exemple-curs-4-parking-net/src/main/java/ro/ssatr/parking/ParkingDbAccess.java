/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.ssatr.parking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihai.hulea
 */
public class ParkingDbAccess {
      
    private Connection conn;
    
    public ParkingDbAccess() throws ClassNotFoundException, SQLException {
          Class.forName("com.mysql.cj.jdbc.Driver");
 
                //conectare la baza de date            
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab4","root","root");
    }
    
    public void insertAccessLog(Car log) throws SQLException{
       Statement s = conn.createStatement();
       s.executeUpdate("INSERT INTO CARLOG(PLATENUMBER, ENTRYTIME, EXITTIME) VALUES('"+log.getPlateNumber()+"','"+log.getEntryTime()+"','"+log.getExitTime()+"')");
       s.close(); 
    }
   
    //OK
    public void insertCar(Car e) throws SQLException{
        Statement s = conn.createStatement();
        s.executeUpdate("INSERT INTO PARKINGLOGS VALUES ('"+e.getPlateNumber()+"',"+e.getEntryTime()+",0)");        
        s.close();
    }
    
    //OK
    public Car findByPlateNumber(String plateNumber) throws SQLException{
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM PARKINGLOGS WHERE PLATENUMBER='"+plateNumber+"'");
        if(rs.next()){
            return new Car(rs.getString("platenumber"), rs.getLong("entrytime"));
        }else{
            return null;
        }           
    }
    
    public List<Car> findAll() throws SQLException{
        ArrayList<Car> cars = new ArrayList<Car>();
        
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM PARKINGLOGS");
        while(rs.next()){
            cars.add(
                    new Car(rs.getString("platenumber"), 
                            rs.getLong("entrytime")
                    )
            );
        }
        return cars;
    }
       
    public void deleteByPlateNumber(String plateNumber) throws SQLException{
        Statement s = conn.createStatement();
        s.executeUpdate("DELETE FROM PARKINGLOGS WHERE PLATENUMBER='"+plateNumber+"'");        
    }
    
    
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        ParkingDbAccess db = new ParkingDbAccess();
//        db.insertCar(new Car("AB09BFG", System.currentTimeMillis()));
//        db.insertCar(new Car("AB10BFG", System.currentTimeMillis()));
//        
//    }
}
