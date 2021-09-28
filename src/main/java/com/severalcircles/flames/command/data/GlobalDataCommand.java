package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.Analysis;
import com.severalcircles.flames.features.external.severalcircles.FlamesAssets;
import com.severalcircles.flames.system.Flames;
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
import java.util.Map;
import java.util.ResourceBundle;

public class GlobalDataCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
//        Message message = event.getMessage();
        @SuppressWarnings("OptionalGetWithoutIsPresent") User user = event.getUser();
        ResourceBundle resources = ResourceBundle.getBundle("commands/GlobalDataCommand", Locale.forLanguageTag(sender.getLocale()));
//        System.out.println("based");
        String trendingEntity = "";
        int times = 0;
        for (Map.Entry<String, Integer> entry: Analysis.entityCache.entrySet()) {
            if (entry.getKey() != trendingEntity && entry.getValue() > times) {
                trendingEntity = entry.getKey();
                times = entry.getValue();
            }
        }
        final String trending = trendingEntity;
        MessageEmbed embed = new EmbedBuilder()
                .setColor(new Color(15, 131, 217))
                .setAuthor(resources.getString("author"), null, user.getAvatarUrl())
                .setTitle(resources.getString("trendingTopic") + ": " + trending)
//                .setUrl("https://en.wikipedia.org/wiki/" + trending.replace(" ", "_"))
                .addField(resources.getString("totalScore"), GlobalData.globalScore + "", true)
                .addField(resources.getString("averageScore"), GlobalData.averageScore + "", true)
//                .addField(resources.getString("trendingTopic"), trending, true)
                .setTimestamp(Instant.now())
                .setThumbnail(FlamesAssets.globalDataUrl)
                .setFooter(resources.getString("footer"), Flames.api.getSelfUser().getAvatarUrl()).build();
        event.replyEmbeds(embed).queue();
    }
}
