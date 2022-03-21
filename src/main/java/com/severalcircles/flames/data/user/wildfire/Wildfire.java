/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.data.user.wildfire;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import net.dv8tion.jda.api.entities.Message;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class Wildfire {
    private static Map<String, UserWildfire> wildfireMap = new HashMap<>();
    private static Map<String, Integer> savableData = new HashMap<>();
    public static final int maxScore = 100;
    private static int counter = 0;
    public static final int greenThreshold = Math.round((2/3) * maxScore);
    public static final int yellowThreshold = Math.round((1/3) * maxScore);

    public static void initialize() throws IOException {
        Properties data = new Properties();
        FileInputStream is = new FileInputStream(FlamesDataManager.wildfireFile);
        data.load(is);
        data.forEach((k, v) -> {
            savableData.put(k + "", Integer.parseInt(v + ""));
        });
    }
    public static void save() throws IOException {
        Properties data = new Properties();
        data.putAll(savableData);
        FileOutputStream os = new FileOutputStream(FlamesDataManager.wildfireFile);
        data.store(os, "Flames Wildfire Data");
    }
    public static void processMessage(Message message) {
        UserWildfire userWildfire;
        AtomicBoolean noPenalties = new AtomicBoolean(true);
        if (wildfireMap.containsKey(message.getAuthor().getId())) userWildfire = wildfireMap.get(message.getAuthor().getId());
        else userWildfire = new UserWildfire(message.getAuthor());
        userWildfire.getRecentMessages().forEach(value -> {
            if (WildfireUtils.checkSimilarity(message.getContentRaw(), value) >= .5) {
                userWildfire.subtractPoints(2, WildfireReason.TOO_SIMILAR_MSG);
                noPenalties.set(false);
            }
        });
        if (userWildfire.getLastMessage().plus(5, ChronoUnit.SECONDS).compareTo(Instant.now()) < 1) {
            userWildfire.subtractPoints(5, WildfireReason.TOO_FAST_MSG);
            noPenalties.set(false);
        }
        if (noPenalties.get()) userWildfire.subtractPoints(-1, WildfireReason.NO_PENALTY_BONUS);
        if (userWildfire.getRecentMessages().size() > 10) userWildfire.popMessage();
        savableData.put(message.getAuthor().getId(), userWildfire.getPoints());
        counter++;
        if (counter == 10) {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
                Flames.incrementErrorCount();
            }
            counter = 0;
        }
    }

}