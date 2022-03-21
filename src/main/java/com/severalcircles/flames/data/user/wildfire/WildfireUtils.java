/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.data.user.wildfire;

import java.util.Locale;

public class WildfireUtils {
    static float checkSimilarity(String s1, String s2) {
        String[] s1a = s1.split("");
        String[] s2a = s2.split("");
        float score = 0;
        for (String s : s1a) {
            for (String s3 : s2a) {
                if (s.toUpperCase(Locale.ROOT).equals(s3.toUpperCase(Locale.ROOT))) {
                    score++;
                }
            }
        }
        score /= s1a.length;
        return score;
    }
}
