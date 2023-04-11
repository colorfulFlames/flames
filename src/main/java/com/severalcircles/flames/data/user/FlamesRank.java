/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesData;

public record FlamesRank(
            int level,
            float multiplier
    ) {
        public static double getThreshold(Rank rank) {
            return rank.getRank().multiplier() * FlamesData.getAverageScore();
        }
        public static boolean checkRankUp(FlamesUser user) {
            return user.getScore() > getThreshold(user.getRank().getNext());
        }
        public int level() {
            return level;
        }
    }

