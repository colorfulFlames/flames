/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserEntities;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import com.severalcircles.flames.frontend.today.Today;
import net.dv8tion.jda.api.entities.Message;
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
    public static final List<String> entityList = new LinkedList<>();
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
    protected boolean expired = false;
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
            Logger.getGlobal().log(Level.FINE, "Processing Message");
            if (expires.compareTo(Instant.now()) < 0) {
                expired = true;
                throw new ExpiredConversationException();
            }

        expires = Instant.now().plus(5, ChronoUnit.MINUTES);
            emotion += finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude();
            if (finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude() > quoteScore | Math.round(Math.random() * 10) == 6) {
                this.quote = new String[]{message.getContentRaw(), message.getAuthor().getName()};
                this.quoteScore = finishedAnalysis.getSentiment().getScore() + finishedAnalysis.getSentiment().getMagnitude();
                if (Math.round(Math.random() * 10) == 6) newFavorite = true;
            }
            if (!userList.contains(message.getAuthor())) userList.add(message.getAuthor());
            finishedAnalysis.getEntityList().forEach((element) -> {
                if (!entities.containsKey(element.getName())) entities.put(element.getName(), 1);
                else entities.put(element.getName(), entities.get(element.getName()) + 1);
                entityList.add(element.getName());
                FlamesUser user;
                try {
                    user = FlamesDataManager.readUser(message.getAuthor());
                } catch (ConsentException e) {
                    return;
                } catch (Exception e) {
                    new ExceptionHandler(e).handle();
                    return;
                }
                UserEntities userEntities = user.getEntities();
                entities.forEach((key, value) -> {
                    userEntities.addEntity(key, finishedAnalysis.getSentiment().getScore() > 0);
                });
                user.setEntities(userEntities);
                user.getEntities().getEntities().forEach((key, value) -> {
                });
                try {
                    user.getEntities().getEntities().forEach((key, value) -> {
                        System.out.println(key + " / " + value);
                    });
                    FlamesDataManager.save(user);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            boolean finalNewFavorite = newFavorite;
            userList.forEach((element) -> {
                if (!conversationCache.containsKey(element.getId())) {
                    try {
                        conversationCache.put(element.getId(), FlamesDataManager.readUser(element));
                    } catch (ConsentException ignored) {
                        return;
                    } catch (Exception e) {
                        new ExceptionHandler(e).handle();
                    }
                    FlamesUser user = conversationCache.get(element.getId());
                    if (user == null) return;
                    userList.forEach((member) -> user.getRelationships().addRelationship(member.getId(), 1));
                    if (user.getDiscordId().equals(message.getAuthor().getId())) {
                        if ((finalNewFavorite | user.getFunFacts().getFavoriteQuote().equals("This is me.")) && user.getConfig().isFavQuoteAllowed())
                            user.getFunFacts().setFavoriteQuote(message.getContentRaw());
                        double score = Math.round(finishedAnalysis.getEmotion() * 15);
                        if (score < 0) score *= 5;
                        user.setScore(user.getScore() + (int) score);
                        user.setEmotion(user.getEmotion() + (float) emotion);
                        if (user.getEmotion() > user.getFunFacts().getHighestEmotion()) {
                            user.getFunFacts().setHighestEmotion(user.getEmotion());
                            user.getFunFacts().setHappyDay(Instant.now());
                        }
                        if (user.getEmotion() < user.getFunFacts().getLowestEmotion()) {
                            user.getFunFacts().setLowestEmotion(user.getEmotion());
                            user.getFunFacts().setSadDay(Instant.now());
                        }
                        if (user.getScore() > user.getFunFacts().getHighestFlamesScore())
                            user.getFunFacts().setHighestFlamesScore(user.getScore());
                        GlobalData.globalScore += score;
                        GlobalData.averageScore = GlobalData.globalScore / GlobalData.participants;
                        if (user.getScore() > Today.highScore) {
                            Today.highUser = message.getAuthor().getName() + " (" + user.getScore() + ")";
                        }
                    }
                    conversationCache.put(element.getId(), user);
                    try {
                        FlamesDataManager.save(user);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                conversationCache.forEach((key, value) -> {
                    try {
                        FlamesDataManager.save(value);
                    } catch (IOException e) {
                        Flames.incrementErrorCount();
                        e.printStackTrace();
                    }
                });
                conversationCache.clear();
            });
    }
}
