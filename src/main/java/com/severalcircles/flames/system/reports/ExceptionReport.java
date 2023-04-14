/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.reports;

import com.severalcircles.flames.system.exception.FlamesExceptionHandler;

import java.util.HashMap;
import java.util.Map;
@RecurringReport(name = "Exception Report", interval = 60 * 60 * 1000)
public class ExceptionReport extends FlamesReport {

    @Override
    public Map<String, String> getReport() {
        Map<String, String> report = new HashMap<>();
        FlamesExceptionHandler.exceptionCounts.forEach((key, value) -> report.put(key.getSimpleName(), value.toString()));
        return report;
    }
}
