package exemple.ui.ex1_simple_app.ex3_listeners.action_listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Radu Miron
 * @version 1
 */
public class Window extends JFrame {
    Window() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container pane = this.getContentPane();
        pane.setLayout(new GridLayout(3, 1));

        JButton b1 = new JButton("Button 1");
        JButton b2 = new JButton("Button 2");
        JButton b3 = new JButton("Button 3");

        b1.addActionListener(new ButtonHandler1(this));
        b2.addActionListener(new ButtonHandler2());
        b3.addActionListener(e -> System.out.println(((JButton) e.getSource()).getText()));

        pane.add(b1);
        pane.add(b2);
        pane.add(b3);

        this.setSize(400, 200);
        this.setVisible(true);
    }

    public class ButtonHandler2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JOptionPane.showMessageDialog(Window.this,
                    actionEvent.getActionCommand(),
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
