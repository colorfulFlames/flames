/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.server;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class FlamesServer {
    private int score;
    private String id;
    private int hootanannyDay;

    public FlamesServer(int score, String id, int hootananyDay) {
        this.score = score;
        this.id = id;
        this.hootanannyDay = hootananyDay;

    }
    public FlamesServer(String id) {
        this.id = id;
        this.score = 0;
        this.hootanannyDay = new Random().nextInt(28) + 1; // Randomly select a day of the month for Hootanany Day
    }
    public Properties createData() {
        Properties data = new Properties();
        data.put("score", String.valueOf(score));
        data.put("id", id);
        data.put("hootanannyDay", String.valueOf(hootanannyDay));
        return data;
    }
    public int getHootanannyDay() {
     return hootanannyDay;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
