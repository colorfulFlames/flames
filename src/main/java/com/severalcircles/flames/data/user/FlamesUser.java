package com.severalcircles.flames.data.user;

public class FlamesUser {
    private int score;
    private String firstSeen;
    private float emotion;
    private int lastSeen;
    private int streak;
    private String discordId;
    private String locale;
    private UserStats stats;
    private int consent;
    private int guilds;
    private UserFunFacts funFacts;

    public UserFunFacts getFunFacts() {
        return funFacts;
    }

    public void setFunFacts(UserFunFacts funFacts) {
        this.funFacts = funFacts;
    }

    public boolean lowEmotionWarned = false;
public FlamesUser(int score, String firstSeen, float emotion, int lastSeen, int streak, String discordId, String locale, UserStats stats, int consent, int guilds, UserFunFacts funFacts) {
        this.score = score;
        this.firstSeen = firstSeen;
        this.emotion = emotion;
//        this.multiplier = multiplier;
        this.lastSeen = lastSeen;
        this.streak = streak;
        this.discordId = discordId;
        this.locale = locale;
        this.stats = stats;
//        this.exp = exp;
        this.consent = consent;
    this.guilds = guilds;
    this.funFacts = funFacts;
}
    public String getLocale() {
        return locale;
    }
    public UserStats getStats() {return stats;}

    public void setStats(UserStats stats) {this.stats = stats;}
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public int getConsent() {
        return consent;
    }

    public void setConsent(int consent) {
        this.consent = consent;
    }

    public FlamesUser() {
        this.score = 0;
        this.firstSeen = "ya mom"; //TODO
        this.emotion = 0;
//        this.multiplier = multiplier;
        this.lastSeen = -1;
        this.streak = 0;
        this.locale = "en";
        this.stats = new UserStats();
        this.consent = 0;
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

    public String getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(String firstSeen) {
        this.firstSeen = firstSeen;
    }

    public float getEmotion() {
        return emotion;
    }

    public void setEmotion(float emotion) {
        this.emotion = emotion;
    }

    public int getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(int lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public String getDiscordId() {
        return discordId;
    }

    public void setDiscordId(String discordId) {
        this.discordId = discordId;
    }

    public int getGuilds() {
        return guilds;
    }

    public void setGuilds(int guilds) {
        this.guilds = guilds;
    }
}
