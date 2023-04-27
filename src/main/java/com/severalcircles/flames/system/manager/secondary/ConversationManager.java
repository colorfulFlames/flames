/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager.secondary;

import com.google.cloud.language.v1.Sentiment;
import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.Analysis;
import com.severalcircles.flames.conversations.AnalysisScore;
import com.severalcircles.flames.conversations.Conversation;
import com.severalcircles.flames.conversations.today.Today;
import com.severalcircles.flames.data.user.FlamesQuote;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.FlamesManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicReference;

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
        }, 0, 1000 * 60);
    }
    public static void removedExpiredConversations() {
        UserDataManager udm = new UserDataManager();
        Map <String,Conversation> cnvs = new HashMap<>(conversations);
        cnvs.forEach((channel, conversation) -> {
            if (conversation.getExpires().isBefore(Instant.now())) {
                Flames.getFlogger().fine("Removing expired conversation from channel " + channel);
                conversation.getParticipationMap().forEach((user, participation) -> {
                    user.addScore(conversation.getScore() / conversation.getParticipationMap().size());
                    user.addEmotion(conversation.getEmotion() / conversation.getParticipationMap().size());
                        udm.saveUser(user);
                    });
                conversation.getTopics().forEach((topic, score) -> {
                    Today.addTopic(topic);
                });
                Today.addEmotion(conversation.getEmotion());
                conversations.remove(channel);
            }
        });
    }
    public static void processMessage(MessageChannelUnion channel, Message message, FlamesUser user) {
        if (!conversations.containsKey(channel.getId())) {
            conversations.put(channel.getId(), new Conversation(channel));
        }
        AtomicReference<AnalysisScore> score = new AtomicReference<>();
        conversations.forEach((channelID, conversation) -> {
            if (channelID.equals(channel.getId())) {
                score.set(new AnalysisScore(Analysis.analyze(message.getContentRaw())));
                FlamesDataManager.addScore(score.get().score());
                conversation.addParticipation(user);
                conversation.addEmotion(score.get().emotion());
                conversation.addScore(score.get().score());
                conversation.addTopics(Analysis.analyzeEntities(message.getContentRaw()));
                Flames.getFlogger().finest("Conversation: " + conversation);
                Flames.getFlogger().finest("Conversation topics: \n" + conversation.getTopics());
                Flames.getFlogger().finest("Conversation participation: \n" + conversation.getParticipationMap());
                Flames.getFlogger().finest("Conversation score: " + conversation.getScore());
                Flames.getFlogger().finest("Conversation emotion: " + conversation.getEmotion());
                Flames.getFlogger().finest("Conversation started: " + conversation.getStarted());
                Flames.getFlogger().finest("Conversation expires: " + conversation.getExpires());
                Flames.getFlogger().finest("Conversation channel: " + conversation.getChannel());
                conversation.updateFavoriteQuote(new FlamesQuote(message.getContentRaw(), user), score.get().score());
                Flames.getFlogger().finest("Conversation favorite quote: " + conversation.getFavoriteQuote().message() + " by " + conversation.getFavoriteQuote().user().getDiscordUser().getName());
            }
            user.addMessage();
            Sentiment s = Analysis.analyze(user.getFavoriteQuote().message());
            AnalysisScore as = new AnalysisScore(s);
            Today.checkQuote(new FlamesQuote(message.getContentRaw(), user), score.get().score());
            Today.checkUser(user);
            if (score.get().emotion() > as.emotion() | new Random().nextInt(1000) == 69) user.setFavoriteQuote(new FlamesQuote(message.getContentRaw(), user));
            FlamesDataManager.addGloabalScore(score.get().score());
            new UserDataManager().saveUser(user);
        });
    }
}
