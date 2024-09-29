/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automatica.ssatr.minicalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BombGame {
    private static final int EMPTY = 0;
    private static final int BOMB = 1;
    private static final int DESTROYED_BOMB = 2;
    
    private int[][] board;
    private JButton[][] buttons;
    private int size;
    private Timer timer;
    private int time;
    private int clicks;

    public BombGame(int size, int bombCount) {
        this.size = size;
        this.board = new int[size][size];
        this.buttons = new JButton[size][size];
        this.timer = new Timer();
        this.time = 0;
        this.clicks = 0;

        // Place bombs randomly
        Random rand = new Random();
        for (int i = 0; i < bombCount; i++) {
            int x, y;
            do {
                x = rand.nextInt(size);
                y = rand.nextInt(size);
            } while (board[x][y] == BOMB);
            board[x][y] = BOMB;
        }

        // Start the timer
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                time++;
            }
        }, 0, 100);
        
        // Create the GUI
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Bomb Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(size, size));
        
        // Create buttons and add them to the frame
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int x = i;
                final int y = j;
                JButton button = new JButton();
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleClick(x, y);
                    }
                });
                frame.add(button);
                buttons[i][j] = button;
            }
        }
        
        frame.pack();
        frame.setVisible(true);
    }

    private void handleClick(int x, int y) {
        clicks++;
        
        if (board[x][y] == BOMB) {
            timer.cancel();
            JOptionPane.showMessageDialog(null, "Sorry for you !");
            return;
        }

        // Destroy bombs
        if (x > 0 && board[x - 1][y] == BOMB) { // Check left
        board[x - 1][y] = DESTROYED_BOMB;
        buttons[x - 1][y].setBackground(Color.RED);
        }
        if (x < size - 1 && board[x + 1][y] == BOMB) { // Check right
            board[x + 1][y] = DESTROYED_BOMB;
            buttons[x + 1][y].setBackground(Color.RED);
        }
        if (y > 0 && board[x][y - 1] == BOMB) { // Check up
            board[x][y - 1] = DESTROYED_BOMB;
            buttons[x][y - 1].setBackground(Color.RED);
        }
        if (y < size - 1 && board[x][y + 1] == BOMB) { // Check down
            board[x][y + 1] = DESTROYED_BOMB;
            buttons[x][y + 1].setBackground(Color.RED);
        }

        // Check if the game is over
        boolean gameIsOver = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == BOMB) {
                    gameIsOver = false;
                    break;
                }
            }
        }
        
        if (gameIsOver) {
            timer.cancel();
            JOptionPane.showMessageDialog(null, "Congratulations ! You made " + clicks + " blasts in " + time/10.0 + " seconds");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BombGame(5, 5);
            }
        });
    }
}
