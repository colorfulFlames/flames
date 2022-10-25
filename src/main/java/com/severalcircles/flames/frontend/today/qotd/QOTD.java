/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.today.qotd;

import java.util.*;

public class QOTD {
    static Queue<String> questionQueue = new PriorityQueue<>();
    static Map<String, String> idQuestionMap = new HashMap<>();
    static Queue<String[]> answers = new PriorityQueue<>();
    public static String[] qotd = {"What? How?", "Flames"};
    public static void nextQuestion() {
        answers = new PriorityQueue<>();
    }
    public static void updateQuestion(String id, String question) throws InvalidQuestionException {
        List<String> questionWords = Arrays.asList("who", "what", "where", "when", "why", "how");
        if (!questionQueue.contains(id)) questionQueue.add(id);
        if (!questionWords.stream().anyMatch(question::contains) | !question.contains("?")) {
            throw new InvalidQuestionException();
        }
        idQuestionMap.put(id, question);
    }
    public static void answer(String name, String answer) {
        answers.add(new String[]{name, answer});
    }
}
