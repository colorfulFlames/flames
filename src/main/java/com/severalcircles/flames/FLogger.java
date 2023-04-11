/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames;

import java.util.logging.Level;

/**
 * A logger that prints to the console with colors and indentation. Not to be confused with flogging, a form of corporal punishment.
 * We use this instead of the default logger because it's easier to use and looks better. Change my fucking mind.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
@SuppressWarnings("SameReturnValue")
public class FLogger {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    private Level level = Level.ALL;
    private static String lastClass = "";

    /**
     * Logs a message at the specified level.
     * @param level The logging Level of the message.
     * @param message Unbelievably, the message.
     * @since Flames 8
     */
    public void log(Level level, String message) {
        String finalMessage = "";
        if (!(level.intValue() >= this.level.intValue())) {
            return;
        }
        String icon = "";
        if (level == Level.FINE) {
            finalMessage += ANSI_PURPLE;
            icon = "♪";
        }
        if (level == Level.FINEST) {
            finalMessage += ANSI_CYAN;
            icon = "♫";
        }
        if (level == Level.INFO){
            finalMessage += ANSI_BLUE;
            icon = "♠";
        }
        if (level == Level.CONFIG) {
            finalMessage += ANSI_GREEN;
            icon = "♣";
        }
        if (level == Level.WARNING) {
            finalMessage += ANSI_YELLOW;
            icon = "♦";
        }
        if (level.intValue() > 901) {
            finalMessage += ANSI_RED;
            icon = "♥";
        }
        finalMessage += "[" + icon + "] ";
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (ste.getClassName().contains("FLogger")) {
            } else {
                if (ste.getClassName().replaceAll(".*\\.", "").equals(lastClass)) {
                    StringBuilder bars = new StringBuilder("├");
                    for (int j = i; j < stElements.length; j++) {
                        bars.append("─");
                    }
                    bars.append("►");
                    finalMessage = bars + finalMessage;
                } else {
                    finalMessage = "\n" + finalMessage;
                    finalMessage += "[" + ste.getClassName().replaceAll(".*\\.", "") + "] ";
                }
            lastClass = ste.getClassName().replaceAll(".*\\.", "");
            break;
            }
        }
        finalMessage += message;
        System.out.println(finalMessage + ANSI_RESET);
    }
    /**
     * Resets the last class. This is used to prevent the logger from grouping messages from the same class relating to different processes.
     * Optionally, you can call it for fun whenever you want.
     * This says a lot about Flames' code quality.
     * @since Flames 8
     */
    public void resetLastClass() {
        lastClass = "";
    }

    /**
     * Logs an informational message.
     * @param message The message.
     */
    public void info(String message) {
        log(Level.INFO, message);
    }
    /**
     * Logs a warning message.
     * @param message The message.
     */
    public void warning(String message) {
        log(Level.WARNING, message);
    }
    /**
     * Logs a severe message. Or, as most people call it, an error.
     * @param message The message.
     */
    public void severe(String message) {
        log(Level.SEVERE, message);
    }
    /**
     * Sets the global logging level.
     * @param level The level.
     * @since Flames 8
     */
    public void setLevel(Level level) {
        this.level = level;
    }
    /**
     * Gets the global logging level.
     * @return The, bare with me, logging level.
     * @since Flames 8
     */
    public Level getLevel() {
        return level;
    }
    /**
     * Logs a message at a level that is just fine.
     * @param message The message.
     * @since Flames 8
     */
    public void fine(String message) {
        log(Level.FINE, message);
    }
    /**
     * Logs a message at it's finest. level.
     * @param message The message.
     * @since Flames 8
     */
    public void finest(String message) {
        log(Level.FINEST, message);
    }
    /**
     * Logs a message at the config level. Usually used for messages that are related to-you get the idea.
     * @param message The message.
     * @since Flames 8
     */
    public void config(String message) {
        log(Level.CONFIG, message);
    }

    /**
     * A totally fucking useless method. Yippee.
     * @return Something, idk
     * @since Flames 8
     */
    public static String getCallerClassName() {

        return null;
    }
}
