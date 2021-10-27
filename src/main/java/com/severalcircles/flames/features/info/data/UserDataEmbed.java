/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.Emotion;
import com.severalcircles.flames.features.StringUtils;
import com.severalcircles.flames.features.external.severalcircles.FlamesAssets;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserDataEmbed implements FlamesEmbed {
    private User user;
    private FlamesUser flamesUser;
    // I'll fix this later lmao
    private ResourceBundle resources;
    public UserDataEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/UserDataEmbed", flamesUser.getConfig().getLocale());
    }


    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .addField(resources.getString("score"), StringUtils.formatScore(flamesUser.getScore()), true)
                .addField(resources.getString("rank"), Ranking.getResources(Locale.getDefault()).getString(Ranking.getRank(flamesUser.getScore()).toString()), true)
                .addField(resources.getString("toNext"), StringUtils.formatScore(Ranking.toNext(flamesUser.getScore())), true)
                .addField(resources.getString("emotion"), Emotion.getEmotionString(flamesUser.getEmotion(), Locale.getDefault()), true)
                .setDescription(String.format(resources.getString("level"), flamesUser.getStats().getLevel()))
                .setColor(new Color(153, 85,187))
                .setFooter(String.format(Flames.getCommonRsc(Locale.getDefault()).getString("userFooter"), user.getName(), Ranking.getResources(Locale.getDefault()).getString(Ranking.getRank(flamesUser.getScore()).toString())))
                .setThumbnail(FlamesAssets.getRankIcon(Ranking.getRank(flamesUser.getScore())))
                .build();
        return embed;
    }
}
