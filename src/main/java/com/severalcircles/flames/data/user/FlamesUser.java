/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.flames.AlreadyCollectedException;
import com.severalcircles.flames.system.reports.FlamesReport;
import net.dv8tion.jda.api.entities.User;

import java.time.Instant;
import java.util.Locale;

public class FlamesUser {
    private final User discordUser;
    private double score;
    private Rank rank;
    private double highScore;
    private double lowScore;
    private double emotion;
    private Instant happyDay;
    private Instant sadDay;
    private FlamesQuote favoriteQuote;
    private Locale locale;
    private int consent;
    private boolean quoteConsent;
    private Instant lastBonus;
    private double bonusMultiplier;
    private int conversations;
    private int messages;

    public FlamesUser(User discordUser, double score, Rank rank, double highScore, double lowScore, double emotion, Instant happyDay, Instant sadDay, FlamesQuote favoriteQuote, Locale locale, int consent, boolean quoteConsent, Instant lastBonus, double bonusMultiplier, int conversations, int messages) {
        this.discordUser = discordUser;
        this.score = score;
        this.rank = rank;
        this.highScore = highScore;
        this.lowScore = lowScore;
        this.emotion = emotion;
        this.happyDay = happyDay;
        this.sadDay = sadDay;
        this.favoriteQuote = favoriteQuote;
        this.locale = locale;
        this.consent = consent;
        this.quoteConsent = quoteConsent;
        this.lastBonus = lastBonus;
        this.bonusMultiplier = bonusMultiplier;
        this.conversations = conversations;
        this.messages = messages;
    }

    public User getDiscordUser() {
        return discordUser;
    }

    public double getScore() {
        return score;
    }

    public Rank getRank() {
        this.rank = FlamesRank.getRank(score);
        Flames.getFlogger().finest("User " + discordUser.getName() + " has a rank of " + this.rank.toString());
        return this.rank;
    }

    public double getHighScore() {
        return highScore;
    }

    public double getLowScore() {
        return lowScore;
    }

    public double getEmotion() {
        return emotion;
    }

    public Instant getHappyDay() {
        return happyDay;
    }

    public Instant getSadDay() {
        return sadDay;
    }

    public FlamesQuote getFavoriteQuote() {
        return favoriteQuote;
    }

    public Locale getLocale() {
        return locale;
    }

    public int getConsent() {
        return consent;
    }
    public void addEmotion(double emotion) {
        this.emotion += emotion;
    }
    public void addScore(double score) {
        this.score += score;
    }

    public void setHighScore(double highScore) {
        this.highScore = highScore;
    }

    public void setLowScore(double lowScore) {
        this.lowScore = lowScore;
    }

    public void setHappyDay(Instant happyDay) {
        this.happyDay = happyDay;
    }

    public void setSadDay(Instant sadDay) {
        this.sadDay = sadDay;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setConsent(int consent) {
        this.consent = consent;
    }

    public void setFavoriteQuote(FlamesQuote favoriteQuote) {
        this.favoriteQuote = favoriteQuote;
    }

    public boolean gaveQuoteConsent() {
        return quoteConsent;
    }

    public void setQuoteConsent(boolean quoteConsent) {
        this.quoteConsent = quoteConsent;
    }

    public Instant getLastBonus() {
        return lastBonus;
    }

    public void setLastBonus(Instant lastBonus) {
        this.lastBonus = lastBonus;
    }
    public double collectBonus() throws AlreadyCollectedException {
        if (Instant.now().isBefore(lastBonus.plusSeconds(60 * 60 * 24 * 7))) throw new AlreadyCollectedException("You've already collected your bonus for this week!");
        double bonus = 100 * bonusMultiplier;
        bonusMultiplier += 0.1;
        lastBonus = Instant.now();
        return bonus;
    }
    public double getBonusMultiplier() {
        return bonusMultiplier;
    }
    public void addConversation() {
        conversations++;
    }
    public void addMessage() {
        messages++;
    }

    public int getConversations() {
        return conversations;
    }

    public int getMessages() {
        return messages;
    }
}
