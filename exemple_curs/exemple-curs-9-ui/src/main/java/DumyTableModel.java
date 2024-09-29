
import java.util.Random;
import javax.swing.table.AbstractTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mihai
 */
public class DumyTableModel extends AbstractTableModel{

    @Override
    public int getRowCount() {
        return 10;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Random r = new Random();
        return r.nextInt(100);
    }
    
}
