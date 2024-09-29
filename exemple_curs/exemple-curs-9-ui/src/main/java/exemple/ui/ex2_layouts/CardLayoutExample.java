package exemple.ui.ex2_layouts;

import javax.swing.*;
import java.awt.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class CardLayoutExample {
    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("CardLayout Example");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Create the CardLayout and the JPanel to hold the cards
        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);

        // Create two JPanels as cards
        JPanel card1 = new JPanel();
        card1.setBackground(Color.WHITE);
        card1.add(new JLabel("Card 1"));
        card1.add(new JButton("Button 1"));

        JPanel card2 = new JPanel();
        card2.setBackground(Color.YELLOW);
        card2.add(new JLabel("Card 2"));
        card2.add(new JButton("Button 2"));

        // Add cards to the JPanel using CardLayout
        cards.add(card1, "Card1");
        cards.add(card2, "Card2");

        // Add JPanel to the JFrame
        frame.getContentPane().add(cards);

        // Create buttons to switch between cards
        JButton nextButton = new JButton("Next");
        JButton previousButton = new JButton("Previous");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to switch between cards
        nextButton.addActionListener(e -> cardLayout.next(cards));
        previousButton.addActionListener(e -> cardLayout.previous(cards));

        // Display the frame
        frame.setVisible(true);
    }
}
