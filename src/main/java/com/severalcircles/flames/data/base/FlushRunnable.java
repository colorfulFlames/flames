package com.severalcircles.flames.data.base;

import java.io.IOException;
import java.util.TimerTask;

public class FlushRunnable extends TimerTask implements Runnable{
    @Override
    public void run() {
        try {
            FlamesData.flushCaches();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
