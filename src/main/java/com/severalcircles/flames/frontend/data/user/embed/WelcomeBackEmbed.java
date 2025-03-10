/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.embed;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesEmbed;
import com.severalcircles.flames.util.StringUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class WelcomeBackEmbed implements FlamesEmbed {
    private static ResourceBundle resources;
    private final int dailyBonus;
    private final User user;
    private final FlamesUser flamesUser;

    public WelcomeBackEmbed(int dailyBonus, User user, FlamesUser flamesUser) {
        this.dailyBonus = dailyBonus;
        this.user = user;
        this.flamesUser = flamesUser;
        resources = Flames.local(Locale.forLanguageTag(flamesUser.getLang()));
    }
    public MessageEmbed get() {
        Date now = Date.from(Instant.now());
        String timeMessage;
        Color color = Color.decode("#D9581C");
        String img = "https://severalcircles.com/flames/assets/welcome/";
            if (now.getHours() < 6) {
                timeMessage = resources.getString("author.earlymorning");
                img += "EARLY_MORNING.png";
                color = Color.decode("#000050");
            }
        else //noinspection deprecation
            if (now.getHours() < 12) {
                timeMessage = resources.getString("author.morning");
                img += "MORNING.png";
                color = Color.decode("#FA6800");
            }
        else //noinspection deprecation
            if (now.getHours() < 15) {
                timeMessage = resources.getString("author.earlyafternoon");
                img += "AFTERNOON.png";
                color = Color.CYAN;

            }
        else //noinspection deprecation
                if (now.getHours() < 18) {
                    timeMessage = resources.getString("author.lateafternoon");
                    img += "AFTERNOON.png";
                    color = Color.CYAN;
                }
        else //noinspection deprecation
            if (now.getHours() < 21) {
                timeMessage = resources.getString("author.evening");
                img += "EVENING.png";
                color = Color.MAGENTA;

            }
        else {
                timeMessage = resources.getString("author.night");
                img += "NIGHT.png";
                color = Color.BLACK;
            }
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(String.format(timeMessage, user.getGlobalName()), null, Flames.api.getSelfUser().getAvatarUrl())
                .setTitle(resources.getString("title"))
                .addField(resources.getString("dailyBonus"), StringUtil.formatScore(dailyBonus), true)
                .addField(resources.getString("score"), StringUtil.formatScore(flamesUser.getScore()), true)
                .setDescription(resources.getString("description"))
                .setImage(img)
                .setFooter(Flames.api.getSelfUser().getGlobalName(), Flames.api.getSelfUser().getAvatarUrl())
                .setColor(color);
        return embed.build();
    }
}
