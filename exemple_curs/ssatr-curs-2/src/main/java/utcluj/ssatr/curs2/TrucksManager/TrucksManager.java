/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utcluj.ssatr.curs2.TrucksManager;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

public class TrucksManager extends AbstractListModel {

    private ArrayList<Truck> list = new ArrayList<>();

    public void addTruck(Truck t) {
        list.add(t);
    }

    public void displayAll() {
        for (Truck t : list) {
            t.display();
        }
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Object getElementAt(int index) {
        return list.get(index);
    }

    public TruckTable getTableModel() {
        return new TruckTable();
    }

    class TruckTable extends AbstractTableModel {

        private final String[] columnNames = {"Truck Number", "Capacity"};

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Truck t = list.get(rowIndex);
            return switch (columnIndex) {
                case 0 ->
                    t.getNr();
                case 1 ->
                    t.getCapacity();
                default ->
                    "N/A";
            };
        }

    }

}
