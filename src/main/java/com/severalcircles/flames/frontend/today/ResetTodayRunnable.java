/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import com.severalcircles.flames.external.analysis.Analysis;
//import com.severalcircles.flames.frontend.today.qotd.QOTD;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class ResetTodayRunnable implements Runnable {
    static final Calendar thanksgiving = Calendar.getInstance();

    @Override
    public void run() {
        Today.emotion = 0;
//        Today.topic = "";
        Today.highScore = 0;
        Analysis.entityCache = new HashMap<>();
        Today.quote = Today.defaultQuote;
        Today.thanks = new LinkedList<>();
        Today.highUser = "Nobody yet!";
        Date now = new Date();
//        thanksgiving.set(2021, Calendar.NOVEMBER, 25);
//        Today.thanksgivingThanks = new HashMap<>();
//        QOTD.nextQuestion();
    }
}