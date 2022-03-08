/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.info.debug;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.events.ButtonEvent;
import com.severalcircles.flames.external.FlamesAssets;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class DebugEmbed implements FlamesEmbed {
    @Override
    public MessageEmbed get() {
        MessageEmbed builder = new EmbedBuilder()
                .setAuthor("Debugging Information")
                .setTitle(Flames.api.getSelfUser().getName() + " version " + Flames.version)
                .setColor(Color.GREEN.darker())
                .setDescription("by Several Circles")
                .addField("Gateway Ping", String.valueOf(Flames.api.getGatewayPing()), true)
                .addField("Commands Registered", String.valueOf(Flames.commandMap.size()), true)
                .addField("Button Actions Registered", String.valueOf(ButtonEvent.buttonActionMap.size()), true)
                .addField("Number of Participants", String.valueOf(GlobalData.participants), true)
                .addField("Guilds", String.valueOf(Flames.api.getGuilds().size()), true)
                .addField("Status", Flames.api.getStatus().name(), true)
                .addField("Created", StringUtil.prettifyDate(Flames.api.getSelfUser().getTimeCreated().toInstant()), true)
                .setTimestamp(Instant.now())
                .setThumbnail(FlamesAssets.getVersionIcon())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        return builder;
    }
}
