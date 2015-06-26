package com.oracle.wallboard.modules.yahoo;

import com.oracle.wallboard.Sign;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahooFeedReader implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(YahooFeedReader.class);
    private String[] labels;
    private Sign sign = new Sign();
    private String runSequence;
    private String seq;
    //private String woeid = "12781922";  //Plymouth, MN
    private String woeid = "2452078";  //Minneapolis, MN

    public YahooFeedReader(Sign sign) {
        this.sign = sign;
        //this.runSequence = Sign.getSequenceOrder();
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    @Override
    public void run() {

        List<String> conditions = new ArrayList<String>();
        try {
            URL feedUrl = new URL("http://weather.yahooapis.com/forecastrss?w=" + woeid);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();
                List<Element> foreignMarkups = (List<Element>) entry.getForeignMarkup();
                for (Element foreignMarkup : foreignMarkups) {
                    if (foreignMarkup.getName().equals("condition")) {
                        String current = "Now: " + foreignMarkup.getAttributeValue("temp").trim() + "\u0008\u0049" + "F";
                        //String current = "Now: " + foreignMarkup.getAttributeValue("temp").trim() + " F";
                        conditions.add(current);
                    }
                    if (foreignMarkup.getName().equals("forecast")) {
                        String forecast = "Forecast for "
                                + foreignMarkup.getAttributeValue("day").trim() + " - "
                                + foreignMarkup.getAttributeValue("text").trim() + ". High: "
                                + foreignMarkup.getAttributeValue("high").trim() + " Low: "
                                + foreignMarkup.getAttributeValue("low").trim() + " ";
                        conditions.add(forecast);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("ERROR: ", ex.getMessage());
        }
        if (conditions.isEmpty()) {
                logger.info("No forecast information available!");
            } else {
                int s = conditions.size();
                for (int i = 0; i < labels.length; i++) {
                    if (i <= (s - 1)) {
                        sign.WriteStringFile(labels[i], conditions.get(i).toString());
                        System.out.println(conditions.get(i).toString());
                    } else {
                        // better than a blank I guess
                        sign.WriteStringFile(labels[i], "www.weather.yahooapis.com");
                    }
                }
            }
        runSequence = Sign.getSequenceOrder();
        if (!runSequence.contains(seq) && conditions.size() > 0) {
            Sign.setSequenceOrder(seq + seq, false);
        } else {
            if (runSequence.contains(seq) && conditions.isEmpty()) {
                Sign.setSequenceOrder(seq, true);
            }
        }
    }
}
