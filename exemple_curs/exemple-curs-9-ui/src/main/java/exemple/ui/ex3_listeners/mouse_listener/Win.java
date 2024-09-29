package exemple.ui.ex3_listeners.mouse_listener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class Win extends JFrame {
    Win() {
        // setup the JFrame
        super("MouseListener Example");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        // create a JPanel
        JPanel panel = new JPanel(new FlowLayout());

        // create a JButton
        JButton button = new JButton("Click me!");

        // add a MouseListener to the JLabel
        button.addMouseListener(new MouseListenerImpl());

        // add the JLabel to the JPanel
        panel.add(button);

        // add JPanel to the JFrame
        this.add(panel);

        // display the frame
        this.setVisible(true);
    }
}
