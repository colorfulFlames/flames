/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class StringUtil {
    public static String formatScore(double score) {
        score = Math.round(score);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return String.format("%s FP", formatter.format(score));
    }
    public static String prettyDate(Instant instant, Locale locale) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY", locale);
        return formatter.format(new Date(instant.toEpochMilli()));
    }
}
