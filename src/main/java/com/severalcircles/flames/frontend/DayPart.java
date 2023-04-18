/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.ExceptionID;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneId;

@ExceptionID("702")
public   enum DayPart {
    EARLY_MORNING(4, 6), MORNING(7, 11), AFTERNOON(12, 18), EVENING(19, 21), NIGHT(22, 23), MIDNIGHT(0, 3);
    private final double hourStart;
    private final double hourFinish;

    DayPart(double hourStart, double hourFinish) {
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public static DayPart getPart() {
        double hour = Instant.now().atZone(ZoneId.of("America/New_York")).getHour();
        Flames.getFlogger().finest(hour + " o'clock");
        for (DayPart part : DayPart.values()) {
            Flames.getFlogger().finest("Checking " + part + " (" + part.getHourStart() + " - " + part.getHourFinish() + ")");
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
                return Color.decode("#63AA63");
            case MORNING:
                return Color.decode("#A73A51");
            case AFTERNOON:
                return Color.decode("#37AECD");
            case EVENING:
                return Color.decode("#ED6A3D");
            case NIGHT:
                return Color.decode("#581B4E");
            case MIDNIGHT:
                return Color.decode("#1A1F3A");
            default:
                return Color.BLACK;
        }
    }
}
