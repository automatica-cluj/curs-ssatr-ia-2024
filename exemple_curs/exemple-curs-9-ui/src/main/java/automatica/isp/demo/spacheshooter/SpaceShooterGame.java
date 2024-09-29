package automatica.isp.demo.spacheshooter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpaceShooterGame extends JFrame {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private Timer timer;

    private int playerX;
    private int enemyX;
    private int enemyY;

    private List<Bullet> bullets;

    public SpaceShooterGame() {
        setTitle("Space Shooter Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        playerX = WIDTH / 2;
        enemyX = WIDTH / 2;
        enemyY = 50;

        bullets = new ArrayList<>();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_LEFT) {
                    playerX -= 5;
                } else if (key == KeyEvent.VK_RIGHT) {
                    playerX += 5;
                } else if (key == KeyEvent.VK_SPACE) {
                    bullets.add(new Bullet(playerX + 25, HEIGHT - 80));
                }

                repaint();
            }
        });

        timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemyY += 2;

                if (enemyY > HEIGHT) {
                    enemyX = (int) (Math.random() * (WIDTH - 50));
                    enemyY = 50;
                }

                Iterator<Bullet> bulletIterator = bullets.iterator();
                while (bulletIterator.hasNext()) {
                    Bullet bullet = bulletIterator.next();
                    bullet.update();

                    if (bullet.getY() < 0) {
                        bulletIterator.remove();
                    } else if (detectCollision(bullet, enemyX, enemyY)) {
                        bulletIterator.remove();
                        enemyX = (int) (Math.random() * (WIDTH - 50));
                        enemyY = 50;
                    }
                }

                repaint();
            }
        });

        timer.start();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(playerX, HEIGHT - 50, 50, 50);

        g.setColor(Color.RED);
        g.fillRect(enemyX, enemyY, 50, 50);

        g.setColor(Color.BLUE);
        for (Bullet bullet : bullets) {
            g.fillRect(bullet.getX(), bullet.getY(), 5, 10);
        }
    }

    private boolean detectCollision(Bullet bullet, int enemyX, int enemyY) {
        int bulletLeft = bullet.getX();
        int bulletRight = bullet.getX() + 5;
        int bulletTop = bullet.getY();
        int bulletBottom = bullet.getY() + 10;

        int enemyLeft = enemyX;
        int enemyRight = enemyX + 50;
        int enemyTop = enemyY;
        int enemyBottom = enemyY + 50;

        return bulletRight >= enemyLeft && bulletLeft <= enemyRight && bulletBottom >= enemyTop && bulletTop <= enemyBottom;
    }

    private class Bullet {
        private int x;
        private int y;

        public Bullet(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void update() {
            y -= 5;
        }
    }

    public static void main(String[] args) {
        new SpaceShooterGame();
    }
}
