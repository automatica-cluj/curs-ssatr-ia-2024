package exemple.ui.ex1_simple_app.ex3_listeners.key_listener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class Enemy extends JComponent {
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0,0,20,20);
    }
}
