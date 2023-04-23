/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data.user;

import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Enum for storing the rank of a FlamesUser.
 * @author Several Circles
 * @version 8
 * @since Flames 8
 * @see FlamesRank
 */
public enum Rank {
    UNRANKED(new FlamesRank(0, 0f)), SPARKS(new FlamesRank(1, 0.5f)), SPARKS_PLUS(new FlamesRank(1, 0.75f)), LIGHTS(new FlamesRank(2, 1f)), LIGHTS_PLUS(new FlamesRank(2, 5f)), COLORS(new FlamesRank(3, 10f)), COLORS_PLUS(new FlamesRank(4, 20f)), SKYBREAK(new FlamesRank(5, 40f)), ICEFALL(new FlamesRank(6, 75f)), SUMMIT(new FlamesRank(6, 100f));
    FlamesRank rank;
    ResourceBundle rsc;
    int level;

    Rank(FlamesRank rank) {
        this.rank = rank;
        rsc = ResourceBundle.getBundle("strings/Rank");

    }

    public FlamesRank getRank() {
        return rank;
    }

    public ResourceBundle getRsc() {
        return rsc;
    }
    public String getName() {
        return rsc.getString(this.name());
    }
    public Rank getNext() {
        switch (this) {
            case UNRANKED -> {
                return SPARKS;
            }
            case SPARKS -> {
                return SPARKS_PLUS;
            }
            case SPARKS_PLUS -> {
                return LIGHTS;
            }
            case LIGHTS -> {
                return LIGHTS_PLUS;
            }
            case LIGHTS_PLUS -> {
                return COLORS;
            }
            case COLORS -> {
                return COLORS_PLUS;
            }
            case COLORS_PLUS -> {
                return SKYBREAK;
            }
            case SKYBREAK -> {
                return ICEFALL;
            }
            case ICEFALL, SUMMIT -> {
                return SUMMIT;
            }
        }
        return UNRANKED;
    }
}
