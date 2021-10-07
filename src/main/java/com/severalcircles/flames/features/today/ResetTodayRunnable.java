/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.today;

import com.severalcircles.flames.features.Analysis;

import java.util.HashMap;

public class ResetTodayRunnable implements Runnable{

    @Override
    public void run() {
        Today.emotion = 0;
//        Today.topic = "";
        Today.highScore = 0;
        Analysis.entityCache = new HashMap<>();
    }
}
