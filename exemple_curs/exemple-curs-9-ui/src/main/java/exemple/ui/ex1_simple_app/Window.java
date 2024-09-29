package exemple.ui.ex1_simple_app;

import javax.swing.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class Window {
    public static void main(String[] args) {
        // create components
        JFrame frame = new JFrame("My Frame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("This is a label");
        JButton button = new JButton("Button");

        //TODO: Add event handler to the button

        // add components to the panel
        panel.add(label); panel.add(button);

        // sets the windows main container
        frame.setContentPane(panel);
        frame.pack(); // sets the components to preferred sizes

        // make the window visible
        frame.setVisible(true);
    }
}
