/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.FlamesPrettyDate;
import com.severalcircles.flames.features.today.Today;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class TodayCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        String trendingEntity = "";
        int times = 0;
        for (Map.Entry<String, Integer> entry: Analysis.entityCache.entrySet()) {
            if (entry.getKey() != trendingEntity && entry.getValue() > times) {
                trendingEntity = entry.getKey();
                times = entry.getValue();
            }
        }
        Instant now = Instant.now();
        String input = now.atOffset(ZoneOffset.of("-5")).getDayOfWeek().toString();
        String day = input.substring(0, 1).toUpperCase() + input.substring(1);
//        ResourceBundle resources = ResourceBundle.getBundle("commands/MyDataCommand", Locale.ENGLISH);
        String emotionString;
        float emotion = Today.emotion;
        if (emotion > 100) emotionString = "Joyous";
        else if (emotion > 50) emotionString = "Happy";
        else if (emotion >= 0) emotionString = "Mixed";
        else if (emotion > -50) emotionString = "A little sad";
        else emotionString = "Pretty upset";
        MessageEmbed today = new EmbedBuilder()
                .setAuthor("Today for " + day + ", " + FlamesPrettyDate.prettifyDate(now),null,  event.getUser().getAvatarUrl())
                .setTitle("Today...")
                .addField("We're talking about", trendingEntity, true)
                .addBlankField(true)
                .addField("We're feeling", emotionString + " (" + emotion + ")", true)
                .addField("The highest scoring user is", Today.highUser + " (" + Today.highScore + ")", true)
                .addBlankField(false)
                .addField("\"" + Today.quote[0] + "\"", " - " + Today.quote[1] + ", " + FlamesPrettyDate.prettifyDate(now), false)
                .addBlankField(false)
                .addField("...and that's what today is all about.", "What will tomorrow bring?", true)
                .setColor(Color.GREEN.darker())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl())
                .build();
        event.replyEmbeds(today).complete();
    }
}
