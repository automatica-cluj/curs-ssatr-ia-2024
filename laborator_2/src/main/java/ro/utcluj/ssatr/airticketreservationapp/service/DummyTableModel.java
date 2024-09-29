/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ro.utcluj.ssatr.airticketreservationapp.service;

import java.util.Random;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mihai
 */
public class DummyTableModel extends AbstractTableModel{

    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Random k = new Random();
        return ""+k.nextInt(100);
    }
    
}
