/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import com.severalcircles.flames.external.Analysis;

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
        Today.quote = new String[]{"We're still waiting for somebody to say something epic.", "Flames", "0"};
        Today.quoteChanges = 0;
        Today.quoteLocked = false;
        Today.thanks = new LinkedList<>();
        Date now = new Date();
        thanksgiving.set(2021, Calendar.NOVEMBER, 25);
        System.out.println(Calendar.getInstance().toInstant().truncatedTo(ChronoUnit.DAYS).compareTo(thanksgiving.toInstant().truncatedTo(ChronoUnit.DAYS)));
        if (Calendar.getInstance().toInstant().truncatedTo(ChronoUnit.DAYS).compareTo(thanksgiving.toInstant().truncatedTo(ChronoUnit.DAYS)) == 0) Today.isThanksgiving = true;
        Today.thanksgivingThanks = new HashMap<>();
    }
}