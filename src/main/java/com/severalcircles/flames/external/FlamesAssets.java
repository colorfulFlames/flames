/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.util.Rank;
//import old.com.severalcircles.flames.system.Flames;

/**
 * Contains permalinks to assets stored at severalcircles.com.
 */
public class FlamesAssets {
    private static final String rankUrl = "https://severalcircles.com/flames/assets/icons/rank/";
    public static final String globalDataUrl = "https://severalcircles.com/flames/assets/icons/global_data.png";
    public static final String welcomeBackUrl = "https://severalcircles.com/flames/assets/welcomeback1.png";
    public static String getRankIcon(Rank rank) {
        switch (rank) {
            case APPROACHING_BRONZE:
                return rankUrl + "approaching_bronze.png";
//                break;
            case BRONZE:
                return rankUrl + "bronze.png";
//                break;
            case SILVER:
                return rankUrl + "silver.png";
//                break;
            case SHINING_SILVER:
                return rankUrl + "shining_silver.png";
//                break;
            case GOLD:
                return rankUrl + "gold.png";
//                break;
            case BEYOND_GOLD:
                return rankUrl + "beyond_gold.png";
//                break;
            case PLATINUM:
                return rankUrl + "platinum.png";
//                break;
            case SPARKLING_PLATINUM:
                return rankUrl + "sparkling_platinum.png";
//                break;
            case PLATINUM_SUMMIT:
                return rankUrl + "platinum_summit.png";
//                break;
            case UNRANKED:
            default:
                return rankUrl + "unranked.png";
//                break;
        }
    }
    public static String getVersionIcon() {
        if (Flames.version.contains("4.4")) return "https://www.severalcircles.com/flames/assets/versions/4.4.png";
        if (Flames.version.contains("5.")) return "https://www.severalcircles.com/flames/assets/versions/5.png";
        return "https://www.severalcircles.com/flames/assets/versions/" + Flames.version + ".png";
    }

}
