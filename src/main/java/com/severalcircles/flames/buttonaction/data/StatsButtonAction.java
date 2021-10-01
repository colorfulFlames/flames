package com.severalcircles.flames.buttonaction.data;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class StatsButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        UserStats stats = user.getStats();
        MessageEmbed embed = new EmbedBuilder()
                .setColor(new Color(153, 85,187))
                .setAuthor("User Data: Stats", null, event.getUser().getAvatarUrl())
                .setTitle(event.getUser().getName())
                .setDescription("Level " + stats.getLevel())
                .addField("EXP", stats.getExp()+ "", true)
                .addField("To Next Level", (GlobalData.averageScore * (stats.getLevel() + 1) - stats.getExp()) + "", true)
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
