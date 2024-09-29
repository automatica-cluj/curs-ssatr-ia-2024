package exemple.ui.ex3_listeners.mouse_listener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author Radu Miron
 * @version 1
 */
class MouseListenerImpl implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        JOptionPane.showMessageDialog(null,
                "You clicked the JLabel!",
                "Mouse Clicked",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Mouse entered the button");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Mouse exited the button");
    }
}
