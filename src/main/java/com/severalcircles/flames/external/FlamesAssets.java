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

    public static String getRankIcon(Rank rank) {
        switch (rank) {
            case APPROACHING_BRONZE:
                return rankUrl + "sparks.png";
//                break;
            case BRONZE:
                return rankUrl + "sparks+.png";
//                break;
            case SILVER:
                return rankUrl + "Lights.png";
//                break;
            case SHINING_SILVER:
                return rankUrl + "Lights+.png";
//                break;
            case GOLD:
                return rankUrl + "Colors.png";
//                break;
            case BEYOND_GOLD:
                return rankUrl + "Colors+.png";
//                break;
            case PLATINUM:
                return rankUrl + "Skybreak.png";
//                break;
            case SPARKLING_PLATINUM:
                return rankUrl + "Icefall.png";
//                break;
            case PLATINUM_SUMMIT:
                return rankUrl + "Summit.png";
//                break;
            case UNRANKED:
            default:
                return rankUrl + "Unranked.png";
//                break;
        }
    }
    public static String getVersionIcon() {
        if (Flames.version.startsWith("6.")) return "https://www.severalcircles.com/flames/assets/versions/6.png";
        if (Flames.version.startsWith("7.")) return "https://www.severalcircles.com/flames/assets/versions/7.0-snapshot.png";
        return "https://www.severalcircles.com/flames/assets/versions/" + Flames.version + ".png";
    }

}
