/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.legacy.user.UserFunFacts;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.ResourceBundle;

public class FunFactsEmbed implements FlamesEmbed {
    final ResourceBundle resources;
    final LegacyFlamesUser legacyFlamesUser;
    final User user;
    public FunFactsEmbed(User user, LegacyFlamesUser legacyFlamesUser) {
        this.legacyFlamesUser = legacyFlamesUser;
        this.user = user;
        this.resources = ResourceBundle.getBundle("features/data/FunFactsEmbed", legacyFlamesUser.getConfig().getLocale());
    }
    public MessageEmbed get() {
        UserFunFacts funFacts = legacyFlamesUser.getFunFacts();
        return new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setColor(new Color(153, 85,187))
                .setTitle(String.format(resources.getString("title"), user.getName()))
                .setDescription(String.format(resources.getString("description"), funFacts.getFavoriteQuote()))
                .addField(resources.getString("happyDay"), StringUtil.prettifyDate(funFacts.getHappyDay()), true)
                .addField(resources.getString("sadDay"), StringUtil.prettifyDate(funFacts.getSadDay()), true)
                .addField(resources.getString("highScore"), StringUtil.formatScore(funFacts.getHighestFlamesScore()), true)
                .addField(resources.getString("lowScore"), StringUtil.formatScore(funFacts.getLowestFlamesScore()), true)
                .setTimestamp(Instant.now())
                .setFooter(String.format(Flames.getCommonRsc(legacyFlamesUser.getConfig().getLocale()).getString("scoreFormat"), legacyFlamesUser.getScore()), user.getAvatarUrl())
                .build();
    }
}
