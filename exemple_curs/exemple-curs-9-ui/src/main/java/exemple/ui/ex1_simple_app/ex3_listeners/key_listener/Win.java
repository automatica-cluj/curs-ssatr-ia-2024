package exemple.ui.ex1_simple_app.ex3_listeners.key_listener;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * @author Radu Miron
 * @version 1
 */
public class Win extends JFrame implements KeyListener {
    private MyCharacter myCharacter = new MyCharacter();

    Win(List<Enemy> enemies) {
        setSize(500, 500);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        myCharacter.setBounds(240, 410, 20, 20);
        this.addKeyListener(this);

        for (int i = 0; i < enemies.size(); i++) {
            int x = (500 / (enemies.size() + 1)) * (i + 1);
            enemies.get(i).setBounds(x, 10, 20, 20);
            add(enemies.get(i));
        }

        add(myCharacter);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == 'a' && myCharacter.getX() > 0) {
            myCharacter.setLocation(myCharacter.getX() - 5, myCharacter.getY());
        } else if (keyEvent.getKeyChar() == 'd' && myCharacter.getX() < 480) {
            myCharacter.setLocation(myCharacter.getX() + 5, myCharacter.getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}
