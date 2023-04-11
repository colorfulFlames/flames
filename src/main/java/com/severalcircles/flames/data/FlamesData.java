/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data;

public class FlamesData {
private static double averageScore = 0;
    public static double getAverageScore() {
        return averageScore;
    }
    public static void setAverageScore(double averageScore) {
        FlamesData.averageScore = averageScore;
    }
}
