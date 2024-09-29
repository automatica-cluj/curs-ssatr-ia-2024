/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aeroport;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ListaZboruri extends AbstractTableModel {
    
    String[] coloane= {"Id","Plecare","Sosire","Status","Ora plecare"};
    ArrayList<Zbor> lista = new ArrayList<>();
    
    public void addZbor(Zbor z){
        lista.add(z);
        this.fireTableDataChanged();
    }
    
    public Zbor get(int index){
        return lista.get(index);
    }
    
    public void update(Zbor z){
        for(Zbor i:lista){
            if(i.getId().equals(z.getId())){
               i.setPlecare(z.getPlecare());
               i.setSosire(z.getSosire());
               i.setOraPlecare(z.getOraPlecare());
               i.setStatus(z.getStatus());
               fireTableDataChanged();
            }
        }
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
       return 5;
    }
    
    @Override
    public String getColumnName(int col) {
        return coloane[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Zbor z = lista.get(rowIndex);
       switch(columnIndex){
           case 0:{return z.getId();}
           case 1:{return z.getPlecare();}
           case 2:{return z.getSosire();}
           case 3:{return z.getStatus();}
           case 4:{return z.getOraPlecare();}
           default:{return "N/A";}
       }
    }
    
}
