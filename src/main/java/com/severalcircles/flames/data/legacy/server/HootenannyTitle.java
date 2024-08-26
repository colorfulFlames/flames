/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.legacy.server;

public enum HootenannyTitle {
    SERVER_FAN(0), SERVER_ENJOYER(100), SERVER_FIEND(500),
    SERVER_DEFENDER(1000), SERVER_CHAMPION(2500), SERVER_RULER(5000)
    ;
    final int scoreThresh;
    HootenannyTitle(int scoreThresh) {
        this.scoreThresh = scoreThresh;
    }
    public int getScoreThresh() {
        return scoreThresh;
    }
}
