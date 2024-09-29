/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatica.isp.demo.air.service;

import automatica.isp.demo.air.model.Airway;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mihai
 */
public class AirwaysManager extends AbstractTableModel{
    
    private ArrayList<Airway> airways = new ArrayList<Airway>();

    public void addAirway(Airway airway){
        airways.add(airway);
    }

    public void setAirways(ArrayList<Airway> airways) {
        this.airways = airways;
    }

    public void saveAll(){
        try {
            SerializableUtil.saveSerializedObjects(airways, "c:\\tmp2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public int getRowCount() {
        return airways.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return airways.get(rowIndex);
            case 1:
                return airways.get(rowIndex).getDeparture();
            case 2:
                return airways.get(rowIndex).getDestination();
            case 3:
                return airways.get(rowIndex).getWaypoints().size();
            default:
                return "N/A";
        }
    }
}
