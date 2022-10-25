/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.intents;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesIntentResponse;
import com.severalcircles.flames.frontend.data.user.embed.WelcomeBackEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class HiIntent implements FlamesIntentResponse {
    static final ResourceBundle values = ResourceBundle.getBundle("balancing/WelcomeBackValues", Locale.getDefault());
    final static int baseBonus = Integer.parseInt(values.getString("baseBonus"));
    final static int riseBonus = Integer.parseInt(values.getString(("riseBonus")));
    final static int streakBonus = Integer.parseInt(values.getString(("streakBonus")));
    final static int randomBonus = Integer.parseInt(values.getString("randomBonus"));
    @Override
    public void execute(MessageReceivedEvent origMsg) {
        Date now = Date.from(Instant.now());
        User discordUser = origMsg.getAuthor();
        FlamesUser flamesUser;
        try {
            flamesUser = FlamesDataManager.readUser(discordUser);
        } catch (IOException | ConsentException | DataVersionException e) {
            e.printStackTrace();
            return;
        }
        int dailyBonus;
        if (Instant.now().truncatedTo(ChronoUnit.DAYS).isAfter(flamesUser.getLastSeen().truncatedTo(ChronoUnit.DAYS))) {
            if (Instant.now().truncatedTo(ChronoUnit.DAYS).compareTo(flamesUser.getLastSeen().truncatedTo(ChronoUnit.DAYS)) > 0) flamesUser.setStreak(flamesUser.getStreak() + 1); else flamesUser.setStreak(0);
            //noinspection deprecation
            dailyBonus = baseBonus + (riseBonus * now.getDay() + 1) + (streakBonus * flamesUser.getStreak()) + (int) Math.round(Math.random() * randomBonus);
            flamesUser.addScore(dailyBonus);
            GlobalData.globalScore += dailyBonus;
            try {
                GlobalData.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            origMsg.getChannel().sendMessageEmbeds(new WelcomeBackEmbed(dailyBonus, discordUser, flamesUser).get()).queue();
            flamesUser.setLastSeen(Instant.now());
            try {
                FlamesDataManager.save(flamesUser);
            } catch (IOException e) {
                e.printStackTrace();
                Flames.incrementErrorCount();
            }
//        } else {
//            origMsg.getMessage().reply("You've already collected your bonus for today. See you tomorrow!").queue();
//        }
        }
    }
}
