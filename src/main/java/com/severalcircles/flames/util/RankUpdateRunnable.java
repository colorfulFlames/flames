/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.util;

import java.io.IOException;

public class RankUpdateRunnable implements Runnable{
    @Override
    public void run() {
        try {
            Ranking.updateThresholds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
