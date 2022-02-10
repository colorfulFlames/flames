/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.conversations.ConversationsController;
import com.severalcircles.flames.conversations.ExpiredConversationException;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.external.analysis.Analysis;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.frontend.today.Today;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewMessageEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public void register() {Flames.api.addEventListener(this);}

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        User user = event.getAuthor();
        Logger logger = Logger.getGlobal();
        // Check to make sure it's worth processing the message
        // Bots don't get processed by Flames, simply because it's easier on everyone.
        if (user.isBot()) return;
        FlamesUser flamesUser;
        // Read Flames User
        try {
            flamesUser = FlamesDataManager.readUser(user);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read user data for " + user.getId() + ".");
            e.printStackTrace();
            Flames.incrementErrorCount();
            return;
        } catch (ConsentException e) {
            logger.log(Level.FINE, "Not processing " + user.getName() + "'s message because they haven't consented yet.");
            e.printStackTrace();
            return;
        } catch (DataVersionException e) {
            e.printStackTrace();
            return;
        }
        // Analyze Message
        FinishedAnalysis finishedAnalysis;
        try {
            finishedAnalysis = Analysis.analyze(event.getMessage().getContentRaw());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Process conversation
        if (ConversationsController.activeConversations.containsKey(event.getChannel().getId())) {
            try {
                ConversationsController.activeConversations.get(event.getChannel().getId()).processMessage(event.getMessage(), finishedAnalysis);
            } catch (ExpiredConversationException e) {
                logger.log(Level.INFO, "Conversation at " + event.getChannel().getId() + " is expired, removing it from the conversations list.");
                ConversationsController.activeConversations.remove(event.getChannel().getId());
            }
        } else {
            Conversation conversation = new Conversation(event.getChannel());
            try {
                conversation.processMessage(event.getMessage(), finishedAnalysis);
            } catch (ExpiredConversationException e) {
                // What the fuck
                e.printStackTrace();
            }
            ConversationsController.activeConversations.put(event.getChannel().getId(), conversation);
        }
        // Check quote of the day
        if (!Today.quote[2].equals(event.getAuthor().getId())) {
            if (finishedAnalysis.getEmotion() > Today.quoteEmotion) {
                Today.quote[0] = event.getMessage().getContentRaw();
                Today.quote[1] = event.getAuthor().getName();
                Today.quote[2] = event.getAuthor().getId();
            }
            flamesUser.setScore(flamesUser.getScore() + 864);
        }
    }
}
