/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.other;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.server.FlamesServer;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.awt.*;
import java.util.*;

public class ServerDataEmbed implements FlamesEmbed {
    private FlamesServer server;
    private FlamesUser user;
    ResourceBundle local;
    GuildChannel origin;
    public ServerDataEmbed(FlamesServer server, FlamesUser user, GuildChannel origin) {
        this.server = server;
        this.user = user;
        this.origin = origin;
        local = Flames.local(user.getConfig().getLocale());
    }
    @Override
    public MessageEmbed get() {
        StringBuilder hootanannyDay = new StringBuilder() ;
        hootanannyDay.append(server.getHootanannyDay());
        String rd = "3";
        String nd = "2";
        String st = "1";
        String end = hootanannyDay.toString().toCharArray()[hootanannyDay.toString().length()-1] + "";
        if (rd.equals(end)) hootanannyDay.append("rd");
        else if (nd.equals(end)) hootanannyDay.append("nd");
        else if (st.equals(end)) hootanannyDay.append("st");
        else hootanannyDay.append("th");
        Color color = Color.GRAY;
        String description = String.format(local.getString("hootananny"), hootanannyDay.toString());
        String title = origin.getGuild().getName();
        if (new Date().getDate() == server.getHootanannyDay()) {
            color = Color.RED;
            title = "Well howdy!";
            description = String.format(local.getString("hootanannyToday"), origin.getGuild().getName());
        }
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(local.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(title)
                .setDescription(description)
                .addField(local.getString("totalScore"), server.getScore() + " FP", true)
                .addField(local.getString("averageScore"), server.getScore() / origin.getGuild().getMemberCount() + " FP", true)
                .setColor(color)
                .setThumbnail(origin.getGuild().getIconUrl())
                .setImage(origin.getGuild().getBannerUrl())
                .setFooter(Flames.api.getSelfUser().getGlobalName(), Flames.api.getSelfUser().getAvatarUrl())
                .build();
        return embed;
    }
}
