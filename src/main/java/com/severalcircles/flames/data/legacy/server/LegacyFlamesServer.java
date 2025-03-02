/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.legacy.server;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class LegacyFlamesServer {
    private int score;
    private String id;
    private final int hootenannyDay;
    ServerHootenannyData hootenannyData;
    public LegacyFlamesServer(int score, String id, int hootenannyDay) {
        this.score = score;
        this.id = id;
        this.hootenannyDay = hootenannyDay;
        try {
            hootenannyData = HootenannyDataManager.getData(id);
        } catch (IOException e) {
            e.printStackTrace();
            hootenannyData = new ServerHootenannyData();
        }
    }
    public LegacyFlamesServer(String id) {
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
    public boolean todayIsHootenannyDay() {
        return hootenannyDay == new java.util.Date().getDate();
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

    public ServerHootenannyData getHootenannyData() {
        return hootenannyData;
    }

    public void setId(String id) {
        this.id = id;
    }
}
