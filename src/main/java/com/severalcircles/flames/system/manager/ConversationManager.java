/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Analysis;
import com.severalcircles.flames.conversations.AnalysisScore;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.conversations.ConversationEntity;
import com.severalcircles.flames.data.user.FlamesQuote;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.FlamesExceptionHandler;
import com.severalcircles.flames.system.exception.java.FlamesDataException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
@ExceptionID("940")
public class ConversationManager extends FlamesManager {
    // Map of channel ID to conversation
    public static Map<String, Conversation> conversations = new HashMap<>();
    @Override
    public void prepare() {
        new Timer().scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                removedExpiredConversations();
            }
        }, 0, 1000 * 60 * 5);
    }
    public static void removedExpiredConversations() {
        UserDataManager udm = new UserDataManager();
        conversations.forEach((channel, conversation) -> {
            if (conversation.getExpires().isBefore(Instant.now())) {
                Flames.getFlogger().fine("Removing expired conversation from channel " + channel);
                conversation.getParticipationMap().forEach((user, participation) -> {
                    user.addScore(conversation.getScore() / conversation.getParticipationMap().size());
                    user.addEmotion(conversation.getEmotion() / conversation.getParticipationMap().size());
                    try {
                        udm.saveUser(user);
                    } catch (IOException e) {
                        new FlamesExceptionHandler(new FlamesDataException("Failed to save user " + user + " after conversation ended"));
                    }});
                conversations.remove(channel);
            }
        });
    }
    public static void processMessage(MessageChannelUnion channel, Message message, FlamesUser user) {
        if (!conversations.containsKey(channel.getId())) {
            conversations.put(channel.getId(), new Conversation(channel));
        }
        conversations.forEach((channelID, conversation) -> {
            if (channelID.equals(channel.getId())) {
                AnalysisScore score = new AnalysisScore(Analysis.analyze(message.getContentRaw()));
                conversation.addParticipation(user);
                conversation.addEmotion(score.emotion());
                conversation.addScore(score.score());
                conversation.addTopic(new ConversationEntity(message.getContentRaw(), message.getAuthor().getId()));
                Flames.getFlogger().finest("Conversation: " + conversation);
                Flames.getFlogger().finest("Conversation topics: \n" + conversation.getTopics());
                Flames.getFlogger().finest("Conversation participation: \n" + conversation.getParticipationMap());
                Flames.getFlogger().finest("Conversation score: " + conversation.getScore());
                Flames.getFlogger().finest("Conversation emotion: " + conversation.getEmotion());
                Flames.getFlogger().finest("Conversation started: " + conversation.getStarted());
                Flames.getFlogger().finest("Conversation expires: " + conversation.getExpires());
                Flames.getFlogger().finest("Conversation channel: " + conversation.getChannel());
                conversation.updateFavoriteQuote(new FlamesQuote(message.getContentRaw(), user), score.score());
                Flames.getFlogger().finest("Conversation favorite quote: " + conversation.getFavoriteQuote().message() + " by " + conversation.getFavoriteQuote().user().getDiscordUser().getName());
            }
        });
    }
}
