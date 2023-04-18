/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class FlamesQuestionManager extends FlamesManager {
    public static final File questionFile = new File(SystemDataManager.getFlamesDirectory().getAbsolutePath() + "/questions.flp");

    @Override
    public void prepare() throws IOException {
        if (questionFile.mkdir()) {
            Flames.getFlogger().fine("Created question file");
        }
    }

    public static void answerQuestion(String question, String answer, FlamesUser user) throws IOException {
        Properties properties = new Properties();
        properties.setProperty(question + "." + user.getDiscordUser().getId(), answer);
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
}