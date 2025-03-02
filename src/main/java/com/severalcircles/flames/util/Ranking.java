/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Determines thresholds for each rank and allows getting the rank for a given score.
 */
public class Ranking {
    public static final int[] thresholds = {5000, 11750, 21875, 35544, 56048, 103728, 156247, 387526, 1000000};
    public static int baseValue = 0;

    public static ResourceBundle getResources(Locale locale) {
        return ResourceBundle.getBundle("features/Ranking", locale);
    }

    /**
     * Gets the Rank for a given score value
     * @param score Input score value
     * @return Corresponding rank
     */
    public static Rank getRank(int score) {
        // what a glorious function
        if (thresholds[0] < 0) return Rank.UNRANKED;
        if (score < thresholds[0]) return Rank.UNRANKED;
        else if (score < thresholds[1]) return Rank.APPROACHING_BRONZE;
        else if (score < thresholds[2]) return Rank.BRONZE;
        else if (score < thresholds[3]) return Rank.SILVER;
        else if (score < thresholds[4]) return Rank.SHINING_SILVER;
        else if (score < thresholds[5]) return Rank.GOLD;
        else if (score < thresholds[6]) return Rank.BEYOND_GOLD;
        else if (score < thresholds[7]) return Rank.PLATINUM;
        else if (score < thresholds[8]) return Rank.SPARKLING_PLATINUM;
        else if (score >= thresholds[8]) return Rank.PLATINUM_SUMMIT;
        else return Rank.UNRANKED;
    }

    /**
     * Gets the integer value for a Rank object
     * @param rank Input Rank Value
     * @return Corresponding Integer
     */
    protected static int getRank(Rank rank) {
        // yet another glorious function
        switch(rank) {
            case APPROACHING_BRONZE: return 0;
            case BRONZE: return 1;
            case SILVER: return 2;
            case SHINING_SILVER: return 3;
            case GOLD: return 4;
            case BEYOND_GOLD: return 5;
            case PLATINUM: return 6;
            case SPARKLING_PLATINUM: return 7;
            case PLATINUM_SUMMIT: return 8;
            case UNRANKED:
            default: return -1;
        }
    }
    public static int toNext(int score) {
        // the most beautiful function yet
        try { return thresholds[getRank(getRank(score))+1] - score; }
        catch (ArrayIndexOutOfBoundsException e) { return 0; }
    }

}

