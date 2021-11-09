/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Ranking;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.ResourceBundle;

public class StatsEmbed implements FlamesEmbed {
    private FlamesUser flamesUser;
    private User user;
    private ResourceBundle resources;

    public StatsEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        resources = ResourceBundle.getBundle("features/data/StatsEmbed", flamesUser.getConfig().getLocale());
    }

    @Override
    public MessageEmbed get() {
        UserStats stats = flamesUser.getStats();
        int next = 2^stats.getLevel() + GlobalData.participants;
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getName()), "https://flamesapi.severalcircles.com/user/" + user.getId() + "/stats")
                .setDescription(String.format(resources.getString("description"), stats.getLevel()))
                .addField(resources.getString("exp"), stats.getExp() + "", true)
                .addField(resources.getString("tonext"), String.valueOf(Math.abs(next - stats.getExp())), true)
                .addField(resources.getString("pow"), stats.getPOW() + "", true)
                .addField(resources.getString("res"), stats.getRES() + "", true)
                .addField(resources.getString("luck"), stats.getLUCK() + "", true)
                .addField(resources.getString("rise"), stats.getRISE() + "", true)
                .addField(resources.getString("car"), stats.getCAR() + "", true)
                .setColor(new Color(153, 85,187))
                .setTimestamp(Instant.now())
                .setFooter(String.format(Flames.getCommonRsc(flamesUser.getConfig().getLocale()).getString("userFooter"), user.getName(), Ranking.getResources(flamesUser.getConfig().getLocale()).getString(String.valueOf(Ranking.getRank(flamesUser.getScore())))), user.getAvatarUrl())
                .build();
        return embed;
    }
}
