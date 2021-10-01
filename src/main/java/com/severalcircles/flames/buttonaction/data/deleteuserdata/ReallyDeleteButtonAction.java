package com.severalcircles.flames.buttonaction.data.deleteuserdata;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.Flames;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.Component;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;

public class ReallyDeleteButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        MessageEmbed embed;
        event.deferReply(true);
        try  {
            FlamesData.deleteUser(user);
            embed = new EmbedBuilder()
                    .setAuthor("Delete User Data", event.getUser().getAvatarUrl())
                    .setTitle(event.getUser().getName() + ", your user data was successfully deleted.")
                    .setDescription("Thanks for using Flames.")
                    .setColor(Color.GREEN.darker())
                    .setTimestamp(Instant.now())
                    .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        } catch (IOException e) {
            embed = new EmbedBuilder()
                    .setAuthor("Delete User Data", event.getUser().getAvatarUrl())
                    .setTitle(event.getUser().getName() + ", something went wrong.")
                    .setDescription("Your user data could not be deleted. Try again later or contact Several Circles#2148.")
                    .setColor(Color.GREEN.darker())
                    .setTimestamp(Instant.now())
                    .setFooter("Flames", Flames.api.getSelfUser().getAvatarUrl()).build();
        }
        event.getMessage().editMessage(embed).complete();
        event.reply(":)");
    }
}
