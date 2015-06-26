package com.oracle.wallboard.core;

/**
 *
 * @author Dave Carpentier
 */
public class Constants {

    // command codes
    public static final String WRITE_TEXT = "A";  // Write TEXT file (p18)
    public static final String READ_TEXT = "B";  // Read TEXT file (p19)
    public static final String WRITE_SPECIAL = "E";  // Write SPECIAL FUNCTION commands (p21)
    public static final String READ_SPECIAL = "F";  // Read SPECIAL FUNCTION commands (p29)
    public static final String WRITE_STRING = "G";  // Write STRING (p37)
    public static final String READ_STRING = "H";  // Read STRING (p38)
    public static final String WRITE_SMALL_DOTS = "I";  // Write SMALL DOTS PICTURE file (p39)
    public static final String READ_SMALL_DOTS = "J";  // Read SMALL DOTS PICTURE file (p41)
    public static final String WRITE_LARGE_DOTS = "M";  // Write LARGE DOTS PICTURE file (p42)
    public static final String READ_LARGE_DOTS = "N";  // Read LARGE DOTS PICTURE file (p43)
    public static final String UNLOCKED = "U";
    public static final String LOCKED = "L";
    // constants used in transmission packets
    public static final String NUL = "00000";  // NULL
    public static final String SOH = "\u0001";  // Start of Header
    public static final String STX = "\u0002";  // Start of TeXt (precedes a command code)
    public static final String ETX = "\u0003";  // End of TeXt
    public static final String EOT = "\u0004";  // End Of Transmission
    public static final String BEL = "\u0007";  // Bell
    public static final String BS = "\u0008";  // Backspace
    public static final String HT = "\u0009";  // Horizontal tab
    public static final char LF = '\n';  // Line Feed
    public static final char NL = '\n';  // New Line
    public static final String VT = "\u000B";  // Vertical Tab
    public static final char FF = '\f';  // Form Feed
    public static final char NP = '\f';  // New Page
    public static final char CR = '\r';  // Carriage Return
    public static final String CAN = "\u0018";  // Cancel
    public static final String SUB = "\u001A";  // Substitute (select charset)
    public static final String ESC = "\u001B";  // Escape character
    public static final char NEWLINE = NL;
    public static final char NEWPAGE = NP;
    // character sets
    public static final String FIVE_HIGH_STD = "\u001A\u0031";
    public static final String SEVEN_HIGH_STD = "\u001A\u0033";
    public static final String SEVEN_HIGH_FANCY = "\u001A\u0035";
    public static final String TEN_HIGH_STD = "\u001A\u0036";
    public static final String FULL_HEIGHT_FANCY = "\u001A\u0038";
    public static final String FULL_HEIGHT_STD = "\u001A\u0039";
    // colors
    public static final String RED = "\u001C\u0031";
    public static final String GREEN = "\u001C\u0032";
    public static final String AMBER = "\u001C\u0033";
    public static final String DIM_RED = "\u001C\u0034";
    public static final String DIM_GREEN = "\u001C\u0035";
    public static final String BROWN = "\u001C\u0036";
    public static final String YELLOW = "\u001C\u0038";
    public static final String RAINBOW_1 = "\u001C\u0039";
    public static final String RAINBOW_2 = "\u001C\u0041";
    public static final String COLOR_MIX = "\u001C\u0042";
    // character attributes
    public static final String WIDE_ON = "\u001D\u0030\u0001";
    public static final String WIDE_OFF = "\u001D\u0030\u0000";
    public static final String DOUBLE_WIDE_ON = "\u001D\u0031\u0001";
    public static final String DOUBLE_WIDE_OFF = "\u001D\u0031\u0000";
    public static final String DOUBLE_HIGH_ON = "\u001D\u0032\u0001";
    public static final String DOUBLE_HIGH_OFF = "\u001D\u0032\u0000";
    public static final String TRUE_DESCENDERS_ON = "\u001D\u0033\u0001";
    public static final String TRUE_DESCENDERS_OFF = "\u001D\u0033\u0000";
    public static final String FIXED_WIDTH_ON = "\u001D\u0034\u0001";
    public static final String FIXED_WIDTH_OFF = "\u001D\u0034\u0000";
    public static final String FANCY_ON = "\u001D\u0035\u0001";
    public static final String FANCY_OFF = "\u001D\u0035\u0000";
    public static final String AUXILIARY_PORT_ON = "\u001D\u0036\u0001";
    public static final String AUXILIARY_PORT_OFF = "\u001D\u0036\u0000";
    public static final String FLASH_ON = "\u0007\u0030";
    public static final String FLASH_OFF = "\u0007\u0031";
    // character spacing
    public static final String PROPORTIONAL = "\u001E\u0030";
    public static final String FIXED_WIDTH = "\u001E\u0031";
    // counters
    public static final String COUNTER_1 = "\u0008\u007A";
    public static final String COUNTER_2 = "\u0008\u007B";
    public static final String COUNTER_3 = "\u0008\u007C";
    public static final String COUNTER_4 = "\u0008\u007D";
    public static final String COUNTER_5 = "\u0008\u007E";
    // extended characters
    public static final String UP_ARROW = "\u0008\u0064";
    public static final String DOWN_ARROW = "\u0008\u0065";
    // normal display modes
    public static final String ROTATE = "a";
    public static final String HOLD = "b";
    public static final String FLASH = "c";
    public static final String ROLL_UP = "e";
    public static final String ROLL_DOWN = "f";
    public static final String ROLL_LEFT = "g";
    public static final String ROLL_RIGHT = "h";
    public static final String WIPE_UP = "i";
    public static final String WIPE_DOWN = "j";
    public static final String WIPE_LEFT = "k";
    public static final String WIPE_RIGHT = "l";
    public static final String SCROLL = "m";
    public static final String AUTOMODE = "o";
    public static final String ROLL_IN = "p";
    public static final String ROLL_OUT = "q";
    public static final String WIPE_IN = "r";
    public static final String WIPE_OUT = "s";
    // special modes
    public static final String TWINKLE = "n0";
    public static final String SPARKLE = "n1";
    public static final String SNOW = "n2";
    public static final String INTERLOCK = "n3";
    public static final String SWITCH = "n4";
    public static final String SLIDE = "n5";
    public static final String SPRAY = "n6";
    public static final String STARBURST = "n7";
    public static final String WELCOME = "n8";
    public static final String SLOT_MACHINE = "n9";
    public static final String CYCLE_COLORS = "nC";
    // special graphics
    public static final String THANK_YOU = "nS";
    public static final String NO_SMOKING = "nU";
    public static final String DONT_DRINK_DRIVE = "nV";
    public static final String RUNNING_ANIMAL = "nW";
    public static final String FISH_ANIMATION = "nW";
    public static final String FIREWORKS = "nX";
    public static final String TURBO_CAR = "nY";
    public static final String BALLOON_ANIMATION = "nY";
    public static final String CHERRY_BOMB = "nZ";
    // display positions
    public static final String MIDDLE_LINE = "\u0020";  // text centered vertically
    public static final char TOP_LINE = 0x22;           // text begins on the top line of the sign
    public static final String BOTTOM_LINE = "\u0026";  // the starting position of the Bottom Line(s)
    public static final String FILL = "\u0030";         // fill all available lines, centering the lines vertically
    // display speeds
    public static final String SPEED_1 = "\u0015";
    public static final String SPEED_2 = "\u0016";
    public static final String SPEED_3 = "\u0017";
    public static final String SPEED_4 = "\u0018";
    public static final String SPEED_5 = "\u0019";
    // file labels
    public static final char[] LABELS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', '[', '/', ']', '¢', '_', '`', 'a', 'b', 'c', 'd',
        'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|'};
}
