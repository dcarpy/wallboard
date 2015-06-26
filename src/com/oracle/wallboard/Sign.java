package com.oracle.wallboard;

import static com.oracle.wallboard.core.Constants.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dave Carpentier
 */
public class Sign {

    private static Logger logger = LoggerFactory.getLogger(Sign.class);
    // the Type Code for a 4120C sign
    private static final String signTypeCode = "a";
    // the identifier or "address" of the sign
    private static final String signAddress = "00";
    // the name of the serial port
    //private static final String PORT = "COM4";
    private static final String PORT = "/dev/ttyUSB0";
    private String[] slashdotLabels = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private String[] mndotLabels = {"I", "J", "K", "L", "M"};
    private String[] hudsonLabels = {"N", "O", "P", "Q", "R", "S"};
    private String[] yahooLabels = {"T", "U", "V", "W", "X", "Y"};
    private static String sequenceOrder = "";
    private static String transBegin = NUL + SOH + signTypeCode + signAddress + STX;

    /**
     *
     */
    public Sign() {
    }

    public String[] getLabels(char seq) {
        String[] labels = null;
        switch (seq) {
            case '6':
                labels = slashdotLabels;
                break;
            case '7':
                labels = mndotLabels;
                break;
            case '8':
                labels = hudsonLabels;
                break;
            case '9':
                labels = yahooLabels;
                break;
        }
        return labels;
    }

    public static String getSequenceOrder() {
        return sequenceOrder;
    }

    public static void setSequenceOrder(String seq, boolean remove) {
        String prevSequenceOrder = sequenceOrder;
        if (remove) {
            sequenceOrder = sequenceOrder.replaceAll(seq, "");
        } else {
            sequenceOrder = seq + sequenceOrder;
        }
        if (!prevSequenceOrder.equals(sequenceOrder)) {
            SetRunSequence(sequenceOrder);
            logger.info("Sequence order set to {}", sequenceOrder);
        }
    }

    private synchronized static void sendString(String str) {

        if (str.length() > 0) {
            SerialPort serialPort = new SerialPort(PORT);
            try {
                serialPort.openPort();
                //serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_7, SerialPort.STOPBITS_2, SerialPort.PARITY_EVEN);
                serialPort.writeBytes(str.getBytes());
                serialPort.closePort();
            } catch (SerialPortException ex) {
                logger.error("ERROR: ", ex.getExceptionType());
            }
        }
    }

    /**
     * Set memory configuration
     */
    public void ConfigureMemory() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append("$");
        // text file in memory location 6
        cmd.append("6");     // file label
        cmd.append("A");     // TEXT file type
        cmd.append(UNLOCKED);
        cmd.append("1000");  // 4096
        cmd.append("FE");    // start time
        cmd.append("00");    // stop time
        // string file in memory location
        for (int i = 0; i < slashdotLabels.length; i++) {
            cmd.append(slashdotLabels[i]);
            cmd.append("B");     // STRING file type
            cmd.append(LOCKED);
            cmd.append("0100");  // 256
            cmd.append("0000");  // padding
        }
        // text file in memory location 7
        cmd.append("7");
        cmd.append("A");
        cmd.append(UNLOCKED);
        cmd.append("0400");  // 1024
        cmd.append("FE");
        cmd.append("00");
        for (int i = 0; i < mndotLabels.length; i++) {
            cmd.append(mndotLabels[i]);
            cmd.append("B");
            cmd.append(LOCKED);
            cmd.append("0080");  // 128
            cmd.append("0000");
        }
        // text file in memory location 8
        cmd.append("8");
        cmd.append("A");
        cmd.append(UNLOCKED);
        cmd.append("0400");  // 1024
        cmd.append("FE");
        cmd.append("00");
        for (int i = 0; i < hudsonLabels.length; i++) {
            cmd.append(hudsonLabels[i]);
            cmd.append("B");
            cmd.append(LOCKED);
            cmd.append("0080");  // 128
            cmd.append("0000");
        }
        // text file in memory location 9
        cmd.append("9");
        cmd.append("A");
        cmd.append(UNLOCKED);
        cmd.append("1000");  // 4096
        cmd.append("FE");
        cmd.append("00");
        for (int i = 0; i < yahooLabels.length; i++) {
            cmd.append(yahooLabels[i]);
            cmd.append("B");
            cmd.append(LOCKED);
            cmd.append("0080");  // 128
            cmd.append("0000");
        }
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
        // pause for 1 sec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Wallboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Write STRING file Command Code - "G" (47H)
     *
     * @param fileLabel
     * @param str
     */
    public void WriteStringFile(String fileLabel, String str) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_STRING);
        cmd.append(fileLabel);
        cmd.append(str);  // actual STRING file data
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     * Write a Priority TEXT File
     *
     * @param msg
     */
    public void WritePriorityTextFile(String msg) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_TEXT);
        cmd.append("0");  // priority TEXT File Label
        cmd.append(ESC);
        cmd.append(MIDDLE_LINE);
        cmd.append(ROTATE);
        cmd.append(RED);
        cmd.append(msg);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     * Disable the Priority TEXT File
     */
    public void DisablePriorityTextFile() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_TEXT);
        cmd.append("0");  // priority TEXT File Label
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    public void WriteTextFileLabel(String label, String msg, String[] labels) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_TEXT);
        cmd.append(label);  // file label
        cmd.append(ESC);
        cmd.append(TOP_LINE);
        cmd.append(HOLD);
        cmd.append(GREEN);
        cmd.append(msg);  // the actual text to be displayed on a sign
        for (int i = 0; i < labels.length; i++) {
            cmd.append(ESC);
            cmd.append(BOTTOM_LINE);
            cmd.append(ROTATE);
            cmd.append(AMBER);
            cmd.append("\u0010");  // call STRING file
            cmd.append(labels[i]);
        }
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     *
     */
    public void WriteTextFileLabel9() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_TEXT);
        cmd.append("9");  // file label
        cmd.append(ESC);
        cmd.append(TOP_LINE);
        cmd.append(HOLD);
        cmd.append(GREEN);
        cmd.append("\u0010");  // call STRING file
        cmd.append(yahooLabels[0]);
        for (int i = 1; i < yahooLabels.length; i++) {
            cmd.append(ESC);
            cmd.append(BOTTOM_LINE);
            cmd.append(ROTATE);
            cmd.append(AMBER);
            cmd.append("\u0010");  // call STRING file
            cmd.append(yahooLabels[i]);
        }
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    public void WriteTextFileLabelx() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_TEXT);
        cmd.append("x");  // file label
        cmd.append(ESC);
        cmd.append(TOP_LINE);
        cmd.append(SNOW);
        cmd.append("This is a test! ");
        cmd.append(ESC);
        cmd.append(BOTTOM_LINE);
        cmd.append(HOLD);
        cmd.append("\u0014");  // call DOTS file
        cmd.append("v");
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    public void WriteSmDOTSUpArrow(String label, String color) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SMALL_DOTS);
        cmd.append(label);  // file label
        cmd.append("07");   // height in Hex
        cmd.append("05");   // width in Hex
        cmd.append("00000");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("0");
        cmd.append(color);
        cmd.append(color);
        cmd.append(color);
        cmd.append("0");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("00000");
        cmd.append(CR);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    public void WriteSmDOTSDownArrow(String label, String color) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SMALL_DOTS);
        cmd.append(label);  // file label
        cmd.append("07");   // height in Hex
        cmd.append("05");   // width in Hex
        cmd.append("00000");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("0");
        cmd.append(color);
        cmd.append(color);
        cmd.append(color);
        cmd.append("0");
        cmd.append(CR);
        cmd.append("00");
        cmd.append(color);
        cmd.append("00");
        cmd.append(CR);
        cmd.append("00000");
        cmd.append(CR);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     * Set the start and stop days in the Run Day table
     *
     * @param fileLabel
     * @param startDay Represents run start day
     * @param stopDay Represents run stop day
     */
    public void SetRunDay(String fileLabel, String startDay, String stopDay) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append("2");
        cmd.append(fileLabel);
        cmd.append(startDay);
        cmd.append(stopDay);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     * Read the start and stop times
     */
    public void ReadRunTimeTable() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(READ_SPECIAL);
        cmd.append(")");
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     * Set the start and stop times in the Run Time table
     *
     * @param fileLabel
     * @param startTime
     * @param stopTime
     */
    public void SetRunTime(String fileLabel, String startTime, String stopTime) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append(")");
        cmd.append(fileLabel);  // one ASCII character that represents a TEXT File Label
        cmd.append(startTime);
        cmd.append(stopTime);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     *
     */
    public void ReadRunSequence() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(READ_SPECIAL);
        cmd.append(".");
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
    }

    /**
     * Specify the Run Sequence
     *
     * @param seq
     */
    public synchronized static void SetRunSequence(String seq) {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append(".");
        cmd.append("T");  // run according to their associated times (default)
        cmd.append(LOCKED);
        cmd.append(seq);  // ASCII character(s) that represent TEXT File Label(s)
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
        //setSequenceOrder(seq, remove);
    }

    /**
     * Set time of day - four ASCII digits used to set the time of day (24-hour
     * format) clock
     */
    public void SetTimeOfDay() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("HHmm");
        String now = formatter.format(cal.getTime());
        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append(" ");
        cmd.append(now);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
        // pause for 1 sec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Wallboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set day of week - one ASCII digit that represents the day of the week
     */
    public void SetDayOfWeek() {

        Calendar cal = Calendar.getInstance();
        Integer day = cal.get(Calendar.DAY_OF_WEEK);
        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append("&");
        cmd.append(day);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
        // pause for 1 sec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Wallboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set date - six ASCII characters that are used to set the date
     */
    public void SetDate() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddyy");
        String now = formatter.format(cal.getTime());
        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append(";");
        cmd.append(now);
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
        // pause for 1 sec
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Wallboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Clear memory
     */
    public void ClearMemory() {

        StringBuilder cmd = new StringBuilder();
        cmd.append(transBegin);
        cmd.append(WRITE_SPECIAL);
        cmd.append("$");  // to clear memory just use "E$"
        cmd.append(EOT);
        System.out.println(cmd);
        sendString(cmd.toString());
        // pause for 5 secs
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(Wallboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
