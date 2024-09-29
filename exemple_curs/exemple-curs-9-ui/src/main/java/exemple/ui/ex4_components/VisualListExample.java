package exemple.ui.ex4_components;

/**
 * @author Radu Miron
 * @version 1
 */


import javax.swing.*;
import java.awt.*;

public class VisualListExample {
    public static void main(String[] args) {
        // create a JFrame
        JFrame frame = new JFrame("JList Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);

        // create a JPanel
        JPanel panel = new JPanel(new FlowLayout());

        Car[] data = {
                new Car(2002, "Dacia Logan"),
                new Car(2021, "Ford Focus"),
                new Car(2018, "BMW 320")
        };

        JList<Car> list = new JList<>(data);

        // create a JScrollPane and add the JList to it
        JScrollPane scrollPane = new JScrollPane(list);

        // create a JButton to display the selected item
        JButton button = new JButton("Show Selected");
        JLabel selectedItemLabel = new JLabel("Selected Item: None");

        // add an ActionListener to the JButton
        button.addActionListener(e -> {
            Car selectedItem = list.getSelectedValue();

            if (selectedItem == null) {
                selectedItemLabel.setText("Selected Item: None");
            } else {
                selectedItemLabel.setText("Selected Item: " + selectedItem);
            }
        });

        // add components to the JPanel
        panel.add(scrollPane);
        panel.add(button);
        panel.add(selectedItemLabel);

        // add JPanel to the JFrame
        frame.getContentPane().add(panel);

        // display the frame
        frame.setVisible(true);
    }
}
