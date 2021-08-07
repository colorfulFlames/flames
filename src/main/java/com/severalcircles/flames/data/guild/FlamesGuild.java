package com.severalcircles.flames.data.guild;

import discord4j.core.object.entity.Guild;
import discord4j.rest.entity.RestGuild;
import reactor.util.annotation.Nullable;

public class FlamesGuild {
    private String name;
    private int favorites = 0;
    private String welcomeMessage;
//    private boolean debug = false;
    private String discordID;
    public FlamesGuild(String discordID, @Nullable String name, @Nullable int favorites, @Nullable String welcomeMessage) {
        this.name = name;
        this.favorites = favorites;
        this.welcomeMessage = welcomeMessage;
//        this.debug = debug;
        this.discordID = discordID;
    }
    public FlamesGuild(Guild guild) {
        this.name = guild.getName();
        this.favorites = 0;
        this.welcomeMessage = "We're so happy you're here!";
        this.discordID = guild.getId().toString();
//        this.debug = false;
    }

    public FlamesGuild(RestGuild guild) {
        this.name = guild.getData().block().name();
        this.favorites = 0;
        this.welcomeMessage = "We're so happy you're here!";
        this.discordID = guild.getId().toString();
    }

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

}
