/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.reports;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.java.FlamesDataException;
import com.severalcircles.flames.system.FlamesReportManager;

import java.util.Map;
import java.util.TimerTask;

public abstract class FlamesReport extends TimerTask {
    public void run() {
        Flames.getFlogger().info("The " + this.getClass().getSimpleName() + " report follows.");
        getReport().forEach((key, value) -> Flames.getFlogger().info(key + ": " + value));
        try {
            FlamesReportManager.saveReport(this);
        } catch (FlamesDataException e) {

        }
    }
    public abstract Map<String, String> getReport();
}
