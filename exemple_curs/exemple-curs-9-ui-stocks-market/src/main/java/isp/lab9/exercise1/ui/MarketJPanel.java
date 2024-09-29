package isp.lab9.exercise1.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarketJPanel extends JPanel {

    private StockMarketJFrame frame;

    public MarketJPanel(StockMarketJFrame frame){
        this.frame = frame;
        initComponent();
    }
    private void initComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JTable jTableMarket = new JTable();
        jTableMarket.setModel(frame.getMarketService());
        JScrollPane marketScrollablePane = new JScrollPane(jTableMarket);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(evt -> buttonRefreshActionPerformed(evt));

        add(marketScrollablePane);
        add(refreshButton);

    }

    /**
     * Refreshes the stock data.
     */
    private void buttonRefreshActionPerformed(ActionEvent evt) {
        try {
            frame.getMarketService().refreshMarketData();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(StockMarketJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
