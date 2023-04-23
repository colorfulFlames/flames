/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations;

import com.google.cloud.language.v1.Sentiment;

/**
 * AnalysisScore class for storing sentiment and emotion scores.
 * @param emotion
 * @param score
 * @author Several Circles
 * @version 8
 * @since Flames 8
 */
public record AnalysisScore(
        double emotion,
        double score
) {
    public AnalysisScore(Sentiment sentiment) {
        this(sentiment.getScore() * sentiment.getMagnitude(), sentiment.getScore() * 10);
    }

}
