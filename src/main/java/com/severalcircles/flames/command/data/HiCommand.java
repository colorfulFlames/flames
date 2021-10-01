package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.base.FlamesData;
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
import java.util.Date;

public class HiCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser flamesUser) {
        Date now = Date.from(Instant.now());
        User discordUser = event.getUser();
        int dailyBonus = 100 + (flamesUser.getStreak() * 100) * flamesUser.getStats().getRISE();
        // noinspection deprecation
        System.out.println("Last Seen (" + flamesUser.getLastSeen() + ") < Today (" + now.getDay() + ") (" + (flamesUser.getLastSeen() < now.getDay()) + ")");
        System.out.println("Last Seen == 6 (" + (flamesUser.getLastSeen() == 6) + ") && Today !=6 0 (" + (now.getDay() != 6) + ")");
        if (flamesUser.getLastSeen() < now.getDay() | (flamesUser.getLastSeen() == 6 && now.getDay() != 6)) {
            flamesUser.addScore(dailyBonus);
            GlobalData.globalScore += dailyBonus;
            try {
                GlobalData.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String timeMessage = "Hi, %s";
            if (now.getHours() < 6) timeMessage = "Thought it was the sunrise, turns out it was just %s";
            else if (now.getHours() < 9) timeMessage = "Good Morning, %s";
            else if (now.getHours() < 12) timeMessage = "Hi, %s";
            else if (now.getHours() < 15) timeMessage = "Good Afternoon, %s";
            else if (now.getHours() < 18) timeMessage = "All done for today, %s?";
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
            flamesUser.setLastSeen(now.getDay());
            FlamesData.write(flamesUser);
        } else {
            event.reply("You've already collected your bonus for today. See you tomorrow!").queue();
        }
    }
}
