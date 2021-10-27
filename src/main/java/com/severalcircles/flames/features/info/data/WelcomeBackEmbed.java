/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.StringUtils;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeBackEmbed implements FlamesEmbed {
    private static ResourceBundle resources;
    private int dailyBonus;
    private User user;
    private FlamesUser flamesUser;

    public WelcomeBackEmbed(int dailyBonus, User user, FlamesUser flamesUser) {
        this.dailyBonus = dailyBonus;
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/data/WelcomeBackEmbed", flamesUser.getConfig().getLocale());
    }
    @Override
    public MessageEmbed get() {
        Date now = Date.from(Instant.now());
        String timeMessage;
        if (now.getHours() < 6) timeMessage = resources.getString("author.earlymorning");
        else if (now.getHours() < 12) timeMessage = resources.getString("author.morning");
        else if (now.getHours() < 15) timeMessage = resources.getString("author.earlyafternoon");
        else if (now.getHours() < 18) timeMessage = resources.getString("author.lateafternoon");
        else if (now.getHours() < 21) timeMessage = resources.getString("author.evening");
        else timeMessage = resources.getString("author.night");
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(String.format(timeMessage, user.getName()), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .addField(resources.getString("dailyBonus"), StringUtils.formatScore(dailyBonus), true)
                .addField(resources.getString("score"), StringUtils.formatScore(flamesUser.getScore()), true)
                .setDescription(resources.getString("description"))
                .setColor(Color.ORANGE)
                .setFooter(String.format(Flames.getCommonRsc(Locale.getDefault()).getString("userFooter"), user.getName(), Ranking.getResources(Locale.getDefault()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                .build();
        return embed;
    }
}
