/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.system.exception.ExceptionID;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;

@ExceptionID("702")
public enum DayPart {
    EARLY_MORNING(4, 6), MORNING(7, 11), AFTERNOON(12, 5), EVENING(6, 9), NIGHT(10, 12), MIDNIGHT(1, 3);
    private final double hourStart;
    private final double hourFinish;

    DayPart(double hourStart, double hourFinish) {
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public static DayPart getPart() {
        double hour = Instant.now().atZone(ZoneId.of("America/New_York")).getHour();
        for (DayPart part : DayPart.values()) {
            if (hour >= part.getHourStart() && hour <= part.getHourFinish()) return part;
        }
        return null;
    }

    public double getHourStart() {
        return hourStart;
    }

    public double getHourFinish() {
        return hourFinish;
    }

    public Color getColor() {
        switch (this) {
            case EARLY_MORNING:
                return Color.CYAN;
            case MORNING:
                return Color.pink;
            case AFTERNOON:
                return Color.blue;
            case EVENING:
                return Color.ORANGE;
            case NIGHT:
                return Color.magenta;
            case MIDNIGHT:
                return Color.darkGray;
            default:
                return Color.BLACK;
        }
    }
}
