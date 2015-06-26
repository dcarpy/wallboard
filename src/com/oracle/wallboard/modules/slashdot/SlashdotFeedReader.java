package com.oracle.wallboard.modules.slashdot;

import com.oracle.wallboard.Sign;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.commons.lang3.StringEscapeUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlashdotFeedReader implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(SlashdotFeedReader.class);
    private String[] labels;
    private Sign sign = new Sign();
    private String runSequence;
    private String seq;

    public SlashdotFeedReader(Sign sign) {
        this.sign = sign;
        this.runSequence = Sign.getSequenceOrder();
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

    @Override
    public void run() {

        //runSequence = sign.getSequenceOrder();
        List<String> titles = new ArrayList<String>();
        try {
            URL feedUrl = new URL("http://rss.slashdot.org/Slashdot/slashdot");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();
                String title = entry.getTitle();
                title = StringEscapeUtils.unescapeHtml4(title);
                // remove emphasized tags
                title = StringUtils.remove(title, "<em>");
                title = StringUtils.remove(title, "</em>");
                titles.add(title);
            }
            if (titles.isEmpty()) {
                logger.info("No slashdot news to report");
            } else {
                int s = titles.size();
                for (int i = 0; i < labels.length; i++) {
                    if (i <= (s - 1)) {
                        sign.WriteStringFile(labels[i], titles.get(i).toString());
                    } else {
                        sign.WriteStringFile(labels[i], "");
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("ERROR: ", ex);
        }
    }
}
