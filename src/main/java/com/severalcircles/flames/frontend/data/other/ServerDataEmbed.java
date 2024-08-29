/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.other;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesServer;
import com.severalcircles.flames.data.legacy.server.LegacyFlamesServer;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;

import java.awt.*;
import java.util.*;

public class ServerDataEmbed implements FlamesEmbed {
    private final FlamesServer server;
    private final FlamesUser user;
    ResourceBundle local;
    GuildChannel origin;
    public ServerDataEmbed(FlamesServer server, FlamesUser user, GuildChannel origin) {
        this.server = server;
        this.user = user;
        this.origin = origin;
        local = Flames.local(Locale.forLanguageTag(user.getLang()));
    }
    @Override
    public MessageEmbed get() {
        Color color = Color.GRAY;
        String description = String.format(local.getString("hootenanny"), ordinal(server.getHootenannyDay()));
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
    public static String ordinal(int i) {
        String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];

        }
    }
}
