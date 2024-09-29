package exemple.ui.ex4_components;

/**
 * @author Radu Miron
 * @version 1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RadioButtonsExample {
    public static void main(String[] args) {
        // create a JFrame
        JFrame frame = new JFrame("JRadioButton Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // create a JPanel
        JPanel panel = new JPanel(new FlowLayout());

        // create JRadioButtons
        JRadioButton radioButton1 = new JRadioButton("Option 1");
        JRadioButton radioButton2 = new JRadioButton("Option 2");
        JRadioButton radioButton3 = new JRadioButton("Option 3");

        // create a ButtonGroup to group the JRadioButtons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);

        // create a JLabel to display the selected option
        JLabel selectedOptionLabel = new JLabel("Selected Option: None");

        // add ActionListeners to the JRadioButtons
        ActionListener radioButtonListener = e -> selectedOptionLabel.setText("Selected Option: " + e.getActionCommand());

        radioButton1.addActionListener(radioButtonListener);
        radioButton2.addActionListener(radioButtonListener);
        radioButton3.addActionListener(radioButtonListener);

        // add components to the JPanel
        panel.add(radioButton1);
        panel.add(radioButton2);
        panel.add(radioButton3);
        panel.add(selectedOptionLabel);

        // add JPanel to the JFrame
        frame.getContentPane().add(panel);

        // display the frame
        frame.setVisible(true);
    }
}
