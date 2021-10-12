package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesData;

import java.time.Instant;
import java.util.Properties;

/**
 * Object used to represent a user in reference to their Flames Data
 */
public class FlamesUser implements FlamesData {

    public static final double latestVersion = 2.0;

    private int score;
    private float emotion;
    private Instant lastSeen;
    private int streak;
    private String discordId;
    private UserStats stats;
    private int consent;
    private UserFunFacts funFacts;
    private double dataVersion = 2.0;

    public UserFunFacts getFunFacts() {
        return funFacts;
    }

    /**
     *
     * @return Properties data that can then be written to a file or returned by the Flams API
     */
    public Properties createData() {
        Properties data = new Properties();
        data.put("score", score + "");
        data.put("emotion", emotion + "");
        data.put("lastSeen", lastSeen.toString());
        data.put("streak", streak + "");
        data.put("discordId", discordId);
        data.put("consent", consent + "");
        data.put("version", dataVersion + "");
        return data;
    }

    public void setFunFacts(UserFunFacts funFacts) {
        this.funFacts = funFacts;
    }

    public void setDataVersion(double dataVersion) {
        this.dataVersion = dataVersion;
    }

    public FlamesUser(int score, float emotion, Instant lastSeen, int streak, String discordId, UserStats stats, int consent, UserFunFacts funFacts, double dataVersion) {
        this.score = score;
        this.emotion = emotion;
        this.streak = streak;
        this.discordId = discordId;
        this.stats = stats;
        this.consent = consent;
        this.funFacts = funFacts;
        this.dataVersion = dataVersion;
        this.lastSeen = lastSeen;
    }
    public UserStats getStats() {
        return stats;
    }

    public void setStats(UserStats stats) {
        this.stats = stats;
    }

    public int getConsent() {
        return consent;
    }

    public void setConsent(int consent) {
        this.consent = consent;
    }

    public FlamesUser() {
        this.score = 0;
        this.emotion = 0;
        this.lastSeen = Instant.now();
        this.streak = 0;
        this.stats = new UserStats();
        this.consent = 0;
        this.funFacts = new UserFunFacts();
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
