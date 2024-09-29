/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demo.ui.curs;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mihai
 */
public class PhoneRecords extends AbstractTableModel {
    private ArrayList<Record> list = new ArrayList<>();
    
    public void addRecord(Record r){
        list.add(r);
        System.out.println("New record added: "+r);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Record r = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> rowIndex;
            case 1 -> r.getName();
            case 2 -> r.getNumber();
            default -> "N/A";
        };

    }
}
