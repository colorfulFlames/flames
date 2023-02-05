/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.frontend.today.Today;
import net.dv8tion.jda.api.entities.Message;
//import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
//import net.dv8tion.jda.api.entitiesGuildMessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

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
    private final Map<String, Integer> entities;
    private final GuildMessageChannel channel;
    private final List<User> userList;
    private Instant expires;
    private double emotion;
    private String[] quote = {"This isn't epic yet", "Flames"};
    private double quoteScore;
    private final Map<String, FlamesUser> conversationCache;
    public Conversation (GuildMessageChannel channel) {
        this.channel = channel;
        this.entities = new HashMap<>();
        this.userList = new LinkedList<>();
        this.expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        this.emotion = 0;
        this.quoteScore = 0;
        this.conversationCache = new HashMap<>();
    }

    public Map<String, Integer> getEntities() {
        return entities;
    }

    public GuildMessageChannel getChannel() {
        return channel;
    }

    public double getEmotion() {
        return emotion;
    }

    public String[] getQuote() {
        return quote;
    }

    public void processMessage(Message message, FinishedAnalysis finishedAnalysis) throws ExpiredConversationException {
        boolean newFavorite = false;
        Logger.getGlobal().log(Level.INFO, "Processing Message");
        if (expires.compareTo(Instant.now()) < 0) throw new ExpiredConversationException();
        expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        emotion += finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude();
        if (finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude() > quoteScore) {
            this.quote = new String[]{message.getContentRaw(), message.getAuthor().getName()};
            this.quoteScore = finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude();
            if (Math.round(Math.random() * 10) == 6) newFavorite = true;
        }
        if (!userList.contains(message.getAuthor())) userList.add(message.getAuthor());
        finishedAnalysis.getEntityList().forEach((element) -> {
            if (!entities.containsKey(element.getName())) entities.put(element.getName(), 1);
            else entities.put(element.getName(), entities.get(element.getName()) + 1);
        });
        boolean finalNewFavorite = newFavorite;
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
                if (finalNewFavorite | user.getFunFacts().getFavoriteQuote().equals("I haven't said anything epic yet.")) user.getFunFacts().setFavoriteQuote(message.getContentRaw());
                int score = (int) Math.round((finishedAnalysis.getEmotion() + (conversationCache.size() * 10)) * emotion);
                user.setScore(user.getScore() + score);
                user.setEmotion(user.getEmotion() + (float) emotion);
                if (user.getEmotion() > user.getFunFacts().getHighestEmotion()) {
                    user.getFunFacts().setHighestEmotion(user.getEmotion());
                    user.getFunFacts().setHappyDay(Instant.now());
                }
                if (user.getEmotion() < user.getFunFacts().getLowestEmotion()) {
                    user.getFunFacts().setLowestEmotion(user.getEmotion());
                    user.getFunFacts().setSadDay(Instant.now());
                }
                if (user.getScore() > user.getFunFacts().getHighestFlamesScore()) user.getFunFacts().setHighestFlamesScore(user.getScore());
                GlobalData.globalScore += score;
                GlobalData.averageScore = GlobalData.globalScore / GlobalData.participants;
                if (user.getScore() > Today.highScore) {
                    Today.highUser = message.getAuthor().getName() + " (" + user.getScore() + ")";
                }
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
