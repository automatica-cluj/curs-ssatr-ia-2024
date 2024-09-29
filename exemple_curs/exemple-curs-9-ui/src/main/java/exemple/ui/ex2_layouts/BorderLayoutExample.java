package exemple.ui.ex2_layouts;

import javax.swing.*;
import java.awt.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class BorderLayoutExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Border Layout Example");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane(); //this is optional; a component can be added directly to the window

        JButton button = new JButton("Button 1 (PAGE_START)");
        pane.add(button, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        centerPanel.add(new JLabel("This is a label"));
        centerPanel.add(new JButton("This is a Button"));
        JComboBox<String> dropDown = new JComboBox<String>();
        dropDown.addItem("op1");
        dropDown.addItem("op2");
        dropDown.addItem("op3");
        centerPanel.add(dropDown);
        pane.add(centerPanel, BorderLayout.CENTER);

        button = new JButton("Button 3 (LINE_START)");
        pane.add(button, BorderLayout.LINE_START);

        button = new JButton("Long-Named Button 4 (PAGE_END)");
        pane.add(button, BorderLayout.PAGE_END);

        button = new JButton("5 (LINE_END)");
        pane.add(button, BorderLayout.LINE_END);

        frame.setSize(500, 200);
        frame.setVisible(true);
    }
}
