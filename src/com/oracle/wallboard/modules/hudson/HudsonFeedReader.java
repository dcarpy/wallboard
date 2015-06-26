package com.oracle.wallboard.modules.hudson;

import static com.oracle.wallboard.core.Constants.*;
import com.oracle.wallboard.Sign;
import com.oracle.wallboard.util.MapUtil;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HudsonFeedReader implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(HudsonFeedReader.class);
    private String[] labels;
    private Sign sign = new Sign();
    private String seq;

    public HudsonFeedReader(Sign sign) {
        this.sign = sign;
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

        Map<String, String> currentState = new HashMap<String, String>();
        try {
            URL feedUrl = new URL("http://msp-rslin01.us.oracle.com:8100/hudson/view/EFD/api/xml");
            //URL feedUrl = new URL("https://builds.apache.org/api/xml");
            //URL feedUrl = new URL("http://ci.jenkins-ci.org/view/Libraries/api/xml");
            Document dom = new SAXReader().read(feedUrl);
            for (Element job : (List<Element>) dom.getRootElement().elements("job")) {
                String color = job.elementText("color");
                String name = job.elementText("name");
                if (color.equals("red")) {
                    name = RED + name + " build failed";
                }
                if (color.equals("yellow")) {
                    name = YELLOW + name + " build unstable";
                }
                if (color.equals("blue")) {
                    name = GREEN + name + " build successful";
                }
                // build in progress
                if (color.endsWith("_anime")) {
                    //name = RAINBOW_2 + name + " in progress";
                    name = RAINBOW_1 + name + " build in progress";
                }
                // only active projects
                if (!color.equals("disabled")) {
                    currentState.put(name, color);
                }
            }
        } catch (Exception ex) {
            logger.error("ERROR: ", ex.getMessage());
        }
        if (!currentState.isEmpty()) {
            // sort blue, yellow, and red
            currentState = MapUtil.sortByValue(currentState);
            String[] keys = currentState.keySet().toArray(new String[0]);
            int s = keys.length - 1;
            for (int i = 0; i < labels.length; i++) {
                if (i <= s) {
                    // grab failed, unstable, and then success
                    sign.WriteStringFile(labels[i], keys[s - i].toString());
                }
            }
        }
        String runSequence = Sign.getSequenceOrder();
        if (!runSequence.contains(seq) && !currentState.isEmpty()) {
            Sign.setSequenceOrder(seq + seq, false);
        } else {
            if (runSequence.contains(seq) && currentState.isEmpty()) {
                Sign.setSequenceOrder(seq, true);
            }
        }
    }
}
