/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Emotion;
import com.severalcircles.flames.util.Ranking;
import com.severalcircles.flames.util.StringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ResourceBundle;

public class UserDataEmbed implements FlamesEmbed {
    private final User user;
    private final FlamesUser flamesUser;
    // I'll fix this later lmao
    private final ResourceBundle resources;
    public UserDataEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/data/UserDataEmbed", flamesUser.getConfig().getLocale());
    }


    @Override
    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getName()))
                .addField(resources.getString("score"), StringUtils.formatScore(flamesUser.getScore()), true)
                .addField(resources.getString("rank"), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank(flamesUser.getScore()).toString()), true)
                .addField(resources.getString("toNext"), StringUtils.formatScore(Ranking.toNext(flamesUser.getScore())), true)
                .addField(resources.getString("emotion"), Emotion.getEmotionString(flamesUser.getEmotion(), flamesUser.getConfig().getLocale()), true)
                .setDescription(String.format(resources.getString("level"), flamesUser.getStats().getLevel()))
                .setColor(new Color(153, 85,187))
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank(flamesUser.getScore()).toString()), user.getAvatarUrl()))
                .setThumbnail(FlamesAssets.getRankIcon(Ranking.getRank(flamesUser.getScore())))
                .build();
    }
}
