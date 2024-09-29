package ro.ssatr.parking;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public class ParkingAccessManager2 extends AbstractTableModel {

    private ParkingDbAccess dbAccess;

    public ParkingAccessManager2() {

        try {
            dbAccess = new ParkingDbAccess();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String handleCar(String msg) {
        try {
            Car c = new Car(msg);
            if (dbAccess.findByPlateNumber(c.getPlateNumber())!=null) {      
                return "Exit car. Cost=" + exitCar(msg);
            } else {
                enterCar(c);
                return "Car parked!";
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Error!";
    }

    public void enterCar(Car c) {
        try {
            if (dbAccess.findByPlateNumber(c.getPlateNumber())==null) {
                c.setEntryTime(System.currentTimeMillis());
                try {
                    dbAccess.insertCar(c);
                } catch (SQLException ex) {
                    Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Car already exists.");
            }
            fireTableDataChanged();
        } catch (SQLException ex) {
            Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

int exitCar(String plateNumber) {
        //varianta 2

       int cost = -1;
       try {
            Car c = dbAccess.findByPlateNumber(plateNumber);
            if (c != null) {
                c.setExitTime(System.currentTimeMillis());
                cost = calculatePayment(c);
                System.out.println("Car is exiting. Payment required=" + cost);
                dbAccess.deleteByPlateNumber(plateNumber);
   
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fireTableDataChanged();
        return cost;

   }


    String getAllCarsAsString() {
        String s = "";
        try {
            List<Car> cars = dbAccess.findAll();

            for (Car c : cars) {
                s = s + c + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }

    private int calculatePayment(Car c) {
        return c.isFreeParking() ? 0 : Math.round((c.getExitTime() - c.getEntryTime()) / 1000 * 3);
    }

    ////////////////////////////////////////////
    @Override
    public int getRowCount() {
        try {
            return dbAccess.findAll().size();
        } catch (SQLException ex) {
            Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Car> cars;        
        try {
             cars = dbAccess.findAll();
             Car c = cars.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return c.getPlateNumber();
                case 1:
                    return "" + c.getEntryTime();
                case 2:
                    return "" + c.getExitTime();
                case 3:
                    return "" + c.isFreeParking();
        }
        } catch (SQLException ex) {
            Logger.getLogger(ParkingAccessManager2.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        return "N/A";
    }

}
