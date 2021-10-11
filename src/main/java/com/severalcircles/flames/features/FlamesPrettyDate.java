package com.severalcircles.flames.features;

import java.time.Instant;
import java.util.Date;

@SuppressWarnings("deprecation")
public class FlamesPrettyDate {
    public static String prettifyDate(Instant instant) {
        String prettyDate = "";
        switch (Date.from(instant).getMonth()) {
            default:
            case 1:
                prettyDate += "January ";
                break;
            case 2:
                prettyDate += "February ";
                break;
            case 3:
                prettyDate += "March ";
                break;
            case 4:
                prettyDate += "April ";
                break;
            case 5:
                prettyDate += "May ";
                break;
            case 6:
                prettyDate += "June ";
                break;
            case 7:
                prettyDate += "July ";
                break;
            case 8:
                prettyDate += "August ";
                break;
            case 9:
                prettyDate += "September ";
                break;
            case 10:
                prettyDate += "October ";
                break;
            case 11:
                prettyDate += "November ";
            case 12:
                prettyDate += "December ";
        }
        prettyDate += Date.from(instant).getDate() + ", " + (Date.from(instant).getYear()+1900);
        return prettyDate;
    }
}
