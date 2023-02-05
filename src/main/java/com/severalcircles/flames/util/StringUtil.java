/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.util;

import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("deprecation")
public class StringUtil {
    /**
     */
    public static int highestScore;
    public static String prettifyDate(Instant instant) {
        String prettyDate = "";
        switch (Date.from(instant).getMonth()) {
            default:
            case Calendar.JANUARY:
                prettyDate += "January ";
                break;
            case Calendar.FEBRUARY:
                prettyDate += "February ";
                break;
            case Calendar.MARCH:
                prettyDate += "March ";
                break;
            case Calendar.APRIL:
                prettyDate += "April ";
                break;
            case Calendar.MAY:
                prettyDate += "May ";
                break;
            case Calendar.JUNE:
                prettyDate += "June ";
                break;
            case Calendar.JULY:
                prettyDate += "July ";
                break;
            case Calendar.AUGUST:
                prettyDate += "August ";
                break;
            case Calendar.SEPTEMBER:
                prettyDate += "September ";
                break;
            case Calendar.OCTOBER:
                prettyDate += "October ";
                break;
            case Calendar.NOVEMBER:
                prettyDate += "November ";
                break;
            case Calendar.DECEMBER:
                prettyDate += "December ";
                break;
        }
        prettyDate += Date.from(instant).getDate() + ", " + (Date.from(instant).getYear()+1900);
        return prettyDate;
    }
    public static String formatScore(int score) {
        if (score > 1000 && score < 1000000) {
            return score / 1000 + "K FP";
        }
        if (score >= 1000000) return score / 1000000 + "M FP";
        DecimalFormat df = new DecimalFormat("###,###,###.###");
        return df.format(score).replace(".", " ") + " FP";
    }
    public static String getFormattedName(User user) {
        FlamesUser flamesUser;
        try {
            flamesUser = FlamesDataManager.readUser(user);
        } catch (IOException | ConsentException | DataVersionException e) {
            e.printStackTrace();
            return user.getName();
        }
        String name = user.getName();
        if (flamesUser.getScore() >= highestScore) {
            highestScore = flamesUser.getScore();
            name += " ?";
        }
        return name;
    }
}

