/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.features.info.data;

import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.StringUtils;
import com.severalcircles.flames.features.info.FlamesEmbed;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ResourceBundle;

public class GuildDataEmbed implements FlamesEmbed {
    private User user;
    private FlamesUser flamesUser;
    private FlamesGuild guild;
    private Guild guild1;
    // I'll fix this later lmao
    private ResourceBundle resources;
    public GuildDataEmbed(User user, FlamesUser flamesUser, FlamesGuild guild, Guild guild1) {
        this.user = user;
        this.flamesUser = flamesUser;
        this.guild = guild;
        this.guild1 = guild1;
        resources = ResourceBundle.getBundle("features/data/GuildDataEmbed", flamesUser.getConfig().getLocale());
    }


    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), guild1.getName()))
                .addField(resources.getString("score"), StringUtils.formatScore(guild.getFlamesScore()), true)
                .addField(resources.getString("rank"), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank((int) guild.getFlamesScore() / guild1.getMemberCount()).toString()), true)
                .setColor(Color.magenta.darker())
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(Ranking.getRank(flamesUser.getScore()).toString())))
                .build();
        return embed;
    }
}
