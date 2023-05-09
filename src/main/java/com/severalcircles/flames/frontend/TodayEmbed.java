/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.Util;
import com.severalcircles.flames.conversations.EmotionLevel;
import com.severalcircles.flames.conversations.today.Today;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;
import java.util.Locale;

@Embed(name = "Today")
public class TodayEmbed extends FlamesEmbed{

    public TodayEmbed(Locale locale) {
        super(locale);
    }

    @Override
    public MessageEmbed get() {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(String.format(local.getString("author"), Util.prettyDate(Instant.now(), locale)), null, Flames.getApi().getSelfUser().getAvatarUrl())
                .setTitle(local.getString("title"))
                .addField(local.getString("talkingAbout"), Today.getTopTopic(), true)
                .addField(local.getString("feeling"), EmotionLevel.getLevel(Today.getEmotion()).getName(this.locale), true)
                .addField(local.getString("highUser"), Today.getHighUser().getDiscordUser().getName() + " (" + Today.getHighUser().getScore() + ")", true)
                .addBlankField(false)
                .addField("\"" + Today.getQuote().message() + "\"", "-" + Today.getQuote().user().getDiscordUser().getName(), false)
                .addBlankField(false)
                .addField(local.getString("closer.title"), local.getString("closer.text"), false)
                .setColor(0xEA2B1F)
                .setThumbnail("https://www.severalcircles.com/flames/assets/apps/today.png")
                .build();
        return embed;
    }
}
