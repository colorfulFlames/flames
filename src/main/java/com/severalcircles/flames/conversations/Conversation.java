/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.data.user.FlamesQuote;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.UserDataManager;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A conversation is a collection of messages that are analyzed for sentiment and emotion. It lasts for 5 minutes, and then its score is divided among the participants.
 * @author Several Circles
 * @version 8
 * @since Flames 6
 */
public class Conversation {
    private double emotion;
    private double score;
    private final Map<String, Integer> topics;
    private final Map<FlamesUser, Integer> participationMap;
    private final Instant started;
    private Instant expires;
    private final MessageChannelUnion channel;
    private FlamesQuote favoriteQuote;
    private final double highScore = 0;

    public Conversation(MessageChannelUnion channel) {
        this.emotion = 0;
        this.score = 0;
        topics = new HashMap<>();
        participationMap = new HashMap<>();
        started = Instant.now();
        expires = started.plusSeconds(60 * 5);
        this.channel = channel;
        this.favoriteQuote = new FlamesQuote("This isn't epic yet.", UserDataManager.flames());

    }

    public double getEmotion() {
        return emotion;
    }

    public void addEmotion(double emotion) {
        this.emotion += emotion;
    }

    public double getScore() {
        return score;
    }

    public void addScore(double score) {
        this.score += score;
    }

    public Map<String, Integer> getTopics() {
        return topics;
    }

    public void addTopic(String topic) {
        if (topics.containsKey(topic)) {
            topics.put(topic, topics.get(topic) + 1);
        } else {
            topics.put(topic, 1);
        }
    }
    public void addTopics(List<String> topics) {
        topics.forEach(this::addTopic);
    }

    public Map<FlamesUser, Integer> getParticipationMap() {
        return participationMap;
    }

    public void addParticipation(FlamesUser user) {
        if (participationMap.containsKey(user)) {
            participationMap.put(user, participationMap.get(user) + 1);
        } else {
            user.addConversation();
            participationMap.put(user, 1);
            new UserDataManager().saveUser(user);
        }
    }
    public Instant getStarted() {
        return started;
    }
    public Instant getExpires() {
        return expires;
    }
    public void updateExpiry() {
        this.expires = Instant.now().plusSeconds(60 * 5);
    }

    public MessageChannelUnion getChannel() {
        return channel;
    }

    public FlamesQuote getFavoriteQuote() {
        return favoriteQuote;
    }

    public double getHighScore() {
        return highScore;
    }

    public void updateFavoriteQuote(FlamesQuote favoriteQuote, double score) {
        if (Math.abs(score) > highScore) {
            this.favoriteQuote = favoriteQuote;
        }
//        this.favoriteQuote = favoriteQuote;
    }
}
