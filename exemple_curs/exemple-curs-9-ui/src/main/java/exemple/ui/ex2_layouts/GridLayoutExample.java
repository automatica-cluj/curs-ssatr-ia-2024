package exemple.ui.ex2_layouts;

import javax.swing.*;
import java.awt.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class GridLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Grid Layout Example");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container pane = frame.getContentPane();
        pane.setLayout(new GridLayout(3, 2));

        for (int i = 0; i < 5; i++) {
            pane.add(new JButton("B" + i));
        }

        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
