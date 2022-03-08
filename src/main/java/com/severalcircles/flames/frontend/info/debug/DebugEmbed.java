/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.info.debug;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.ButtonEvent;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.StringUtil;
import jdk.nashorn.internal.runtime.Debug;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.ResourceBundle;

public class DebugEmbed implements FlamesEmbed {
    private ResourceBundle resources;
    User user;
    FlamesUser flamesUser;
    public DebugEmbed(User user, FlamesUser flamesUser) {
        this.user = user;
        this.flamesUser = flamesUser;
        this.resources = ResourceBundle.getBundle("features/DebugEmbed", flamesUser.getConfig().getLocale());
    }
    @Override
    public MessageEmbed get() {
        MessageEmbed builder = new EmbedBuilder()
                .setAuthor(resources.getString("author"), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), Flames.api.getSelfUser().getName(), Flames.version))
                .setColor(Color.GREEN.darker())
                .setDescription(resources.getString("description"))
                .addField(resources.getString("gatewayPing"), String.valueOf(Flames.api.getGatewayPing()), true)
                .addField(resources.getString("commands"), String.valueOf(Flames.commandMap.size()), true)
                .addField(resources.getString("buttonActions"), String.valueOf(ButtonEvent.buttonActionMap.size()), true)
                .addField(resources.getString("participants"), String.valueOf(GlobalData.participants), true)
                .addField(resources.getString("guilds"), String.valueOf(Flames.api.getGuilds().size()), true)
                .addField(resources.getString("status"), Flames.api.getStatus().name(), true)
                .addField(resources.getString("created"), StringUtil.prettifyDate(Flames.api.getSelfUser().getTimeCreated().toInstant()), true)
                .setTimestamp(Instant.now())
                .setThumbnail(FlamesAssets.getVersionIcon())
                .setFooter(resources.getString("copyright"), Flames.api.getSelfUser().getAvatarUrl()).build();
        return builder;
    }
}
