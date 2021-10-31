/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.util;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Date;

@SuppressWarnings("deprecation")
public class StringUtil {
    /**
     * @return String with Instant formatted as a nice, human-readable date string.
     */
    public static String prettifyDate(Instant instant) {
        String prettyDate = "";
        switch (Date.from(instant).getMonth()) {
            default:
            case 0:
                prettyDate += "January ";
                break;
            case 1:
                prettyDate += "February ";
                break;
            case 2:
                prettyDate += "March ";
                break;
            case 3:
                prettyDate += "April ";
                break;
            case 4:
                prettyDate += "May ";
                break;
            case 5:
                prettyDate += "June ";
                break;
            case 6:
                prettyDate += "July ";
                break;
            case 7:
                prettyDate += "August ";
                break;
            case 8:
                prettyDate += "September ";
                break;
            case 9:
                prettyDate += "October ";
                break;
            case 10:
                prettyDate += "November ";
            case 11:
                prettyDate += "December ";
        }
        prettyDate += Date.from(instant).getDate() + ", " + (Date.from(instant).getYear()+1900);
        return prettyDate;
    }
    public static String formatScore(int score) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(score).replace(".", " ") + " FP";
    }
}
