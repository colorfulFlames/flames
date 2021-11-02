/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.awt.*;
import java.time.Instant;

public class StatsButtonAction implements FlamesButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) {
        UserStats stats = user.getStats();
        int next = 2^stats.getLevel() + GlobalData.participants;
        MessageEmbed embed = new EmbedBuilder()
                .setColor(new Color(153, 85,187))
                .setAuthor("User Data: Stats", null, event.getUser().getAvatarUrl())
                .setTitle(event.getUser().getName(), "https://flamesapi.severalcircles.com/user/" + event.getUser().getId() + "/stats")
                .setDescription("Level " + stats.getLevel())
                .addField("EXP", stats.getExp()+ "", true)
                .addField("Estimated To Next Level", (Math.abs(next - stats.getExp()) + " (" + ((stats.getExp() / next) * 100) + "%)"), true)
                .addField("Power", stats.getPOW() + "", true)
                .addField("Resistance", stats.getRES() + "", true)
                .addField("Luck", stats.getLUCK() + "", true)
                .addField("Rising", stats.getRISE() + "", true)
                .addField("Charisma", stats.getCAR() + "", true)
                .setTimestamp(Instant.now())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        event.editMessageEmbeds(embed).queue();

    }
}
