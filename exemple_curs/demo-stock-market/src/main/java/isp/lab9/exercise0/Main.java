/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isp.lab9.exercise0;

import isp.lab9.exercise1.services.StockItem;
import isp.lab9.exercise1.services.YahooWebClient;

import java.math.BigDecimal;

/**
 * @author mihai.hulea
 */
public class Main {
    public static void main(String[] args) throws Exception {
        StockItem stock = YahooWebClient.get("INTC");

        BigDecimal price = stock.getPrice();
        BigDecimal change = stock.getChange();
        //etc.

        System.out.println(stock);
        System.out.println("*************************************");


        String[] symbols = new String[]{"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
        YahooWebClient.get(symbols)
                .forEach(System.out::println);
        System.out.println("*************************************");

    }
}
