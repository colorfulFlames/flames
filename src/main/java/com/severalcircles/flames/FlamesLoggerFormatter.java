/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames;

import java.awt.*;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class FlamesLoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        StringBuilder buffer = new StringBuilder();
        switch (record.getLevel().getName()) {
            case "INFO":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.BLUE).append("[INFO] ").append(Color.WHITE).append("n");
                break;
            case "WARNING":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.YELLOW).append("[WARNING] ").append(Color.WHITE).append("n");
                break;
            case "SEVERE":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.RED).append("[SEVERE] ").append(Color.WHITE).append("n");
                break;
            case "CONFIG":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.GREEN).append("[CONFIG] ").append(Color.WHITE).append("n");
                break;
            case "FINE":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.CYAN).append("[FINE] ").append(Color.WHITE).append("n");
                break;
            case "FINER":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.MAGENTA).append("[FINER] ").append(Color.WHITE).append("n");
                break;
            case "FINEST":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.ORANGE).append("[FINEST] ").append(Color.WHITE).append("n");
                break;
            case "OFF":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.GRAY).append("[OFF] ").append(Color.WHITE).append("n");
                break;
            case "ALL":
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.WHITE).append("[ALL] ").append(Color.WHITE).append("n");
                break;
            default:
                buffer.append(record.getSourceClassName()).append(" : ").append(Color.LIGHT_GRAY).append("[UNKNOWN] ").append(Color.WHITE).append("n");
                break;
        }
        return buffer.toString();
    }
}
