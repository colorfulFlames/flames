/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.global.GlobalData;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.data.user.embed.WelcomeBackEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import com.severalcircles.flames.frontend.FlamesCommand;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class HiCommand implements FlamesCommand {
    static final ResourceBundle values = ResourceBundle.getBundle("balancing/WelcomeBackValues", Locale.getDefault());
    final static int baseBonus = Integer.parseInt(values.getString("baseBonus"));
    final static int riseBonus = Integer.parseInt(values.getString(("riseBonus")));
    final static int streakBonus = Integer.parseInt(values.getString(("streakBonus")));
    final static int randomBonus = Integer.parseInt(values.getString("randomBonus"));

    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser flamesUser) {
        Date now = Date.from(Instant.now());
        User discordUser = event.getUser();
        int dailyBonus;
        if (Instant.now().truncatedTo(ChronoUnit.DAYS).isAfter(flamesUser.getLastSeen().toInstant().truncatedTo(ChronoUnit.DAYS))) {
            if (Instant.now().truncatedTo(ChronoUnit.DAYS).compareTo(flamesUser.getLastSeen().toInstant().truncatedTo(ChronoUnit.DAYS)) > 0) flamesUser.setStreak(flamesUser.getStreak() + 1); else flamesUser.setStreak(0);
            //noinspection deprecation
            dailyBonus = baseBonus + (riseBonus * now.getDay() + 1) + (streakBonus * flamesUser.getStreak()) + (int) Math.round(Math.random() * randomBonus);
            flamesUser.addScore(dailyBonus);
            GlobalData.globalScore += dailyBonus;
            try {
                GlobalData.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.replyEmbeds(new WelcomeBackEmbed(dailyBonus, discordUser, flamesUser).get()).queue();
            flamesUser.setLastSeen(new Date());
            try {
                FlamesDataManager.saveUser(flamesUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            event.reply("You've already collected your bonus for today. See you tomorrow!").setEphemeral(true).queue();

        }
    }
}
