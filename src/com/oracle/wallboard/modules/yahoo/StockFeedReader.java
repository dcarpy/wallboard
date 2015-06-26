/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oracle.wallboard.modules.yahoo;

/**
 *
 * @author Dave Carpentier
 */
public class StockFeedReader implements Runnable {

    @Override
    public void run() {

        StockBean stock = StockTickerDAO.getInstance().getStockPrice("ORCL");
        System.out.println(stock.getTicker());
        System.out.println(stock.getPrice());
        System.out.println(stock.getLastUpdated());
        System.out.println(stock.getChange());
        System.out.println(stock.getChartUrlLarge());

        //System.out.println("Sequence = " + Sequence.getSequence());
    }
}
