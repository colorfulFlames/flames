/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

public enum DayPart {
    EARLY_MORNING(4, 6), MORNING(7, 11), AFTERNOON(12, 5), EVENING(6, 9), NIGHT(10, 12), MIDNIGHT(1, 3);
    private final double hourStart;
    private final double hourFinish;

    DayPart(double hourStart, double hourFinish) {
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public double getHourStart() {
        return hourStart;
    }

    public double getHourFinish() {
        return hourFinish;
    }
}
