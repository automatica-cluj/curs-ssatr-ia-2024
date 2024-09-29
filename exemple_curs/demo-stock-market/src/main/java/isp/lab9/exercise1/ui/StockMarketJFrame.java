/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package isp.lab9.exercise1.ui;

import isp.lab9.exercise1.services.UserPortfolio;
import isp.lab9.exercise1.services.StockMarketQueryService;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mihai.hulea
 * @author radu.miron
 */
public class StockMarketJFrame extends JFrame {
    private StockMarketQueryService marketService;
    private UserPortfolio portfolio;

    /**
     * Creates new form StockMarketJFrame
     */
    public StockMarketJFrame() {
        try {
            marketService = new StockMarketQueryService();
            marketService.refreshMarketData();

            portfolio = new UserPortfolio(new BigDecimal(1000), new TreeMap<>());
        } catch (IOException ex) {
            Logger.getLogger(StockMarketJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        initComponents();
        setVisible(true);
    }

    /**
     * Initializes the window with the tabs and main panels. Each panel is definied in its own class.
     */
    private void initComponents() {
        this.setSize(700, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // configure windows the tabs
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Market", new MarketJPanel(this));
        tabs.addTab("UserPortfolio", new PortfolioJPanel());
        tabs.addTab("Buy", new BuyJPanel(this));
        tabs.addTab("Sell", new SellJPanel());

        this.add(tabs);
    }

    public StockMarketQueryService getMarketService() {
        return marketService;
    }

    public UserPortfolio getPortfolio() {
        return portfolio;
    }
}
