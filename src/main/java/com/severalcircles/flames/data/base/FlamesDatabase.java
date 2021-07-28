package com.severalcircles.flames.data.base;

import com.severalcircles.flames.Main;
import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.errors.FlamesException;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Guild;

import java.sql.*;
import java.util.Queue;

public class FlamesDatabase {
    private Connection connection;
    public FlamesDatabase() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connection = DriverManager.getConnection("jdbc:mysql://localhost:33036/flames", "flames", "lightitup");
    }
    public void write(FlamesUser user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs;
        if (!(user instanceof FlamesUser) || user.equals(new FlamesUser())) rs = statement.executeQuery("insert into users (score, firstSeen, emotion, multiplier, lastSeen, streak, discordId) values (" + user.getScore() + ", " + user.getFirstSeen() + ", " + user.getEmotion() + ", " + user.getLastSeen() + ", " + user.getStreak() + ", " + user.getDiscordId() + ");");
        else rs = statement.executeQuery("update users\nSET score=" + user.getScore() + ", firstSeen=" + user.getFirstSeen() + ", emotion=" + user.getEmotion() + ", multiplier=" + user.getMultiplier() + ", lastSeen=" + user.getLastSeen() + ", streak=" + user.getStreak() + ", discordId=" + user.getDiscordId() + ", locale=" + user.getLocale() + ", stats=" + user.getStats() + "\nwhere discordId=" + user.getDiscordId());
    }
    public void write(FlamesGuild guild) {
        //TODO
    }

    public FlamesUser readUser(Snowflake discordId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from users where discordId = " + discordId);
        while(rs.next()) {
            return new FlamesUser(rs.getInt("score"), rs.getString("firstSeen"), rs.getInt("emotion"), rs.getFloat("multiplier"), rs.getDate("lastSeen"), rs.getInt("streak"), rs.getString("discordId"), rs.getString("locale"), rs.getObject("stats", UserStats.class));
        }
        return new FlamesUser();
    }
    public FlamesGuild readGuild(Snowflake discordId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from users where discordId = " + discordId);
        while(rs.next()) {
            return new FlamesGuild(rs.getString("discordId"), rs.getString("name"), rs.getInt("favorites"), rs.getString("welcomeMessage"), rs.getBoolean("debug"));
        }
        return new FlamesGuild(Main.client.getGuildById(discordId));
    }

    public void close() throws SQLException {
        connection.close();
    }
}
