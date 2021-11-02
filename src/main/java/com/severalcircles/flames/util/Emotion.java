/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class Emotion {
    public static ResourceBundle getResources(Locale locale) {
        return ResourceBundle.getBundle("features/Emotion", locale);
    }
    public static String getEmotionString(float emotion, Locale locale) {
        if (emotion < 10 && emotion >= 0) return getResources(locale).getString("mid");
        if (emotion >= 10 && emotion < 50) return getResources(locale).getString("high");
        if (emotion >= 50) return getResources(locale).getString("veryhigh");
        if (emotion < 0 && emotion >= -10) return getResources(locale).getString("low");
        else return getResources(locale).getString("verylow");
    }
}
