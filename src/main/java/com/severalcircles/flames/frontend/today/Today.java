/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Today {
    public static int highScore = 0;
    public static String highUser = "Nobody yet!";
    public static float emotion = 0;
    public static String[] quote = {"We're still waiting for somebody to say something epic.", "Flames", "0"};
    public static double quoteEmotion = 0;
    public static List<String> thanks = new LinkedList<>();
    public static boolean isThanksgiving = false;
    public static Map<String, List<String>> thanksgivingThanks = new HashMap<>();
}
