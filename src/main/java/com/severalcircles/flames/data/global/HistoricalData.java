/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.data.global;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class HistoricalData {
    static final List<String[]> data = new LinkedList<>();

    public static void write() throws IOException {
        FileWriter csvWriter = new FileWriter("");
        csvWriter.append("Date");
        csvWriter.append(",");
        csvWriter.append("Average Score");
        csvWriter.append(",");
        csvWriter.append("Global Score");
        csvWriter.append(",");
        csvWriter.append("Participants");
        csvWriter.append(",");
        csvWriter.append("Approaching Bronze Threshold");
        csvWriter.append(",");
        csvWriter.append("Bronze Threshold");
        csvWriter.append(",");
        csvWriter.append("Silver Threshold");
        csvWriter.append(",");
        csvWriter.append("Shining Silver Threshold");
        csvWriter.append(",");
        csvWriter.append("Gold Threshold");
        csvWriter.append(",");
        csvWriter.append("Beyond Gold Threshold");
        csvWriter.append(",");
        csvWriter.append("Platinum Threshold");
        csvWriter.append(",");
        csvWriter.append("Sparkling Platinum Threshold");
        csvWriter.append(",");
        csvWriter.append("Platinum Summit Threshold");
        csvWriter.append("\n");

        for (String[] rowData : data) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
            data.remove(rowData);
        }

    }
}
