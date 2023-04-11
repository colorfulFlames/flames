/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations;

import com.google.cloud.language.v1.Sentiment;

public record AnalysisScore(
        double emotion,
        double score
) {
    public AnalysisScore(Sentiment sentiment) {
        this(sentiment.getScore() * sentiment.getMagnitude(), sentiment.getScore() * 10);
    }

}
