/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.guild;

import com.severalcircles.flames.data.FlamesData;

import java.time.Instant;
import java.util.Properties;

public class FlamesGuild implements FlamesData {
    private String name;
    private String id;
    private Instant joined;
//    private List<GuildBan> banList = new LinkedList<>();
    private int flamesScore;
    private float emotion;

    public FlamesGuild(String name, String id, Instant joined, int flamesScore, float emotion) {
        this.name = name;
        this.id = id;
        this.joined = joined;
//        this.banList = banList;
        this.flamesScore = flamesScore;
        this.emotion = emotion;
    }
    public Properties createData() {
        Properties data = new Properties();
        data.put("name", name);
        data.put("discordId", id);
        data.put("joined", joined.toString());
//        data.put("bans", banList.size() + "");
        data.put("score", flamesScore + "");
        data.put("emotion", emotion + "");
        return data;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getJoined() {
        return joined;
    }

    public void setJoined(Instant joined) {
        this.joined = joined;
    }

//    public List<GuildBan> getBanList() {
//        return banList;
//    }
//
//    public void addBanToList(GuildBan ban) {
//        this.banList = banList;
//    }

    public int getFlamesScore() {
        return flamesScore;
    }

    public void setFlamesScore(int flamesScore) {
        this.flamesScore = flamesScore;
    }

    public float getEmotion() {
        return emotion;
    }

    public void setEmotion(float emotion) {
        this.emotion = emotion;
    }
}
