/*
 * Copyright (c) 2021-2021 Several Circles.
 */

package com.severalcircles.flames.data.global;

import com.severalcircles.flames.Flames;

import java.io.IOException;

public class FlushHistoricalData implements Runnable {

    @Override
    public void run() {
        try {
            HistoricalData.write();
        } catch (IOException e) {
            e.printStackTrace();
            Flames.incrementErrorCount();
        }
    }
}
