/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.server;

import java.util.Properties;
import java.util.Random;

public class FlamesServer {
    private int score;
    private String id;
    private int hootenannyDay;

    public FlamesServer(int score, String id, int hootenannyDay) {
        this.score = score;
        this.id = id;
        this.hootenannyDay = hootenannyDay;

    }
    public FlamesServer(String id) {
        this.id = id;
        this.score = 0;
        this.hootenannyDay = new Random().nextInt(28) + 1; // Randomly select a day of the month for Hootanany Day
    }
    public Properties createData() {
        Properties data = new Properties();
        data.put("score", String.valueOf(score));
        data.put("id", id);
        data.put("hootenannyDay", String.valueOf(hootenannyDay));
        return data;
    }
    public int getHootenannyDay() {
     return hootenannyDay;
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
