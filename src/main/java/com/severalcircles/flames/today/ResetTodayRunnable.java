/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.today;

import com.severalcircles.flames.external.google.Analysis;

import java.util.HashMap;

public class ResetTodayRunnable implements Runnable{

    @Override
    public void run() {
        Today.emotion = 0;
//        Today.topic = "";
        Today.highScore = 0;
        Analysis.entityCache = new HashMap<>();
        Today.quote = new String[]{"We're still waiting for somebody to say something epic.", "Flames"};
        Today.quoteChanges = 0;
        Today.quoteLocked = false;
    }
}
