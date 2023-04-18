/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.java.FlamesDataException;
import com.severalcircles.flames.system.reports.FlamesReport;
import com.severalcircles.flames.system.reports.RecurringReport;
import com.severalcircles.flames.system.reports.StaticReport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
@ExceptionID("950")
public class FlamesReportManager extends FlamesManager {
    public static final File reportDir = new File(SystemDataManager.getFlamesDirectory().getAbsolutePath() + "/reports");
    static final List<FlamesReport> recurringReports = new LinkedList<>();
    public FlamesReportManager() {

    }

    @Override
    public void prepare() throws IOException {
        if (reportDir.mkdir()) {
            Flames.getFlogger().fine("Created report directory.");
        }
//        recurringReports.add(new ExceptionReport());
        recurringReports.forEach(report -> {
            if (report.getClass().isAnnotationPresent(RecurringReport.class)) {
                RecurringReport recurringReportAnnotation = report.getClass().getAnnotation(RecurringReport.class);
                if (recurringReportAnnotation.interval() > 0) {
                    Flames.getFlogger().fine("Scheduled report " + report.getClass().getSimpleName() + " to run every " + recurringReportAnnotation.interval() + " milliseconds.");
                    new Timer().scheduleAtFixedRate(report, 0, recurringReportAnnotation.interval());
                }
            }
        });
    }
    public static void saveReport(FlamesReport report) throws FlamesDataException {
        Properties properties = new Properties();
        properties.putAll(report.getReport());
        File file;
        try {
            file = new File(reportDir.getAbsolutePath() + "/" + report.getClass().getAnnotation(RecurringReport.class).name() + " " + Instant.now().get(ChronoField.NANO_OF_SECOND) + ".flr");
        } catch (NullPointerException e) {
            file = new File(reportDir.getAbsolutePath() + "/" + report.getClass().getAnnotation(StaticReport.class).name() + " " + Instant.now().get(ChronoField.NANO_OF_SECOND) + ".flr");
        }
        try {
            if (file.createNewFile()) {
                Flames.getFlogger().finest("Created report file for " + report.getClass().getSimpleName() + ".");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FlamesDataException("Could not create report file for " + report.getClass().getSimpleName() + "because of a(n) " + e.getClass().getSimpleName() + ".");
        }
        try {
            FileWriter fw = new FileWriter(file);
            properties.store(fw, "Flames " + report.getClass().getSimpleName());
            fw.close();
        } catch (IOException e) {
            throw new FlamesDataException("Could not save report " + report.getClass().getSimpleName() + " to file.");
        }
    }
}
