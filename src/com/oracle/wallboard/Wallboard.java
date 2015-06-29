package com.oracle.wallboard;

import com.oracle.wallboard.modules.hudson.HudsonFeedReader;
import com.oracle.wallboard.modules.mndot.MndotFeedReader;
import com.oracle.wallboard.modules.slashdot.SlashdotFeedReader;
import com.oracle.wallboard.modules.yahoo.StockFeedReader;
import com.oracle.wallboard.modules.yahoo.YahooFeedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dave Carpentier
 */
public class Wallboard {

    // Initial sequence
    private String sequenceOrder = "6789";
    private static Logger logger = LoggerFactory.getLogger(Wallboard.class);

    public void run() throws FileNotFoundException {

        // Toplevel directory
        File topDir = new File(System.getProperty("user.dir"));

        // Load properties file
        PropertiesConfiguration config = new PropertiesConfiguration();
        File configFile = new File(topDir, "wallboard.properties");
        try {
            config.load(new FileInputStream(configFile));
        } catch (ConfigurationException e) {
            System.out.println("Exception : " + e);
            return;
        }

        // Indicates what the change log is based on
        String proxyHost = config.getString("wallboard.proxy.host");
        String proxyPort = config.getString("wallboard.proxy.port", "80");
        String proxyUser = config.getString("wallboard.proxy.user");
        String proxyPassword = config.getString("wallboard.proxy.password");

        if (proxyHost != null) {
            System.setProperty("http.proxyHost", proxyHost);
        }
        System.setProperty("http.proxyPort", proxyPort);
        if (proxyUser != null) {
            System.setProperty("http.proxyUser", proxyUser);
        }
        if (proxyPassword != null) {
            System.setProperty("http.proxyPassword", proxyPassword);
        }

        Sign sign = new Sign();
        sign.ClearMemory();
        sign.SetTimeOfDay();
        sign.SetDayOfWeek();
        sign.SetDate();
        sign.ConfigureMemory();

        SlashdotFeedReader slashdotFeedReader = new SlashdotFeedReader(sign);
        slashdotFeedReader.setLabels(sign.getLabels('6'));
        slashdotFeedReader.setSeq("6");

        MndotFeedReader mndotFeedReader = new MndotFeedReader(sign);
        mndotFeedReader.setLabels(sign.getLabels('7'));
        mndotFeedReader.setSeq("7");

        HudsonFeedReader hudsonFeedReader = new HudsonFeedReader(sign);
        hudsonFeedReader.setLabels(sign.getLabels('8'));
        hudsonFeedReader.setSeq("8");

        YahooFeedReader yahooFeedReader = new YahooFeedReader(sign);
        yahooFeedReader.setLabels(sign.getLabels('9'));
        yahooFeedReader.setSeq("9");

        StockFeedReader stockFeedReader = new StockFeedReader();

        // start "8" 38H = Monday-Friday, stop "1" 31H = Sunday
        sign.SetRunDay(slashdotFeedReader.getSeq(), "8", "1");
        sign.SetRunDay(mndotFeedReader.getSeq(), "8", "1");
        sign.SetRunDay(hudsonFeedReader.getSeq(), "8", "1");
        sign.SetRunDay(yahooFeedReader.getSeq(), "8", "1");

        // start 7:00am - 2AH, stop 7:00pm - 72H
        sign.SetRunTime(slashdotFeedReader.getSeq(), "2A", "72");
        sign.SetRunTime(mndotFeedReader.getSeq(), "2A", "72");
        sign.SetRunTime(hudsonFeedReader.getSeq(), "2A", "72");
        sign.SetRunTime(yahooFeedReader.getSeq(), "2A", "72");

        // specify the initial Run Sequence
        Sign.setSequenceOrder(sequenceOrder, false);

        sign.WriteTextFileLabel(slashdotFeedReader.getSeq(), "\u0013", slashdotFeedReader.getLabels());
        sign.WriteTextFileLabel(mndotFeedReader.getSeq(), "Traffic", mndotFeedReader.getLabels());
        sign.WriteTextFileLabel(hudsonFeedReader.getSeq(), "Jenkins CI", hudsonFeedReader.getLabels());
        sign.WriteTextFileLabel9();

        final ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        // fire every 1 minute
        scheduledThreadPool.scheduleAtFixedRate(hudsonFeedReader, 0, 1, TimeUnit.MINUTES);
        // fire every 30 minutes
        scheduledThreadPool.scheduleAtFixedRate(slashdotFeedReader, 0, 30, TimeUnit.MINUTES);
        // fire every 15 minutes
        scheduledThreadPool.scheduleAtFixedRate(mndotFeedReader, 0, 15, TimeUnit.MINUTES);
        // fire every 10 minutes
        scheduledThreadPool.scheduleAtFixedRate(yahooFeedReader, 0, 10, TimeUnit.MINUTES);
        // fire every 20 minutes
        scheduledThreadPool.scheduleAtFixedRate(stockFeedReader, 0, 20, TimeUnit.MINUTES);

        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                logger.info("Shutdown");
                scheduledThreadPool.shutdown();
            }
        }));
    }

    public static void main(String[] args) throws Exception {

        Wallboard wallboard = new Wallboard();
        wallboard.run();
    }
}
