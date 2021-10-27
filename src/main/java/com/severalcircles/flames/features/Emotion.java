/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features;

import java.util.Locale;
import java.util.ResourceBundle;

public class Emotion {
    private static ResourceBundle resourceBundle;
    public static ResourceBundle getResources(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("features/Emotion", locale);
        return resourceBundle;
    }
    public static String getEmotionString(float emotion, Locale locale) {
        if (emotion < 10 && emotion >= 0) return getResources(locale).getString("mid");
        if (emotion >= 10 && emotion < 50) return getResources(locale).getString("high");
        if (emotion >= 50) return getResources(locale).getString("veryhigh");
        if (emotion < 0 && emotion >= -10) return getResources(locale).getString("low");
        else return getResources(locale).getString("verylow");
    }
}
