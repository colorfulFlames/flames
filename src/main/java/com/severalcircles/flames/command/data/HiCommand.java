package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.external.severalcircles.FlamesAssets;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

public class HiCommand implements FlamesCommand {
    final static int baseBonus = 1000;
    final static int riseBonus = 100;
    final static int streakBonus = 100;
    final static int randomBonus = 25;

    @Override
    public void execute(SlashCommandEvent event, FlamesUser flamesUser) {
        Date now = Date.from(Instant.now());
        User discordUser = event.getUser();
        int dailyBonus;
        if (Instant.now().truncatedTo(ChronoUnit.DAYS).isAfter(flamesUser.getLastSeen().truncatedTo(ChronoUnit.DAYS))) {
            if (Instant.now().truncatedTo(ChronoUnit.DAYS).compareTo(flamesUser.getLastSeen().truncatedTo(ChronoUnit.DAYS)) == 1) flamesUser.setStreak(flamesUser.getStreak() + 1); else flamesUser.setStreak(0);
            dailyBonus = baseBonus + (riseBonus * flamesUser.getStats().getRISE()) + (streakBonus * flamesUser.getStreak()) + (int) Math.round(Math.random() * randomBonus);
            flamesUser.addScore(dailyBonus);
            GlobalData.globalScore += dailyBonus;
            try {
                GlobalData.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String timeMessage = "Hi, %s";
            if (now.getHours() < 6) timeMessage = "A sunrise as pretty as %s";
            else if (now.getHours() < 9) timeMessage = "Good Morning, %s";
            else if (now.getHours() < 12) timeMessage = "Hi, %s";
            else if (now.getHours() < 15) timeMessage = "Good Afternoon, %s";
            else if (now.getHours() < 18) timeMessage = "Nice work today, %s!";
            else if (now.getHours() < 21) timeMessage = "Good Evening, %s";
            else if (now.getHours() >= 21) timeMessage = "Have a good night, %s";
            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Welcome Back to Flames")
                    .setAuthor(String.format(timeMessage, discordUser.getName()), null, discordUser.getAvatarUrl())
                    .setDescription("It's so nice to see you again!")
                    .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl())
                    .setImage(FlamesAssets.welcomeBackUrl)
                    .addField("Daily Bonus", "" + dailyBonus, true)
                    .setColor(Color.ORANGE)
                    .addField("Your Flames Score", "" + flamesUser.getScore() + " FP", true)
                    .setTimestamp(Instant.now()).build();
            event.replyEmbeds(embed).queue();
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
