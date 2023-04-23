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
/**
 * This class contains various string-related utilities.
 * @author Several Circles
 * @version 8
 * @since Flames 4
 */
public class StringUtil {
    /**
     * Formats a score into the proper format.
     * @param score
     * @return A formatted score String
     */
    public static String formatScore(double score) {
        score = Math.round(score);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return String.format("%s FP", formatter.format(score));
    }
    /**
     * Formats a date into the proper format.
     * @param instant The date to format
     * @param locale The locale to use
     * @return A formatted date String
     */
    public static String prettyDate(Instant instant, Locale locale) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY", locale);
        return formatter.format(new Date(instant.toEpochMilli()));
    }
}
