package com.severalcircles.flames.data.user;

import com.severalcircles.flames.features.rank.Rank;

import java.time.Instant;

public class UserFunFacts {
    private Instant sadDay;
    private float lowestEmotion;
    private Instant happyDay;
    private float highestEmotion;

    private int highestFlamesScore;
    private int lowestFlamesScore;
    private Rank bestRank;

    private int frenchToastMentioned;

    public UserFunFacts(Instant sadDay, float lowestEmotion, Instant happyDay, float highestEmotion, int highestFlamesScore, int lowestFlamesScore, Rank bestRank, int frenchToastMentioned) {
        this.sadDay = sadDay;
        this.lowestEmotion = lowestEmotion;
        this.happyDay = happyDay;
        this.highestEmotion = highestEmotion;
        this.highestFlamesScore = highestFlamesScore;
        this.lowestFlamesScore = lowestFlamesScore;
        this.bestRank = bestRank;
        this.frenchToastMentioned = frenchToastMentioned;
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
