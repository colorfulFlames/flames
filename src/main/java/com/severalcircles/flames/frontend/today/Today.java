/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Today {
    public static final int QUOTE_BONUS = 1000;
    public static final TodayQuote defaultQuote = new TodayQuote("We're still waiting for somebody to say something epic.", 0, "Flames", Instant.now().minus(17, ChronoUnit.MINUTES), 0);
    public static int highScore = 0;
    public static String highUser = "Nobody yet!";
    public static float emotion = 0;
//    public static String[] quote = {"We're still waiting for somebody to say something epic.", "Flames", "0"};
    public static TodayQuote quote = defaultQuote;
    public static List<String> thanks = new LinkedList<>();
    public static boolean quoteMessage(String msg, String author, double emotion) {
        addEmotion(emotion);
        emotion = Math.abs(emotion);
        int seed = new Random().nextInt(10);
        System.out.println("Seed:" + seed);
        TodayQuote quote = new TodayQuote(msg, emotion, author, Instant.now(), seed);
        if (seed % 2 == 0 &&
                emotion > Today.quote.emotion() &&
                Duration.between(Today.quote.inst(), Instant.now()).toMinutes() >= 15) {
            Today.quote = quote;
            return true;
        }
        else return false;
    }
    public static boolean highScore(String user, int score) {
        if (score > highScore) {
            highScore = score;
            highUser = user;
            return true;
        }
        else return false;
    }
    public static void addEmotion(double emotion){Today.emotion += emotion;}

}
record TodayQuote(String message, double emotion, String author, Instant inst, int seed) {
}