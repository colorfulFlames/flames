/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.severalcircles.flames.Flames;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Analysis class for analyzing messages and returning sentiment and entities.
 * @author Several Circles
 * @version 8
 * @since Flames 2
 */
public class Analysis {
    public static Sentiment analyze(String text) {
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().
                    setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
            Flames.getFlogger().finest("Sentiment: \n" + sentiment);
            return sentiment;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<String> analyzeEntities(String text) {
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().
                    setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            List<Entity> entities = language.analyzeEntities(doc).getEntitiesList();
            List<String> finalEntities = new LinkedList<>();
            entities.forEach(entity -> {
                Flames.getFlogger().finest("Entity: \n" + entity.getName());
                finalEntities.add(entity.getName());
            });
            return finalEntities;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
