/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conversation {
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Integer> entities;
    @SuppressWarnings("FieldMayBeFinal")
    private MessageChannel channel;
    @SuppressWarnings("FieldMayBeFinal")
    private List<User> userList;
    private Instant expires;
    private double emotion;
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, String> quote;
    @SuppressWarnings("FieldMayBeFinal")
    private double quoteScore;
    private Map<String, FlamesUser> conversationCache;
    public Conversation(MessageChannel channel) {
        this.channel = channel;
        this.entities = new HashMap<>();
        this.userList = new LinkedList<>();
        this.expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        this.emotion = 0;
        this.quote = new HashMap<>();
        this.quoteScore = 0;
    }

    public Map<String, Integer> getEntities() {
        return entities;
    }

    public MessageChannel getChannel() {
        return channel;
    }

    public List<User> getUserList() {
        return userList;
    }

    public double getEmotion() {
        return emotion;
    }

    public Map<String, String> getQuote() {
        return quote;
    }

    public double getQuoteScore() {
        return quoteScore;
    }

    public Instant getExpires() {
        return expires;
    }
    public void processMessage(Message message, FinishedAnalysis finishedAnalysis) throws ExpiredConversationException {
        Logger.getGlobal().log(Level.INFO, "Processing Message");
        if (expires.compareTo(Instant.now()) > 0) throw new ExpiredConversationException();
        expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        emotion += finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude();
        if (!userList.contains(message.getAuthor())) userList.add(message.getAuthor());
        finishedAnalysis.getEntityList().forEach((element) -> {
            if (!entities.containsKey(element.getName())) entities.put(element.getName(), 1);
            else entities.put(element.getName(), entities.get(element.getName()) + 1);
        });
        userList.forEach((element) -> {
            if (!conversationCache.containsKey(element.getId())) {
                try {
                    conversationCache.put(element.getId(), FlamesDataManager.readUser(element));
                } catch (IOException | DataVersionException e) {
                    e.printStackTrace();
                    return;
                } catch (ConsentException e) {
                    return;
                }
            }
            FlamesUser user = conversationCache.get(element.getId());
            userList.forEach((member) -> user.getRelationships().addRelationship(member.getId(), 1));
            if (user.getDiscordId().equals(message.getAuthor().getId())) {
                int score = user.getScore() + (int) Math.round((finishedAnalysis.getEmotion() + (conversationCache.size() * 10)) * Math.abs(emotion));
                user.setScore(score);
            }
            conversationCache.put(element.getId(), user);
        });
        conversationCache.forEach((key, value) -> {
            try {
                FlamesDataManager.save(value);
            } catch (IOException e) {
                Flames.incrementErrorCount();
                e.printStackTrace();
            }
        });
        conversationCache.clear();
    }
}
