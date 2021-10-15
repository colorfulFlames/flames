package com.severalcircles.flames.features.rank;

import com.severalcircles.flames.data.global.GlobalData;

import java.io.IOException;

/**
 * Determines thresholds for each rank and allows getting the rank for a given score.
 */
public class Ranking {
    public static final int[] thresholds = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    /**
     * Re-calculates the rank thresholds based on the current average score
     */
    public static void updateThresholds() throws IOException {
        GlobalData.read();
        // Bronze is the average score times 10, minus the number of participants.
        thresholds[1] = Math.abs(GlobalData.averageScore * 10) - GlobalData.averageScore;
        // Approaching Bronze is half of Bronze
        thresholds[0] = Math.abs(thresholds[1] / 2);
        // Silver is 1.5 times Bronze
        thresholds[2] = Math.abs(thresholds[1]);
        // Shining Silver is double Bronze
        thresholds[3] = Math.abs(2 * thresholds[1]);
        // Gold is triple Bronze
        thresholds[4] = Math.abs(3 * thresholds[1]);
        System.out.println(thresholds[4]);
        thresholds[5] = Math.abs(thresholds[4]);
        System.out.println(thresholds[5]);
        // Platinum is double Gold
        thresholds[6] = Math.abs(2 * thresholds[4]);
        System.out.println(thresholds[6]);
        // Sparkling Platinum is 1.5 times Platinum
        thresholds[7] = Math.abs(thresholds[6]);
        System.out.println(thresholds[7]);
        // Platinum Summit is double Platinum
        thresholds[8] = Math.abs(2 * thresholds[6]);
        System.out.println(thresholds[8]);
    }

    /**
     * Gets the Rank for a given score value
     * @param score Input score value
     * @return Corresponding rank
     */
    public static Rank getRank(int score) {
        // what a glorious function
        if (thresholds[0] <= 0) return Rank.UNRANKED;
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

