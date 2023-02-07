/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.util;

public enum Rank {
    UNRANKED ("Unranked"), APPROACHING_BRONZE ("Approaching Bronze"), BRONZE ("Bronze"), SILVER ("Silver"), SHINING_SILVER ("Shining Silver"), GOLD ("Gold"), BEYOND_GOLD ("Beyond Gold"), PLATINUM ("Platinum"), SPARKLING_PLATINUM ("Sparkling Platinum"), PLATINUM_SUMMIT ("Platinum Summit");
    private final String name;
    Rank(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
