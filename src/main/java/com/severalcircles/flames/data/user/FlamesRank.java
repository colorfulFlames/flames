/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.system.manager.secondary.FlamesDataManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public record FlamesRank(
            int level,
            float multiplier
    ) {
        public static double getThreshold(Rank rank) {
            return rank.getRank().multiplier() * FlamesDataManager.getAverageScore();
        }
        public static boolean checkRankUp(FlamesUser user) {
            return user.getScore() > getThreshold(user.getRank().getNext());
        }
        public int level() {
            return level;
        }
        public static Rank getRank(double score) {
            Map<Rank, Double> thresholds = new HashMap<>();
            AtomicReference<Rank> rank = new AtomicReference<>();
            for (Rank rnk : Rank.values()) {
                thresholds.put(rnk, rnk.getRank().multiplier() * FlamesDataManager.getAverageScore());
            }
            thresholds.forEach((rnk, threshold) -> {
                if (score > threshold) {
                    rank.set(rnk);
                }
            });
            if (rank.get() == null) {
                rank.set(Rank.UNRANKED);
            }
            return rank.get();
        }
    }

