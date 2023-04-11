/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
import com.severalcircles.flames.Flames;

import java.io.IOException;

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
}
