/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external.analysis;

import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.Sentiment;

import java.util.List;

public class FinishedAnalysis {
    private List<Entity> entityList;
    private Sentiment sentiment;

    public FinishedAnalysis(List<Entity> entityList, Sentiment sentiment) {
        this.entityList = entityList;
        this.sentiment = sentiment;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }
    public double getEmotion() {
        return this.sentiment.getScore() * this.sentiment.getMagnitude();
    }
}
