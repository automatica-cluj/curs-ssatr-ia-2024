package exemple.ui.ex4_components;

/**
 * @author Radu Miron
 * @version 1
 */

import javax.swing.*;
import java.awt.*;

public class CheckBoxExample {
    public static void main(String[] args) {
        // create a JFrame
        JFrame frame = new JFrame("JCheckBox Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // create a JPanel
        JPanel panel = new JPanel(new FlowLayout());

        // create a JCheckBox
        JCheckBox checkBox = new JCheckBox("Check me!");

        // create a JLabel to display the status
        JLabel statusLabel = new JLabel("Status: Unchecked");

        // add an ActionListener to the JCheckBox
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                statusLabel.setText("Status: Checked");
            } else {
                statusLabel.setText("Status: Unchecked");
            }
        });

        // add components to the JPanel
        panel.add(checkBox);
        panel.add(statusLabel);

        // add JPanel to the JFrame
        frame.getContentPane().add(panel);

        // display the frame
        frame.setVisible(true);
    }
}