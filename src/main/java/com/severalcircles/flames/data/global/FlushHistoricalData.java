/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.data.global;

import com.severalcircles.flames.system.Flames;

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
