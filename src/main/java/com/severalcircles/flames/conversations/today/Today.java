/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations.today;

import com.severalcircles.flames.data.user.FlamesQuote;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.UserDataManager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Today is a class that stores data about the current day. Today, we're talking about Flames, and we're feeling a certain way.
 * @author Several Circles
 * @version 8
 * @since Flames 4
 */
public class Today {
    static Map<String, Integer> topics = new HashMap<>();
    static FlamesQuote quote = new FlamesQuote("No quote found", UserDataManager.flames());
    static double quoteScore = 0;
    static double emotion = 0;
    public static List<String> thanks = new LinkedList<>();
    static FlamesUser highUser = UserDataManager.flames();
    static {
        int msToMidnight = (int) (Instant.now().until(Instant.now().plusSeconds(60 * 60 * 24).truncatedTo(ChronoUnit.DAYS), ChronoUnit.SECONDS) * 1000);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                topics.clear();
                quote = new FlamesQuote("No quote found", UserDataManager.flames());
                quoteScore = 0;
                emotion = 0;
                highUser = UserDataManager.flames();
            }
        }, msToMidnight, 1000 * 60 * 60 * 24);
    }
    public static void addTopic(String topic) {
        if (topics.containsKey(topic)) {
            topics.put(topic, topics.get(topic) + 1);
        } else {
            topics.put(topic, 1);
        }
    }
    public static void checkQuote(FlamesQuote quote, double score) {
        if (score > quoteScore) {
            Today.quote = quote;
            quoteScore = score;
        }
    }
    public static void addEmotion(double emotion) {
        Today.emotion += emotion;
    }
    public static void checkUser(FlamesUser user) {
        if (user.getScore() > highUser.getScore()) {
            highUser = user;
        }
    }
    public static String getTopTopic() {
        AtomicReference<Integer> max = new AtomicReference<>(0);
        AtomicReference<String> toptopic = new AtomicReference<>("");
        topics.forEach((topic, count) -> {
            if (count > max.get()) {
                max.set(count);
                toptopic.set(topic);
            }
        });
        return toptopic.get();
    }

    public static double getEmotion() {
        return emotion;
    }

    public static Map<String, Integer> getTopics() {
        return topics;
    }

    public static FlamesQuote getQuote() {
        return quote;
    }

    public static double getQuoteScore() {
        return quoteScore;
    }

    public static FlamesUser getHighUser() {
        return highUser;
    }
}
