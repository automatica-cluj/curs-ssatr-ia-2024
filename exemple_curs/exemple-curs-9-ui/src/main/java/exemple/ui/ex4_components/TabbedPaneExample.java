package exemple.ui.ex4_components;

import javax.swing.*;

public class TabbedPaneExample extends JFrame {
    public TabbedPaneExample() {
        // create a JFrame
        super("JTabbedPane Example");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(400, 300);

        // create a JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();

        // create two JPanels as tabs
        JPanel tab1 = new JPanel();
        tab1.add(new JLabel("Tab 1"));
        tab1.add(new JButton("Button 1"));

        JPanel tab2 = new JPanel();
        tab2.add(new JLabel("Tab 2"));
        tab2.add(new JButton("Button 2"));

        // add tabs to the JTabbedPane
        tabbedPane.addTab("Tab 1", tab1);
        tabbedPane.addTab("Tab 2", tab2);

        // add JTabbedPane to the JFrame
        this.getContentPane().add(tabbedPane);

        // display the frame
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new TabbedPaneExample();
    }
}
