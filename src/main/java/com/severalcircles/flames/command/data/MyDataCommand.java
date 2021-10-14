package com.severalcircles.flames.command.data;

import com.severalcircles.flames.api.FlamesAPI;
import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.StringUtils;
import com.severalcircles.flames.features.external.severalcircles.FlamesAssets;
import com.severalcircles.flames.features.rank.Ranking;
import com.severalcircles.flames.system.Flames;
//import com.severalcircles.flames.ux.embeds.errors.ErrorLevel;
//import com.severalcircles.flames.ux.embeds.errors.ErrorMessage;
//import com.severalcircles.flames./**/ux.embeds.errors.ErrorType;
//import discord4j.core.event.domain.message.MessageCreateEvent;
//import discord4j.core.object.entity.Message;
//import discord4j.core.object.entity.User;
//import discord4j.core.spec.EmbedCreateSpec;
//import discord4j.rest.http.client.ClientException;
//import discord4j.rest.util.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDataCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        Logger.getGlobal().log(Level.FINE, "mydata");
        try {
            Ranking.updateThresholds();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = event.getUser();
        ResourceBundle resources = ResourceBundle.getBundle("commands/MyDataCommand", Locale.ENGLISH);
        String rank;
        switch(Ranking.getRank(sender.getScore())) {
            case APPROACHING_BRONZE:
                rank = resources.getString("rank.1");
                break;
            case BRONZE:
                rank = resources.getString("rank.2");
                break;
            case SILVER:
                rank = resources.getString("rank.3");
                break;
            case SHINING_SILVER:
                rank = resources.getString("rank.4");
                break;
            case GOLD:
                rank = resources.getString("rank.5");
                break;
            case BEYOND_GOLD:
                rank = resources.getString("rank.6");
                break;
            case PLATINUM:
                rank = resources.getString("rank.7");
                break;
            case SPARKLING_PLATINUM:
                rank = resources.getString("rank.8");
                break;
            case PLATINUM_SUMMIT:
                rank = resources.getString("rank.9");
                break;
            default:
            case UNRANKED:
                rank = resources.getString("rank.none");
        }
        float emotion = sender.getEmotion();
        String emotionString;
        if (emotion > 5) emotionString = resources.getString("emotion.high");
        else if (emotion > 2) emotionString = resources.getString("emotion.midplus");
        else if (emotion >= 0) emotionString = resources.getString("emotion.mid");
        else if (emotion > -1) emotionString = resources.getString("emotion.midminus");
        else emotionString = resources.getString("emotion.low");
        MessageEmbed embed = new EmbedBuilder()
                .setColor(new Color(153, 85,187))
                .setAuthor(resources.getString("author"), null, user.getAvatarUrl())
                .setTitle(String.format(resources.getString("title"), user.getName()), FlamesAPI.getUserDataLink(user))
                .setDescription(String.format(resources.getString("description"), sender.getStats().getLevel()))
                .addField(resources.getString("flamesScore"), StringUtils.formatScore(sender.getScore()) + " FP", true)
                .addField(resources.getString("rank"), rank, true)
                .addField(resources.getString("toNext"), Ranking.toNext(sender.getScore()) + "", true)
                .addField(resources.getString("emotion"), emotionString, true)
                .setThumbnail(FlamesAssets.getRankIcon(Ranking.getRank(sender.getScore())))
                .setFooter(resources.getString("footer"), Flames.api.getSelfUser().getAvatarUrl()).build();
        event.replyEmbeds(embed).addActionRow(Button.success("mydata", "My Data"), Button.primary("stats", "Stats"), Button.primary("funFacts", "Fun Facts")).queue();

}}
