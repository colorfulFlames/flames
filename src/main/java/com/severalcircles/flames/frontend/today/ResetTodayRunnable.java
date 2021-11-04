/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import com.severalcircles.flames.external.Analysis;

import java.util.HashMap;
import java.util.LinkedList;

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
        Today.thanks = new LinkedList<>();
    }
}
