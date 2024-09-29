/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.service;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import ro.utcluj.ssatr.airticketreservationapp.model.FlightInformation;
import ro.utcluj.ssatr.airticketreservationapp.model.User;
import ro.utcluj.ssatr.airticketreservationapp.repository.DBAccess;

/**
 *
 * @author mihai
 */
public class UsersTableModel extends AbstractTableModel {

    private DBAccess connection;

    public UsersTableModel(DBAccess connection) {
        this.connection = connection;
    }   
    
    public void updateTable(){
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        try {
            return connection.findAllUsers().size();
        } catch (SQLException ex) {
            Logger.getLogger(UsersTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       try {
            List<User> list = connection.findAllUsers();
            User f = list.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return f.getUsedId();
                case 1:
                    return f.getName();               
                default:
                    return "N/A";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
