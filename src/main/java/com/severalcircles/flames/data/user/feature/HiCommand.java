/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user.feature;

import com.severalcircles.flames.FlamesCommand;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.Flames;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class HiCommand implements FlamesCommand {
    static ResourceBundle values = ResourceBundle.getBundle("balancing/WelcomeBackValues", Locale.getDefault());
    final static int baseBonus = (int) values.getObject("baseBonus");
    final static int riseBonus = (int) values.getObject("riseBonus");
    final static int streakBonus = (int) values.getObject("streakBonus");
    final static int randomBonus = (int) values.getObject("randomBonus");

    @SuppressWarnings("deprecation")
    @Override
    public void execute(SlashCommandEvent event, FlamesUser flamesUser) {
        Date now = Date.from(Instant.now());
        User discordUser = event.getUser();
        int dailyBonus;
        if (Instant.now().truncatedTo(ChronoUnit.DAYS).isAfter(flamesUser.getLastSeen().truncatedTo(ChronoUnit.DAYS))) {
            if (Instant.now().truncatedTo(ChronoUnit.DAYS).compareTo(flamesUser.getLastSeen().truncatedTo(ChronoUnit.DAYS)) > 0) flamesUser.setStreak(flamesUser.getStreak() + 1); else flamesUser.setStreak(0);
            dailyBonus = baseBonus + (riseBonus * flamesUser.getStats().getRISE()) + (streakBonus * flamesUser.getStreak()) + (int) Math.round(Math.random() * randomBonus);
            flamesUser.addScore(dailyBonus);
            GlobalData.globalScore += dailyBonus;
            try {
                GlobalData.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.replyEmbeds(new WelcomeBackEmbed(dailyBonus, discordUser, flamesUser).get()).queue();
            flamesUser.setLastSeen(Instant.now());
            try {
                FlamesDataManager.save(flamesUser);
            } catch (IOException e) {
                e.printStackTrace();
                Flames.incrementErrorCount();
            }
        } else {
            event.reply("You've already collected your bonus for today. See you tomorrow!").queue();
        }
    }
}
