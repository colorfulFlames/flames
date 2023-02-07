/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.external.analysis;
import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;

import java.io.*;
import java.util.*;

/**
 * Handles most of the 'black box' components of Flames, namely the sentiment and entity analysis.
 */
public class Analysis {
    public static Map<String, Integer> entityCache = new HashMap<>();
    //    public static Map<String, String> knowledgeBase = new HashMap<>();
    public static FinishedAnalysis analyze(String message) throws Exception {
        return new FinishedAnalysis(analyzeEntities(message), analyzeSentiment(message));
    }
    public static Sentiment analyzeSentiment(String message) throws Exception {
        // Instantiates a client
        try (LanguageServiceClient language = LanguageServiceClient.create()) {

            // The text to analyze
            Document doc = Document.newBuilder().setContent(message).setType(Type.PLAIN_TEXT).build();

            // Detects the sentiment of the text
            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

            System.out.printf("Text: %s%n", message);
            System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
//            analyzeEntities(message);
            return sentiment;
        }
    }

    public static List<Entity> analyzeEntities(String message) throws IOException {
        boolean toast = false;
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        LanguageServiceClient language = LanguageServiceClient.create();
            Document doc = Document.newBuilder().setContent(message).setType(Type.PLAIN_TEXT).build();
            AnalyzeEntitiesRequest request =
                    AnalyzeEntitiesRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();

            AnalyzeEntitiesResponse response = language.analyzeEntities(request);
            // Print the response
        response.getEntitiesList().forEach((element) -> {
            if (!entityCache.containsKey(element.getName())) entityCache.put(element.getName(), 1);
            else entityCache.put(element.getName(), entityCache.get(element.getName()) + 1);
        });
        return response.getEntitiesList();
    }
}