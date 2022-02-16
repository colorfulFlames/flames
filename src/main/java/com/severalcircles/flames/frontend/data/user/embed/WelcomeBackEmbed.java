/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.today.Today;
import com.severalcircles.flames.util.Ranking;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import com.severalcircles.flames.util.StringUtil;
import com.severalcircles.flames.frontend.FlamesEmbed;

import java.awt.*;
import java.time.Instant;
import java.util.Date;
import java.util.ResourceBundle;

public class WelcomeBackEmbed implements FlamesEmbed {
    private static ResourceBundle resources;
    private final int dailyBonus;
    private final User user;
    private final FlamesUser flamesUser;

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
        //noinspection deprecation
        if (Today.isThanksgiving) timeMessage = resources.getString("author.thanksgiving");
        else if (now.getHours() < 6) timeMessage = resources.getString("author.earlymorning");
        else //noinspection deprecation
            if (now.getHours() < 12) timeMessage = resources.getString("author.morning");
        else //noinspection deprecation
            if (now.getHours() < 15) timeMessage = resources.getString("author.earlyafternoon");
        else //noinspection deprecation
                if (now.getHours() < 18) timeMessage = resources.getString("author.lateafternoon");
        else //noinspection deprecation
            if (now.getHours() < 21) timeMessage = resources.getString("author.evening");
        else timeMessage = resources.getString("author.night");
        return new EmbedBuilder()
                .setAuthor(String.format(timeMessage, user.getName()), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .addField(resources.getString("dailyBonus"), StringUtil.formatScore(dailyBonus), true)
                .addField(resources.getString("score"), StringUtil.formatScore(flamesUser.getScore()), true)
                .setDescription(resources.getString("description"))
                .setColor(Color.decode("#D9581C"))
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                .build();
    }
}
