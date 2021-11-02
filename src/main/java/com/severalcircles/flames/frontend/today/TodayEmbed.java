/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.Analysis;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Emotion;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class TodayEmbed implements FlamesEmbed {
    private final User user;
    private final FlamesUser flamesUser;
    private static ResourceBundle resources;
    public TodayEmbed(User user, FlamesUser flamesUser) {
        this.flamesUser = flamesUser;
        this.user = user;
        resources = ResourceBundle.getBundle("features/TodayEmbed", flamesUser.getConfig().getLocale());
    }
    @Override
    public MessageEmbed get() {
        String trendingEntity = "";
        int times = 0;
        for (Map.Entry<String, Integer> entry: Analysis.entityCache.entrySet()) {
            if (!Objects.equals(entry.getKey(), trendingEntity) && entry.getValue() > times) {
                trendingEntity = entry.getKey();
                times = entry.getValue();
            }
        }
        return new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), StringUtils.prettifyDate(Instant.now())), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .addField(resources.getString("talkingAbout"), trendingEntity, true)
                .addField(resources.getString("feeling"), Emotion.getEmotionString(Today.emotion, flamesUser.getConfig().getLocale()), true)
                .addBlankField(false)
                .addField("\"" + Today.quote[0] + "\"", "- " + Today.quote[1] + ", " + StringUtils.prettifyDate(Instant.now()), false)
                .addBlankField(false)
                .addField(resources.getString("allAbout"), resources.getString("tomorrowBring"), false)
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                .setColor(Color.GREEN.darker())
                .build();
    }
}
