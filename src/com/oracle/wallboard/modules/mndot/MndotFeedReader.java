package com.oracle.wallboard.modules.mndot;

import static com.oracle.wallboard.core.Constants.*;
import com.oracle.wallboard.Sign;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MndotFeedReader implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(MndotFeedReader.class);
    private String[] labels;
    private Sign sign = new Sign();
    private String runSequence;
    private String seq;
    private List<String> prevTitles = new ArrayList<String>();

    public MndotFeedReader(Sign sign) {
        this.sign = sign;
    }

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public final String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public void run() {

        List<String> titles = new ArrayList<String>();
        try {
            URL feedUrl = new URL("http://www.dot.state.mn.us/tmc/trafficinfo/incidents.rss");
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
                SyndEntry entry = (SyndEntry) i.next();
                String title = entry.getTitle();
                if (title.contains("Crash")) {
                    title = RED + title.trim();
                }
                titles.add(title);
            }
            if (titles.isEmpty()) {
                logger.info("No traffic incidents to report!");
            } else {
                int s = titles.size();
                for (int i = 0; i < labels.length; i++) {
                    if (i <= (s - 1)) {
                        sign.WriteStringFile(labels[i], titles.get(i));
                    } else {
                        // better than a blank I guess
                        sign.WriteStringFile(labels[i], "www.dot.state.mn.us");
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("ERROR: ", ex.getMessage());
        }
        runSequence = Sign.getSequenceOrder();
        if (!runSequence.contains(seq) && titles.size() > 0) {
            Sign.setSequenceOrder(seq, false);
        } else {
            if (runSequence.contains(seq) && titles.isEmpty()) {
                Sign.setSequenceOrder(seq, true);
            }
        }
    }
}
