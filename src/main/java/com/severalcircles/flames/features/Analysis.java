package com.severalcircles.flames.features;
import com.google.cloud.language.v1.*;
import com.google.cloud.language.v1.Document.Type;
import com.severalcircles.flames.data.base.FlamesData;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Analysis {
    public static Map<String, Integer> entityCache = new HashMap<>();
//    public static Map<String, String> knowledgeBase = new HashMap<>();
    public static Sentiment analyze(String message) throws Exception {
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
    public static boolean analyzeEntities(String message) {
        boolean toast = false;
        // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
        try (LanguageServiceClient language = LanguageServiceClient.create()) {
            Document doc = Document.newBuilder().setContent(message).setType(Type.PLAIN_TEXT).build();
            AnalyzeEntitiesRequest request =
                    AnalyzeEntitiesRequest.newBuilder()
                            .setDocument(doc)
                            .setEncodingType(EncodingType.UTF16)
                            .build();

            AnalyzeEntitiesResponse response = language.analyzeEntities(request);

            // Print the response
            for (Entity entity : response.getEntitiesList()) {
                System.out.printf("Entity: %s", entity.getName());
                System.out.printf("Salience: %.3f\n", entity.getSalience());
                System.out.println("Metadata: ");
                if (entity.getName().contains("french toast")) toast = true;
//                Logger.getGlobal().log(Level.FINE, "Reading guild: " + discordId);
                if (entityCache.containsKey(entity.getName())) entityCache.put(entity.getName(), entityCache.get(entity.getName()) + 1);
                else entityCache.put(entity.getName(), 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toast;
    }
}