/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.other;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.legacy.server.FlamesServer;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.awt.*;
import java.util.*;

public class ServerDataEmbed implements FlamesEmbed {
    private FlamesServer server;
    private LegacyFlamesUser user;
    ResourceBundle local;
    GuildChannel origin;
    public ServerDataEmbed(FlamesServer server, LegacyFlamesUser user, GuildChannel origin) {
        this.server = server;
        this.user = user;
        this.origin = origin;
        local = Flames.local(user.getConfig().getLocale());
    }
    @Override
    public MessageEmbed get() {
        StringBuilder hootenannyDay = new StringBuilder() ;
        hootenannyDay.append(server.getHootenannyDay());
        String rd = "3";
        String nd = "2";
        String st = "1";
        String end = hootenannyDay.toString().toCharArray()[hootenannyDay.toString().length()-1] + "";
        if (rd.equals(end)) hootenannyDay.append("rd");
        else if (nd.equals(end)) hootenannyDay.append("nd");
        else if (st.equals(end)) hootenannyDay.append("st");
        else hootenannyDay.append("th");
        Color color = Color.GRAY;
        String description = String.format(local.getString("hootenanny"), hootenannyDay.toString());
        String title = origin.getGuild().getName();
        if (new Date().getDate() == server.getHootenannyDay()) {
            color = Color.RED;
            title = "Well howdy!";
            description = String.format(local.getString("hootenannyToday"), origin.getGuild().getName());
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
