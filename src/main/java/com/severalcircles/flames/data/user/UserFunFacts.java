package com.severalcircles.flames.data.user;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.severalcircles.flames.data.FlamesData;
import com.severalcircles.flames.features.rank.Rank;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Properties;

public class UserFunFacts implements FlamesData {
    private Instant sadDay;
    private float lowestEmotion;
    private Instant happyDay;
    private float highestEmotion;

    private int highestFlamesScore;
    private int lowestFlamesScore;
    private Rank bestRank;

    private int frenchToastMentioned;
    public Properties createData() {
        Properties data = new Properties();
        data.put("sadDay", sadDay.toString());
        data.put("lowestEmotion", lowestEmotion + "");
        data.put("highestEmotion", highestEmotion + "");
        data.put("happyDay", happyDay.toString());
        data.put("highScore", highestFlamesScore + "");
        data.put("lowScore", lowestFlamesScore + "");
        data.put("frenchToastScore", frenchToastMentioned + "");
        data.put("bestRank", bestRank.toString());
        return data;
    }
    public UserFunFacts(@NotNull Instant sadDay, @NotNull float lowestEmotion, Instant happyDay, @NotNull float highestEmotion, @NotNull int highestFlamesScore, @NotNull int lowestFlamesScore, @NotNull Rank bestRank, @NotNull int frenchToastMentioned) {
        this.sadDay = sadDay;
        this.lowestEmotion = lowestEmotion;
        this.happyDay = happyDay;
        this.highestEmotion = highestEmotion;
        this.highestFlamesScore = highestFlamesScore;
        this.lowestFlamesScore = lowestFlamesScore;
        this.bestRank = bestRank;
        this.frenchToastMentioned = frenchToastMentioned;
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

    public Rank getBestRank() {
        return bestRank;
    }

    public void setBestRank(Rank bestRank) {
        this.bestRank = bestRank;
    }

    public int getFrenchToastMentioned() {
        return frenchToastMentioned;
    }

    public void setFrenchToastMentioned(int frenchToastMentioned) {
        this.frenchToastMentioned = frenchToastMentioned;
    }
}
