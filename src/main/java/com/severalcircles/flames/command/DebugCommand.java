package com.severalcircles.flames.command;

import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.discord.ButtonEvent;
import com.severalcircles.flames.features.FlamesPrettyDate;
import com.severalcircles.flames.system.Flames;
import jdk.vm.ci.meta.Local;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;
import java.util.ResourceBundle;

public class DebugCommand implements FlamesCommand{
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        ResourceBundle common = ResourceBundle.getBundle("Common", Locale.ENGLISH);
        MessageEmbed builder = new EmbedBuilder()
                .setAuthor("Debugging Information")
                .setTitle(Flames.api.getSelfUser().getName() + " version " + common.getString("version"))
                .setColor(Color.GREEN.darker())
                .setDescription("by Several Circles")
                .addField("Gateway Ping", String.valueOf(Flames.api.getGatewayPing()), true)
                .addField("Commands Registered", String.valueOf(Flames.commandMap.size()), true)
                .addField("Button Actions Registered", String.valueOf(ButtonEvent.buttonActionMap.size()), true)
                .addField("Number of Participants", String.valueOf(GlobalData.participants), true)
                .addField("Guilds", String.valueOf(Flames.api.getGuilds().size()), true)
                .addField("Status", Flames.api.getStatus().name(), true)
                .addField("Created", FlamesPrettyDate.prettifyDate(Flames.api.getSelfUser().getTimeCreated().toInstant()), true)
                .setTimestamp(Instant.now())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        event.replyEmbeds(builder).complete();
    }
}
