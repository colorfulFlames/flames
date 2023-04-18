/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.system.manager;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesQuote;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.Rank;
import com.severalcircles.flames.frontend.ConsentEmbed;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.flames.ConsentException;
import com.severalcircles.flames.system.exception.java.FlamesDataException;
import com.severalcircles.flames.system.reports.FlamesReport;
import com.severalcircles.flames.system.reports.NewFlamesUserReport;
import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.time.Instant;
import java.util.Locale;
import java.util.Properties;
@ExceptionID("912")
public class UserDataManager extends FlamesManager {
    private static final File userDataDir = new File(SystemDataManager.getFlamesDirectory().getAbsolutePath() + "/users");
    @Override
    public void prepare() {
        if (userDataDir.mkdir()) {
            Flames.getFlogger().config("Created user data directory at " + userDataDir.getAbsolutePath());
        }


    }
    public void saveUser(FlamesUser user) {
        Properties properties = new Properties();
        Flames.getFlogger().fine(user.toString());
        properties.setProperty("score", String.valueOf(user.getScore()));
        properties.setProperty("rank", String.valueOf(user.getRank()));
        properties.setProperty("highScore", String.valueOf(user.getHighScore()));
        properties.setProperty("lowScore", String.valueOf(user.getLowScore()));
        properties.setProperty("emotion", String.valueOf(user.getEmotion()));
        properties.setProperty("happyDay", String.valueOf(user.getHappyDay()));
        properties.setProperty("sadDay", String.valueOf(user.getSadDay()));
        properties.setProperty("favoriteQuote", String.valueOf(user.getFavoriteQuote()));
        properties.setProperty("locale", String.valueOf(user.getLocale()));
        properties.setProperty("consent", String.valueOf(user.getConsent()));
        properties.setProperty("lastBonus", String.valueOf(user.getLastBonus()));
        properties.setProperty("bonusMultiplier", String.valueOf(user.getBonusMultiplier()));
        Flames.getFlogger().fine(properties.toString());
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(userDataDir.getAbsolutePath() + "/" + user.getDiscordUser().getId() + ".flp");
            properties.store(os, "Flames User Data for " + user.getDiscordUser().getAsTag());
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public FlamesUser loadUser(User user, boolean overrideConsent) {
        Properties properties = new Properties();
        File userFile = new File(userDataDir.getAbsolutePath() + "/" + user.getId() + ".flp");
        try {
            if (userFile.createNewFile()) {
                Flames.getFlogger().fine("Created user data file for " + user.getAsTag() + " at " + userFile.getAbsolutePath());
                try {
                    FlamesReport nur = new NewFlamesUserReport(user);
                    nur.run();
                    FlamesReportManager.saveReport(nur);
                    Flames.getFlogger().finest("Consent Override: " + overrideConsent);
                    if (!overrideConsent) {
                        throw new ConsentException(0);
                    }
                } catch (FlamesDataException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConsentException e) {
            new ConsentEmbed(Locale.ROOT).sendTo(user);
        }
        try {
            properties.load(new FileInputStream(userFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FlamesUser fluser;
        try { fluser = new FlamesUser(
                user,
                Double.parseDouble(properties.getProperty("score")),
                Rank.valueOf(properties.getProperty("rank")),
                Double.parseDouble(properties.getProperty("highScore")),
                Double.parseDouble(properties.getProperty("lowScore")),
                Double.parseDouble(properties.getProperty("emotion")),
                Instant.parse(properties.getProperty("happyDay")),
                Instant.parse(properties.getProperty("sadDay")),
                null,
                Locale.forLanguageTag(properties.getProperty("locale")),
                Integer.parseInt(properties.getProperty("consent")),
                Boolean.parseBoolean(properties.getProperty("quoteConsent")),
                Instant.parse(properties.getProperty("lastBonus")),Double.parseDouble(properties.getProperty("bonusMultiplier")));
        fluser.setFavoriteQuote(FlamesQuote.valueOf(properties.getProperty("favoriteQuote"), fluser));}
        catch (NullPointerException e) {
            fluser = dataDefault(user);
            saveUser(fluser);
            try {
                throw new ConsentException(fluser.getConsent());
            } catch (ConsentException ex) {
                new ConsentEmbed(Locale.ROOT).sendTo(user);
            }
        }
        return fluser;
    }
    public static FlamesUser flames() {
        return new FlamesUser(
                Flames.getApi().getSelfUser(),
                0,
                Rank.UNRANKED,
                0,
                0,
                0,
                Instant.now(),
                Instant.now(),
                null,
                Locale.forLanguageTag("en-US"),
                1,
                true,
                Instant.now(), 1);
    }
    public static FlamesUser dataDefault(User user) {
        return new FlamesUser(
                user,
                0,
                Rank.UNRANKED,
                0,
                0,
                0,
                Instant.now(),
                Instant.now(),
                null,
                Locale.forLanguageTag("en-US"),
                1,
                false,
                Instant.now(),1);
    }
}
