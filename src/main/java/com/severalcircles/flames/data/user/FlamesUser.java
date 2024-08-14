/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesDatatype;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class FlamesUser extends FlamesDatatype {
    // Core values
    private String id;
    private int score;
    private int consent;
    private int streak;
    private float emotion;

    // Fun Facts
    private Date lastSeen;
    private Date sadDay;
    private float lowestEmotion;
    private Date happyDay;
    private float highestEmotion;
    private String favoriteQuote;

    // Config
    private String lang;
    private boolean quoteConsent;

    // Entities
    private UserEntities entities;
    FlamesUser() {
        id = null;
    }

    public FlamesUser(String id) {
        this.id = id;
        this.score = 0;
        this.consent = 0;
        this.streak = 0;
        this.emotion = 0;
        this.lastSeen = Date.from(Instant.now());
        this.sadDay = Date.from(Instant.now());
        this.lowestEmotion = 0;
        this.happyDay = Date.from(Instant.now());
        this.highestEmotion = 0;
        this.favoriteQuote = "This is me.";
        this.lang = Locale.ENGLISH.toLanguageTag();
        this.quoteConsent = false;
        this.entities = new UserEntities();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public FlamesUser(String id, int score, int consent, int streak, float emotion, Date lastSeen, Date sadDay, float lowestEmotion, Date happyDay, float highestEmotion, String favoriteQuote, String lang, boolean quoteConsent, UserEntities entities) {
        this.id = id;
        this.score = score;
        this.consent = consent;
        this.streak = streak;
        this.emotion = emotion;
        this.lastSeen = lastSeen;
        this.sadDay = sadDay;
        this.lowestEmotion = lowestEmotion;
        this.happyDay = happyDay;
        this.highestEmotion = highestEmotion;
        this.favoriteQuote = favoriteQuote;
        this.lang = lang;
        this.quoteConsent = quoteConsent;
        this.entities = entities;
    }

    public String getId() {
        checkId();
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getScore() {
        checkId();
        return score;
    }

    public int getConsent() {
        checkId();
        return consent;
    }

    public int getStreak() {
        checkId();
        return streak;
    }

    public float getEmotion() {
        checkId();
        return emotion;
    }

    public Date getLastSeen() {
        checkId();
        return lastSeen;
    }

    public Date getSadDay() {
        checkId();
        return sadDay;
    }

    public float getLowestEmotion() {
        checkId();
        return lowestEmotion;
    }

    public Date getHappyDay() {
        checkId();
        return happyDay;
    }

    public float getHighestEmotion() {
        checkId();
        return highestEmotion;
    }

    public String getFavoriteQuote() {
        checkId();
        return favoriteQuote;
    }

    public boolean isQuoteConsent() {
        checkId();
        return quoteConsent;
    }

    public UserEntities getEntities() {
        checkId();
        return entities;
    }

    public void setScore(int score) {
        checkId();
        this.score = score;
    }

    public void setConsent(int consent) {
        checkId();
        this.consent = consent;
    }

    public void setStreak(int streak) {
        checkId();
        this.streak = streak;
    }

    public void setEmotion(float emotion) {
        checkId();
        this.emotion = emotion;
    }

    public void setLastSeen(Date lastSeen) {
        checkId();
        this.lastSeen = lastSeen;
    }

    public void setSadDay(Date sadDay) {
        checkId();
        this.sadDay = sadDay;
    }

    public void setLowestEmotion(float lowestEmotion) {
        checkId();
        this.lowestEmotion = lowestEmotion;
    }

    public void setHappyDay(Date happyDay) {
        checkId();
        this.happyDay = happyDay;
    }

    public void setHighestEmotion(float highestEmotion) {
        checkId();
        this.highestEmotion = highestEmotion;
    }

    public void setFavoriteQuote(String favoriteQuote) {
        checkId();
        this.favoriteQuote = favoriteQuote;
    }

    public void setQuoteConsent(boolean quoteConsent) {
        checkId();
        this.quoteConsent = quoteConsent;
    }

    public void setEntities(UserEntities entities) {
        checkId();
        this.entities = entities;
    }

    private void checkId() {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalStateException("Not in my house! (Please provide an ID)");
        }
    }

    @Override
    public String getID() {
        return getId();
    }
}
