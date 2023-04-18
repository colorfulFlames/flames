/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;

import java.io.*;
import java.util.*;

public class FlamesQuestionManager extends FlamesManager {
    public static final File questionFile = new File(SystemDataManager.getFlamesDirectory().getAbsolutePath() + "/questions.flp");
    private static final List<String> questions = new LinkedList<>();
    @Override
    public void prepare() throws IOException {
        if (questionFile.createNewFile()) {
            Flames.getFlogger().fine("Created question file");
        }
        ResourceBundle questions = ResourceBundle.getBundle("strings/Questions");
        questions.keySet().forEach(key -> {
            if (!key.equals("answer")) {
                this.questions.add(key);
            }
        });
    }

    public static void answerQuestion(String id, String answer, FlamesUser user) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(questionFile));
        id = id.replace("question:", "");
        properties.setProperty(id, answer);
        properties.store(new FileWriter(questionFile), "Questions answered");
    }

    public static String getAnswer(String question) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(questionFile));
        List<String> answers = new LinkedList<>();
        properties.forEach((key, value) -> {
            if (key.toString().startsWith(question)) {
                answers.add(value.toString());
            }
        });
        return answers.get((int) (Math.random() * answers.size()));
    }
    public static String getQuestion() {
        int i = new Random().nextInt(questions.size());
        return questions.get(i);
    }
}