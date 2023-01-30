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
import java.util.Date;

@SuppressWarnings("deprecation")
public class StringUtil {
    /**
     * @return String with Instant formatted as a nice, human-readable date string.
     */
    public static int highestScore;
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
                break;
            case 11:
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

