/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.today.qotd;

import java.util.*;

public class QOTD {
    static Queue<String[]> answers = new PriorityQueue<>();
    public static final String[] qotd = {"What? How?", "Flames"};
    public static void nextQuestion() {
        answers = new PriorityQueue<>();
    }
}
