/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames;

import java.text.DecimalFormat;

public class StringUtil {
    public static String formatScore(double score) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return String.format("%s fp", formatter.format(score));
    }
}
