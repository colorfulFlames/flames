/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * EmotionLevel enum for storing emotion levels.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
public enum EmotionLevel {
    VERY_LOW(-9999, -5), LOW(-5, -1), NEUTRAL(-1, 1), HIGH(1, 5), VERY_HIGH(5, 9999);
    private final double lowBound;
    private final double highBound;

    EmotionLevel(double lowBound, double highBound) {
        this.lowBound = lowBound;
        this.highBound = highBound;
    }
    public String getName(Locale locale) {
        ResourceBundle rsc = ResourceBundle.getBundle("strings/Emotion", locale);
        return rsc.getString(this.name());
    }
    public static EmotionLevel getLevel(double emotion) {
        for (EmotionLevel level : EmotionLevel.values()) {
            if (level.lowBound <= emotion && level.highBound >= emotion) return level;
        }
        return NEUTRAL;
    }
}
