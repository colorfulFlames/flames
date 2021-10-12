package com.severalcircles.flames.features.external.severalcircles;

import com.severalcircles.flames.features.rank.Rank;

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

}
