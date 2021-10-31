/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.global;

import com.severalcircles.flames.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.util.StringUtil;
import com.severalcircles.flames.external.severalcircles.FlamesAssets;
import com.severalcircles.flames.Flames;
//import discord4j.core.event.domain.message.MessageCreateEvent;
//import discord4j.core.object.entity.Message;
//import discord4j.core.object.entity.User;
//import discord4j.rest.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class GlobalDataCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
//        Message message = event.getMessage();
        User user = event.getUser();
        ResourceBundle resources = ResourceBundle.getBundle("commands/GlobalDataCommand", Locale.ENGLISH);
        MessageEmbed embed = new EmbedBuilder()
                .setColor(new Color(15, 131, 217))
                .setAuthor(resources.getString("author"), null, user.getAvatarUrl())
                .setTitle(Flames.api.getSelfUser().getName())
//                .setUrl("https://en.wikipedia.org/wiki/" + trending.replace(" ", "_"))
                .addField(resources.getString("totalScore"), StringUtil.formatScore(GlobalData.globalScore), true)
                .addField(resources.getString("averageScore"), StringUtil.formatScore(GlobalData.averageScore), true)
//                .addField(resources.getString("trendingTopic"), trending, true)
                .setTimestamp(Instant.now())
                .setThumbnail(FlamesAssets.globalDataUrl)
                .setFooter(resources.getString("footer"), Flames.api.getSelfUser().getAvatarUrl()).build();
        event.replyEmbeds(embed).queue();
    }
}
