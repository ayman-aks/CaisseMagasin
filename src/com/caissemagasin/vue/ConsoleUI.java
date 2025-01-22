package com.caissemagasin.vue;

/**
 * An abstract class that defines constants for console text formatting.
 * These constants are used to apply different colors and styles to console output.
 */
public abstract class ConsoleUI {
    
    /**
     * ANSI escape code for resetting text formatting to default.
     */
    public static final String RESET = "\u001B[0m";
    
    /**
     * ANSI escape code for red text color.
     */
    public static final String RED = "\u001B[31m";
    
    /**
     * ANSI escape code for green text color.
     */
    public static final String GREEN = "\u001B[32m";
    
    /**
     * ANSI escape code for yellow text color.
     */
    public static final String YELLOW = "\u001B[33m";
    
    /**
     * ANSI escape code for blue text color.
     */
    public static final String BLUE = "\u001B[34m";
    
    /**
     * ANSI escape code for cyan text color.
     */
    public static final String CYAN = "\u001B[36m";
    
    /**
     * ANSI escape code for bold text style.
     */
    public static final String BOLD = "\u001B[1m";
}
