package com.severalcircles.flames.data.base;

import com.severalcircles.flames.system.Flames;
import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.system.OperationMode;
import discord4j.common.util.Snowflake;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class FlamesDatabase {
    private final Connection connection;
    private static final Map<String, FlamesUser> userCache = new HashMap<>();
    public FlamesDatabase() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connection = DriverManager.getConnection("jdbc:mysql://localhost:33036/flames", "flames", "lightitup");
    }
    public void write(FlamesUser user) throws SQLException {
        userCache.put(user.getDiscordId(), user);
    }
    public static void flushUserCache() throws SQLException {
        FlamesDatabase fd = new FlamesDatabase();
        for (Map.Entry<String, FlamesUser> entry: userCache.entrySet()) {
        Statement statement = fd.connection.createStatement();
        ResultSet rs;
        if (!(entry.getValue() instanceof FlamesUser) || entry.getValue().equals(new FlamesUser())) rs = statement.executeQuery("insert into users (score, firstSeen, emotion, multiplier, lastSeen, streak, discordId) values (" + entry.getValue().getScore() + ", " + entry.getValue().getFirstSeen() + ", " + entry.getValue().getEmotion() + ", " + entry.getValue().getLastSeen() + ", " + entry.getValue().getStreak() + ", " + entry.getValue().getDiscordId() + ");");
        else rs = statement.executeQuery("update users\nSET score=" + entry.getValue().getScore() + ", firstSeen=" + entry.getValue().getFirstSeen() + ", emotion=" + entry.getValue().getEmotion() + ", lastSeen=" + entry.getValue().getLastSeen() + ", streak=" + entry.getValue().getStreak() + ", discordId=" + entry.getValue().getDiscordId() + ", locale=" + entry.getValue().getLocale() + ", exp=" + entry.getValue().getStats().getExp() + ", level=" + entry.getValue().getStats().getLevel() + ", POW=" + entry.getValue().getStats().getPOW() + ", RES=" + entry.getValue().getStats().getRES() + ", LUCK=" + entry.getValue().getStats().getLUCK() + ", RISE=" + entry.getValue().getStats().getRISE() + ", PTY=" + entry.getValue().getStats().getPTY() + ", SEN=" + entry.getValue().getStats().getSEN() + ", CAR=" + entry.getValue().getStats().getCAR() +"\nwhere discordId=" + entry.getValue().getDiscordId());
    }
        fd.close();
    }
    public void write(FlamesGuild guild) {
        //TODO
    }

    public FlamesUser readUser(Snowflake discordId) throws SQLException {
        if (userCache.containsKey(discordId.asString())) return userCache.get(discordId);
        else {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users where discordId = " + discordId);
            while (rs.next()) {
                UserStats stats = new UserStats(rs.getInt("exp"), rs.getInt("level"), rs.getInt("POW"), rs.getInt("RES"), rs.getInt("LUCK"), rs.getInt("RISE"), rs.getInt("PTY"), rs.getInt("SEN"), rs.getInt("CAR"));
                FlamesUser user = new FlamesUser(rs.getInt("score"), rs.getString("firstSeen"), rs.getInt("emotion"), rs.getDate("lastSeen"), rs.getInt("streak"), rs.getString("discordId"), rs.getString("locale"), stats);
                return userCache.put(user.getDiscordId(), user);
            }
        }
        return new FlamesUser();
    }
    public FlamesGuild readGuild(Snowflake discordId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("select * from users where discordId = " + discordId);
        while(rs.next()) {
            return new FlamesGuild(rs.getString("discordId"), rs.getString("name"), rs.getInt("favorites"), rs.getString("welcomeMessage"), rs.getBoolean("debug"));
        }
        return new FlamesGuild(Flames.client.getGuildById(discordId));
    }

    public void close() throws SQLException {
        connection.close();
    }
}
