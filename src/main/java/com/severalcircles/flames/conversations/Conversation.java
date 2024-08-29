/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.conversations;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.FlamesServer;
import com.severalcircles.flames.data.legacy.user.consent.Consent;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserEntities;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.external.analysis.Analysis;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.GuildMessageChannel;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conversation {
    public static final int SCORE_BASE_MULTIPLIER = 7;
    public static final int HOOTENANNY_BONUS = 2;
    public static final int NEGATIVE_MULTIPLIER = 1;
    public static final List<String> entityList = new LinkedList<>();
    private final Map<String, Integer> entities;
    private final GuildMessageChannel channel;
    private final List<User> userList;
    private Instant expires;
    private double emotion;
    private String[] quote = {"This isn't epic yet", "Flames"};
    private double quoteScore;
    private final Map<String, FlamesUser> conversationCache;
    /**
     * Represents a conversation between users in a guild message channel.
     *
     * @param channel The guild message channel where the conversation is taking place.
     */
    public Conversation(GuildMessageChannel channel) {
        this.channel = channel;
        this.entities = new HashMap<>();
        this.userList = new LinkedList<>();
        this.expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        this.emotion = 0;
        this.quoteScore = 0;
        this.conversationCache = new HashMap<>();
    }

    /**
     * Represents whether the conversation is expired or not.
     *
     * <p>
     * The {@code expired} variable is a boolean variable that indicates whether the conversation
     * has expired or not. If the value is {@code true}, it means the conversation has expired.
     * Otherwise, if the value is {@code false}, it means the conversation is still active.
     * </p>
     *
     * <p>
     * This variable is used in the {@code Conversation} class to track the expiration status of a conversation.
     * It is protected, which means it can be accessed by subclasses within the same package or in other packages
     * that extend the {@code Conversation} class.
     * </p>
     *
     * @see Conversation
     */
    protected boolean expired = false;

    /**
     * Returns the entities map.
     * The map contains the entities and their corresponding count.
     *
     * @return the entities map
     */
    public Map<String, Integer> getEntities() {
        return entities;
    }

    /**
     *
     */
    public GuildMessageChannel getChannel() {
        return channel;
    }

    /**
     * Retrieves the list of users associated with this conversation.
     *
     * @return The list of users.
     */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Returns the expiration date and time of the conversation.
     *
     * @return The expiration date and time as an Instant object.
     */
    public Instant getExpires() {
        return expires;
    }

    /**
     * Sets the expiration time for the conversation.
     *
     * @param expires The new expiration time for the conversation.
     */
    public void setExpires(Instant expires) {
        this.expires = expires;
    }

    /**
     * Returns the emotion value of the conversation.
     *
     * @return The emotion value as a double.
     */
    public double getEmotion() {
        return emotion;
    }

    /**
     * Sets the emotion value of the Conversation.
     *
     * @param emotion the emotion value to set
     */
    public void setEmotion(double emotion) {
        this.emotion = emotion;
    }

    /**
     * Retrieves the quote stored in the conversation.
     *
     * @return an array containing the quote. The first element is the quote text and the second element is the attribution.
     */
    public String[] getQuote() {
        return quote;
    }

    /**
     * Sets the quote for the Conversation object.
     *
     * @param quote an array of strings representing the quote to be set
     */
    public void setQuote(String[] quote) {
        this.quote = quote;
    }

    /**
     * Retrieves the score of the quote.
     *
     * @return The score of the quote.
     */
    public double getQuoteScore() {
        return quoteScore;
    }

    /**
     * Set the score of the quote.
     *
     * @param quoteScore the score of the quote to be set
     */
    public void setQuoteScore(double quoteScore) {
        this.quoteScore = quoteScore;
    }

    /**
     * Retrieves the conversation cache.
     *
     * @return The conversation cache, represented as a map with keys of type String and values of type FlamesUser.
     */
    public Map<String, FlamesUser> getConversationCache() {
        return conversationCache;
    }

    /**
     * Checks if the conversation is expired.
     *
     * @return true if the conversation is expired, false otherwise
     */
    public boolean isExpired() {
        return expired;
    }

    /**
     * Sets the expiration status of the conversation.
     *
     * @param expired the new expiration status
     */
    public void setExpired(boolean expired) {
        this.expired = expired;
    }

//    public void processMessage(Message message, FinishedAnalysis finishedAnalysis) throws ExpiredConversationException {
//        Logger.getGlobal().log(Level.INFO, "Processing Message");
//        finishedAnalysis.getEntityList().forEach((element) -> {
//            FlamesUser user;
//            try {
//                user = FlamesDataManager.getUser(message.getAuthor().getId());
//            } catch (ConsentException e) {
//                Logger.getGlobal().log(Level.INFO, "User has not consented to Flames");
//                Consent.getConsent(message.getAuthor());
//                return;
//            } catch (IOException prettyMuchIgnored) {
//                prettyMuchIgnored.printStackTrace();
//                return;
//            }
//            UserEntities userEntities = user.getEntities();
//        });
//        if (Instant.now().isAfter(expires)) {
//            expired = true;
//            throw new ExpiredConversationException();
//        }
//        this.expires = Instant.now().plus(5, ChronoUnit.MINUTES);
//        System.out.println(userList.toString());
//        userList.forEach((element) -> {
//            if (!conversationCache.containsKey(element.getId())) {
//                try {
//                    conversationCache.put(element.getId(), FlamesDataManager.getUser(element.getId()));
//                } catch (ConsentException | IOException ignored) {}
//            }
//            FlamesUser user = conversationCache.get(element.getId());
//            conversationCache.put(element.getId(), user);;
//            user.addScore(score(finishedAnalysis, Objects.requireNonNull(FlamesDataManager.getServer(channel.getGuild().getId())).todayIsHootenannyDay()));
//            try {
//                FlamesDataManager.saveUser(user);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            conversationCache.forEach((key, value) -> {
//                try {
//                    FlamesDataManager.saveUser(value);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        });
//    }
    public void processMessage(User user, FinishedAnalysis finishedAnalysis) throws ExpiredConversationException {
        FlamesUser flamesUser;
        Logger.getGlobal().log(Level.INFO, "Processing Message");
        try {
            flamesUser = FlamesDataManager.getUser(user.getId());
        } catch (ConsentException e) {
            Logger.getGlobal().log(Level.INFO, "User has not consented to Flames");
            Consent.getConsent(user);
            e.printStackTrace();
            return;
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to get Flames Data");
            return;
        }
        if (Instant.now().isAfter(expires)) {
            expired = true;
            throw new ExpiredConversationException();
        }
        this.expires = Instant.now().plus(5, ChronoUnit.MINUTES);
        userList.add(user);
        if (!conversationCache.containsKey(user.getId())) {
            try {
                conversationCache.put(user.getId(), FlamesDataManager.getUser(user.getId()));
            } catch (ConsentException | IOException ignored) {}
        }
        flamesUser.addScore(score(finishedAnalysis, Objects.requireNonNull(FlamesDataManager.getServer(channel.getGuild().getId())).todayIsHootenannyDay()));
        finishedAnalysis.getEntityList().forEach((element) -> {
            if (entityList.contains(element)) {
                entities.put(element.getName(), entities.get(element) + 1);
            } else {
                entityList.add(element.getName());
                entities.put(element.getName(), 1);
            }
        });
        FlamesServer flamesServer = FlamesDataManager.getServer(channel.getGuild().getId());
        assert flamesServer != null;
        flamesServer.addScore(score(finishedAnalysis, flamesServer.todayIsHootenannyDay()));
        try {
            FlamesDataManager.saveUser(flamesUser);
            System.out.println(flamesServer.getScore());
            FlamesDataManager.saveServer(flamesServer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public int score(FinishedAnalysis fA, boolean hootenanny) {
        int score = 0;
        score += (int) (fA.getEmotion() * SCORE_BASE_MULTIPLIER);
        if (score <= 0) score *= NEGATIVE_MULTIPLIER;
        if (hootenanny) score *= HOOTENANNY_BONUS;
        score *= userList.size();
        return score;
    }
}
