/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.today;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.Emotion;
import com.severalcircles.flames.features.StringUtils;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class TodayEmbed implements FlamesEmbed {
    private User user;
    private FlamesUser flamesUser;
    private static ResourceBundle resources = ResourceBundle.getBundle("features/TodayEmbed", Locale.getDefault());
    public TodayEmbed(User user, FlamesUser flamesUser) {
        this.flamesUser = flamesUser;
        this.user = user;
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
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(String.format(resources.getString("author"), StringUtils.prettifyDate(Instant.now())), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .addField(resources.getString("talkingAbout"), trendingEntity, true)
                .addField(resources.getString("feeling"), Emotion.getEmotionString(Today.emotion, Locale.getDefault()), true)
                .addBlankField(false)
                .addField("\"" + Today.quote[0] + "\"", "- " + Today.quote[1] + ", " + StringUtils.prettifyDate(Instant.now()), false)
                .addBlankField(false)
                .addField(resources.getString("allAbout"), resources.getString("tommorowBring"), false)
                .setFooter(String.format(Flames.getCommonRsc(Locale.getDefault()).getString("userFooter"), user.getName(), Ranking.getResources(Locale.getDefault()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                .setColor(Color.GREEN.darker())
                .build();
        return embed;
    }
}
