/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.ImageSearch;
import com.severalcircles.flames.external.analysis.Analysis;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.Emotion;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class TodayEmbed implements FlamesEmbed {
    private final FlamesUser flamesUser;
    private static ResourceBundle resources;
    public TodayEmbed(User user, FlamesUser flamesUser) {
        this.flamesUser = flamesUser;
        resources = Flames.local(Locale.forLanguageTag(flamesUser.getLang()));
    }
    public MessageEmbed get() {
        String trendingEntity = "";
        int times = 0;
        for (Map.Entry<String, Integer> entry: Analysis.entityCache.entrySet()) {
            if (!Objects.equals(entry.getKey(), trendingEntity) && entry.getValue() > times) {
                trendingEntity = entry.getKey();
                times = entry.getValue();
            }
        }
        String title = resources.getString("title");
        try {
            return new EmbedBuilder()
                    .setAuthor(String.format(resources.getString("author"), StringUtil.prettifyDate(Instant.now())), null, Flames.api.getSelfUser().getAvatarUrl())
                    .setTitle(title)
                    .setDescription(
                            String.format(resources.getString("quoteTitle"), Today.quote.author()) + "\n" +
                            String.format(resources.getString("quote"), Today.quote.message(), StringUtil.prettifyDate(Today.quote.inst())))
                    .addBlankField(false)
                    .addField(resources.getString("talkingAbout"), trendingEntity, true)
                    .addField(resources.getString("feeling"), Emotion.getEmotionString(Today.emotion, Locale.forLanguageTag(flamesUser.getLang())), true)
                    .addField(resources.getString("highUser"), Today.highUser + " (" + StringUtil.formatScore(Today.highScore) + ")", true)
//                    .addBlankField(false)
//                    .addField(String.format(resources.getString("quoteTitle"), Today.quote.author()), String.format(resources.getString("quote"), Today.quote.message(), StringUtil.prettifyDate(Today.quote.inst())), false)
//                    .addBlankField(false)
//                    .addField(resources.getString("allAbout"), resources.getString("tomorrowBring"), false)
                    .setThumbnail(ImageSearch.searchImage(trendingEntity))
                    .setFooter(Flames.api.getSelfUser().getGlobalName(), Flames.api.getSelfUser().getAvatarUrl())
                    .setImage("https://severalcircles.com/flames/assets/apps/today_footer.png")
                    .setColor(Color.decode("#F1D302"))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
