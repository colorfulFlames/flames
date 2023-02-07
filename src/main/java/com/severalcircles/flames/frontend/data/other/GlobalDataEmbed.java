/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.other;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.util.Ranking;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import com.severalcircles.flames.util.StringUtil;
import com.severalcircles.flames.frontend.FlamesEmbed;

import java.awt.*;
import java.util.ResourceBundle;

public class GlobalDataEmbed implements FlamesEmbed {
    private final User user;
    private final FlamesUser flamesUser;
    // I'll fix this later lmao
    private final ResourceBundle resources;
    public GlobalDataEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/data/GlobalDataEmbed", flamesUser.getConfig().getLocale());
    }


    public MessageEmbed get() {
        return new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), Flames.api.getSelfUser().getName()))
                .addField(resources.getString("globalScore"), StringUtil.formatScore(GlobalData.globalScore), true)
                .addField(resources.getString("averageScore"), StringUtil.formatScore(GlobalData.averageScore), true)
                .setColor(Color.decode("#DBC2CF"))
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank(flamesUser.getScore()).toString())), user.getAvatarUrl())
                .build();
    }
}
