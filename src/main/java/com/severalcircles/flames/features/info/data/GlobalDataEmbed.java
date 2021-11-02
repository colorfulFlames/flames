/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.data;

import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.StringUtils;
import com.severalcircles.flames.features.external.severalcircles.FlamesAssets;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ResourceBundle;

public class GlobalDataEmbed implements FlamesEmbed {
    private User user;
    private FlamesUser flamesUser;
    // I'll fix this later lmao
    private ResourceBundle resources;
    public GlobalDataEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/data/GlobalDataEmbed", flamesUser.getConfig().getLocale());
    }


    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), Flames.api.getSelfUser().getName()))
                .addField(resources.getString("globalScore"), StringUtils.formatScore(GlobalData.globalScore), true)
                .addField(resources.getString("averageScore"), StringUtils.formatScore(GlobalData.averageScore), true)
                .setColor(new Color(15, 131, 217))
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank(flamesUser.getScore()).toString())))
                .build();
        return embed;
    }
}
