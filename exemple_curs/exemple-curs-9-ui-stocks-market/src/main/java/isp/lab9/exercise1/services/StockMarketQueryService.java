/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.lab9.exercise1.services;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses Yahoo Finance API for getting stock financial data.
 *
 * @author mihai.hulea
 */
public class StockMarketQueryService extends AbstractTableModel {
    private String[] columns = new String[]{"Name", "Symbol", "Price", "Currency", "Change", "Exchange"};
    private String[] symbols = new String[]{"INTC", "BABA", "TSLA", "AIR.PA", "MSFT", "AAPL",
            "OHI", "MPW", "MMM", "SWK", "PFE", "ABBV", "JNJ", "MDT", "RIO", "EPD", "ET", "USA",
            "BHP", "BP", "BCE", "VZ", "GOOG"};
    private List<StockItem> items = new ArrayList<>();


    /**
     * Retrieves stocks' data through the Yahoo Finance API and refreshes the table.
     *
     * @throws IOException in case of API communication errors
     */
    public void refreshMarketData() throws IOException {
        items = YahooWebClient.get(symbols);
        this.fireTableDataChanged();
    }

    /**
     * Retrieves the price for a single stock symbol.
     *
     * @param symbol the stock symbol (ticker)
     * @return the price
     * @throws IOException in case of API communication errors
     */
    public BigDecimal getStockPrice(String symbol) throws IOException {
        StockItem stock = YahooWebClient.get(symbol);
        return stock.getPrice();
    }

    /**
     * Used to populate drop down button (Combo Box).
     *
     * @return
     */
    public String[] getSymbols() {
        return symbols;
    }

    //--------------------------------------------------------------
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StockItem i = items.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return i.getName();
            case 1:
                return i.getSymbol();
            case 2:
                if (i.getPrice() != null) return i.getPrice().toPlainString();
                else return "N/A";
            case 3:
                return i.getCurrency();
            case 4:
                return i.getChange().toPlainString() + "%";
            case 5:
                return i.getExchange();
        }
        return "N/A";
    }

    @Override
    public String getColumnName(int index) {
        return columns[index];
    }

}
