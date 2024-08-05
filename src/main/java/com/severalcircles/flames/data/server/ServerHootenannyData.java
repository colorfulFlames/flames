/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerHootenannyData {
    final Map<String, Integer> userScores = new HashMap<>();
    public void addUser(String userId) {
        userScores.put(userId, 0);
    }
    public void addScore(String userId, int score) {
        if (!userScores.containsKey(userId)) addUser(userId);
        userScores.put(userId, userScores.get(userId) + score);
    }
    HootenannyTitle getTitle(String userId) {
        int score = userScores.get(userId);
        HootenannyTitle title = HootenannyTitle.SERVER_FAN;
        for (HootenannyTitle value : HootenannyTitle.values()) {
            if (score >= value.getScoreThresh()) {
                title = value;
            }
        }
        return title;
    }
    public int getScore(String userId) {
        return userScores.get(userId);
    }
    public void setScore(String userId, int score) {
        userScores.put(userId, score);
    }
    public Properties createData() {
        Properties data = new Properties();
        for (String userId : userScores.keySet()) {
            data.put(userId, String.valueOf(userScores.get(userId)));
        }
        return data;
    }
}
