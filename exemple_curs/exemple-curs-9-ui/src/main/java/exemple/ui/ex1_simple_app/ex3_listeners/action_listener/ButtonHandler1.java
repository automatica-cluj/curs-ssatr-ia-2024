package exemple.ui.ex1_simple_app.ex3_listeners.action_listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Radu Miron
 * @version 1
 */
public class ButtonHandler1 implements ActionListener {
    private JFrame parentContainer;

    public ButtonHandler1(JFrame parentContainer) {
        this.parentContainer = parentContainer;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(parentContainer,
                actionEvent.getActionCommand(),
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
