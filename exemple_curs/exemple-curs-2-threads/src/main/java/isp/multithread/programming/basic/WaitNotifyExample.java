/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isp.multithread.programming.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WaitNotifyExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Wait-Notify Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            DrawingPanel drawingPanel = new DrawingPanel();
            ControlPanel controlPanel = new ControlPanel(drawingPanel);

            frame.getContentPane().add(drawingPanel, BorderLayout.CENTER);
            frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

class ControlPanel extends JPanel {
    private DrawingPanel drawingPanel;

    public ControlPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.startDrawing();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.stopDrawing();
            }
        });

        setLayout(new FlowLayout());
        add(startButton);
        add(stopButton);
    }
}

class DrawingPanel extends JPanel implements Runnable {
    private boolean isDrawing = false;
    private Object lock = new Object();
    private int k = 0;

    public DrawingPanel() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                synchronized (lock) {
                    lock.notify();
                }
            }
        });
    }

    public void startDrawing() {
        System.out.println("START DRAWING");
        isDrawing = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stopDrawing() {
        isDrawing = false;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isDrawing) {
            g.setColor(Color.RED);
            g.fillOval(180+k, 180, 40, 40);
        }
    }

        public void run() {
            System.out.println("RUN");
            while (isDrawing) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                k=k+10;
                System.out.println("Drwaing....");
                SwingUtilities.invokeLater(() -> {
                    repaint();
                });
            }
        }
    
}
