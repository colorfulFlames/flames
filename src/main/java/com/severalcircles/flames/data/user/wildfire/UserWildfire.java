/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.data.user.wildfire;

import net.dv8tion.jda.api.entities.User;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserWildfire {
    private int points = 100;
    private WildfireStage stage = WildfireStage.GREEN;
    private List<String> recentMessages = new LinkedList<>();
    private Instant lastMessage = Instant.now();
    private User user;
    private Map<WildfireReason, Integer> reasonCounts = new HashMap<>();
    public UserWildfire(User user) {
        this.user = user;
        for (WildfireReason value : WildfireReason.values()) {
            reasonCounts.put(value, 0);
        }
    }
    public void subtractPoints(int points, WildfireReason reason) {
        Logger.getGlobal().log(Level.INFO, "[Wildfire] -" + points + " to" + user.getName() + " for reason " + reason.toString());
        this.points -= points;
        reasonCounts.put(reason, reasonCounts.get(reason) + 1);
        if (this.points < Wildfire.yellowThreshold) this.stage = WildfireStage.RED;
        else if (this.points < Wildfire.greenThreshold) this.stage = WildfireStage.YELLOW;
        else this.stage = WildfireStage.GREEN;
        if (points < 0) {
            Logger.getGlobal().log(Level.WARNING, "[Wildfire] Wildfire score for " + user.getName() + " would go below zero.");
            this.points = 0;
        }
    }
    public void popMessage() {
        recentMessages.remove(0);
    }
    public int getPoints() {
        return points;
    }

    public WildfireStage getStage() {
        return stage;
    }

    public List<String> getRecentMessages() {
        return recentMessages;
    }

    public Instant getLastMessage() {
        return lastMessage;
    }
}
