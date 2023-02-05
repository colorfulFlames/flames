/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.external.dialog;

import com.google.cloud.dialogflow.v2.*;

import java.io.IOException;

public class DialogSession {
    private final String sessionId;
    public DialogSession() {
        sessionId = String.valueOf(Math.round(Math.random() * 10000));
    }
    public String processMessage(String text, String id) throws IOException {
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            // Set the session name using the sessionId (UUID) and projectID (my-project-id)
            SessionName session = SessionName.of("flames-312616", sessionId);
            System.out.println("Session Path: " + session.toString());

            // Detect intents for each text input
                // Set the text (hello) and language code (en-US) for the query
                TextInput.Builder textInput =
                        TextInput.newBuilder().setText(text).setLanguageCode("en-US");

                // Build the query with the TextInput
                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();

                // Performs the detect intent request
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);

                // Display the query result
                QueryResult queryResult = response.getQueryResult();

                System.out.println("====================");
                System.out.format("Query Text: '%s'\n", queryResult.getQueryText());
                System.out.format(
                        "Detected Intent: %s (confidence: %f)\n",
                        queryResult.getIntent().getDisplayName(), queryResult.getIntentDetectionConfidence());
                System.out.format(
                        "Fulfillment Text: '%s'\n",
                        queryResult.getFulfillmentMessagesCount() > 0
                                ? queryResult.getFulfillmentMessages(0).getText()
                                : "Triggered Default Fallback Intent");

                return queryResult.getFulfillmentText() + "~" + queryResult.getIntent().getDisplayName() + " ";
            }
    }
}
