/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.exception;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.frontend.FlamesExceptionEmbed;
import com.severalcircles.flames.system.exception.flames.FlamesException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FlamesExceptionHandler {
    public static final Map<Class<FlamesException>, Integer> exceptionCounts = new HashMap<>();
    FlamesException e;
    public FlamesExceptionHandler(FlamesException e) {
        this.e = e;
        countException();
        Flames.getFlogger().severe( "[" + MessageCodes.generateCodeError(e) + "] " + e.getMessage());
    }
    public FlamesEmbed getEmbed() {
        return new FlamesExceptionEmbed(Locale.ROOT, e);
    }
    private void countException() {
        if(exceptionCounts.containsKey(e.getClass())) {
            exceptionCounts.put((Class<FlamesException>) e.getClass(), exceptionCounts.get(e.getClass()) + 1);
        } else {
            exceptionCounts.put((Class<FlamesException>) e.getClass(), 1);
        }
    }
}
