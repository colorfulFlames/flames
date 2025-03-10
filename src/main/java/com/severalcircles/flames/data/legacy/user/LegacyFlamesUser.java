/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.legacy.user;

import com.severalcircles.flames.data.user.UserEntities;

import java.time.Instant;
import java.util.Properties;

/**
 * Object used to represent a user in reference to their Flames Data
 */
@Deprecated
public class LegacyFlamesUser {

    private int score;
    private float emotion;
    private Instant lastSeen;
    private int streak;
    private String discordId;
    private int consent;
    private UserFunFacts funFacts;
    private double dataVersion;
    private UserRelationships relationships;
    private UserEntities entities;
    public UserRelationships getRelationships() {
        return relationships;
    }

    public void setRelationships(UserRelationships relationships) {
        this.relationships = relationships;
    }

    public UserConfig getConfig() {
        return config;
    }

    public void setConfig(UserConfig config) {
        this.config = config;
    }

    public UserEntities getEntities() {
        return entities;
    }

    public void setEntities(UserEntities entities) {
        this.entities = entities;
    }

    private UserConfig config;

    public UserFunFacts getFunFacts() {
        return funFacts;
    }

    /**
     *
     * @return Properties data that can then be written to a file
     */
    public Properties createData() {
        Properties data = new Properties();
        data.put("score", String.valueOf(score));
        data.put("emotion", String.valueOf(emotion));
        data.put("lastSeen", lastSeen.toString());
        data.put("streak", String.valueOf(streak));
        data.put("discordId", discordId);
        data.put("consent", String.valueOf(consent));
        data.put("version", String.valueOf(dataVersion));
        return data;
    }

    public void setFunFacts(UserFunFacts funFacts) {
        this.funFacts = funFacts;
    }

    public void setDataVersion(double dataVersion) {
        this.dataVersion = dataVersion;
    }

    public int getConsent() {
        return consent;
    }

    public void setConsent(int consent) {
        this.consent = consent;
    }

    public LegacyFlamesUser(String discordId) {
        this.score = 0;
        this.emotion = 0;
        this.lastSeen = Instant.now();
        this.streak = 0;
//        this.stats = new UserStats();
        this.consent = 0;
        this.funFacts = new UserFunFacts();
        this.config = new UserConfig();
        this.relationships = new UserRelationships();
        this.dataVersion = 2.2;
        this.entities = new UserEntities();
        this.discordId = discordId;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getEmotion() {
        return emotion;
    }

    public void setEmotion(float emotion) {
        this.emotion = emotion;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

}
