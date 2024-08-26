/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

public class FlamesServer extends FlamesDatatype {
    String id;
    int score;
    int hootenannyDay;
    Map<String, Integer> hootenannyScores;
    public void setId(String id) {
        this.id = id;
    }
        public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHootenannyDay() {
        return hootenannyDay;
    }

    public void setHootenannyDay(int hootenannyDay) {
        this.hootenannyDay = hootenannyDay;
    }

    @Override
    public String getID() {
        return id;
    }

    public FlamesServer(String id, int score, int hootenannyDay, Map<String, Integer> hootenannyScores) {
        this.id = id;
        this.score = score;
        this.hootenannyDay = hootenannyDay;
        this.hootenannyScores = hootenannyScores;
    }
    public FlamesServer(String id) {
        this.id = id;
        this.score = 0;
        this.hootenannyDay = 1;
        this.hootenannyScores = new HashMap<>();
    }
    public FlamesServer() {
        this.id = null;
    }

    public String getId() {
        return id;
    }

    public Map<String, Integer> getHootenannyScores() {
        return hootenannyScores;
    }

    public void setHootenannyScores(Map<String, Integer> hootenannyScores) {
        this.hootenannyScores = hootenannyScores;
    }
    public boolean todayIsHootenannyDay() {
        return hootenannyDay == new java.util.Date().getDate();
    }
}
