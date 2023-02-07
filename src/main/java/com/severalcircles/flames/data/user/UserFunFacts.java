/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesData;
import com.severalcircles.flames.util.Rank;

import java.time.Instant;
import java.util.Properties;

/**
 * Represents Fun Facts data for a FlamesUser
 */
public class UserFunFacts implements FlamesData {
    private Instant sadDay;
    private float lowestEmotion;
    private Instant happyDay;
    private float highestEmotion;

    private int highestFlamesScore;
    private int lowestFlamesScore;
    private Rank bestRank;

    private String favoriteQuote;

    private int frenchToastMentioned;

    /**
     *
     * @return Properties object which can be written to a file or returned by the Flames API
     */
    public Properties createData() {
        Properties data = new Properties();
        data.put("sadDay", sadDay.toString());
        data.put("lowestEmotion", String.valueOf(lowestEmotion));
        data.put("highestEmotion", String.valueOf(highestEmotion));
        data.put("happyDay", happyDay.toString());
        data.put("highScore", String.valueOf(highestFlamesScore));
        data.put("lowScore", String.valueOf(lowestFlamesScore));
        data.put("frenchToastScore", String.valueOf(frenchToastMentioned));
        data.put("bestRank", bestRank.toString());
        data.put("favoriteQuote", favoriteQuote);
        return data;
    }
    public UserFunFacts() {
        this.sadDay = Instant.now();
        this.lowestEmotion = 0;
        this.happyDay = Instant.now();
        this.highestEmotion = 0;
        this.highestFlamesScore = 0;
        this.lowestFlamesScore = 0;
        this.bestRank = Rank.UNRANKED;
        this.frenchToastMentioned = 0;
        this.favoriteQuote = "I haven't said anything epic yet.";
    }
    public Instant getSadDay() {
        return sadDay;
    }

    public void setSadDay(Instant sadDay) {
        this.sadDay = sadDay;
    }

    public float getLowestEmotion() {
        return lowestEmotion;
    }

    public void setLowestEmotion(float lowestEmotion) {
        this.lowestEmotion = lowestEmotion;
    }

    public Instant getHappyDay() {
        return happyDay;
    }

    public void setHappyDay(Instant happyDay) {
        this.happyDay = happyDay;
    }

    public float getHighestEmotion() {
        return highestEmotion;
    }

    public void setHighestEmotion(float highestEmotion) {
        this.highestEmotion = highestEmotion;
    }

    public int getHighestFlamesScore() {
        return highestFlamesScore;
    }

    public void setHighestFlamesScore(int highestFlamesScore) {
        this.highestFlamesScore = highestFlamesScore;
    }

    public int getLowestFlamesScore() {
        return lowestFlamesScore;
    }

    public void setLowestFlamesScore(int lowestFlamesScore) {
        this.lowestFlamesScore = lowestFlamesScore;
    }

    public void setBestRank(Rank bestRank) {
        this.bestRank = bestRank;
    }

    public void setFrenchToastMentioned(int frenchToastMentioned) {
        this.frenchToastMentioned = frenchToastMentioned;
    }

    public String getFavoriteQuote() {
        return favoriteQuote;
    }

    public void setFavoriteQuote(String favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }
}
