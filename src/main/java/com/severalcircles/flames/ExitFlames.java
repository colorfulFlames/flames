/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames;

import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExitFlames extends Thread {
    @Override
    public void run() {
        Logger.getGlobal().log(Level.INFO, "Preparing to exit Flames");
        Logger.getGlobal().log(Level.INFO,
                "***Flames Exit Report***\n" +
                "Messages Processed: " + FlamesRunStats.messageCount + "\n" +
                "Commands Used: " + FlamesRunStats.commandCount + "\n" +
                "Buttons Pressed: " + FlamesRunStats.buttonCount + "\n\n" +
                "Total Interactions: " + (FlamesRunStats.buttonCount + FlamesRunStats.commandCount + FlamesRunStats.messageCount) + "\n" +
                "Fatal Error Count: " + Flames.fatalErrorCounter);
    }
}
