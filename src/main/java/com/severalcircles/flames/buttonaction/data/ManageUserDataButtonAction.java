package com.severalcircles.flames.buttonaction.data;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;

public class ManageUserDataButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        MessageEmbed embed = new EmbedBuilder()
                .setAuthor("User Data Management",null, event.getUser().getAvatarUrl())
                .setTitle(event.getUser().getName() + ", what can I help you with?")
                .addField("Select an option below to continue.", "You may be prompted to continue in a DM.", true)
                .setTimestamp(Instant.now())
                .setColor(Color.RED.darker())
                .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        event.editMessageEmbeds(embed).setActionRow(Button.primary("updateData", "Fix User Data")).queue();
//        event.getMessage().delete();
    }
}
