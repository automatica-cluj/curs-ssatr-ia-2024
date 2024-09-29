package exemple.ui.ex4_components;

import javax.swing.*;

/**
 * @author Radu Miron
 * @version 1
 */
public class ComboBoxAndScrollableTextAreaExample extends JFrame {
    public ComboBoxAndScrollableTextAreaExample() {
        //setup the window
        super("Combobox Example");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setBounds(200, 200, 400, 300);

        //create and add components
        JLabel label = new JLabel("Choose one option:");
        label.setBounds(20, 20, 160, 20);
        this.add(label);

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(220, 20, 120, 20);
        comboBox.addItem("Option 1");
        comboBox.addItem("Option 2");
        comboBox.addItem("Option 3");
        this.add(comboBox);

        JButton button = new JButton("Print chosen option");
        button.setBounds(20, 60, 320, 20);
        this.add(button);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 100, 320, 100);
        this.add(scrollPane);

        //add event listener
        button.addActionListener(e -> textArea.append(comboBox.getSelectedIndex()
                + ". " + comboBox.getSelectedItem() + "\n"));

        //display
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ComboBoxAndScrollableTextAreaExample();
    }
}
