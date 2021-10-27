package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.info.data.UserDataEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.Locale;
import java.util.ResourceBundle;

//import com.severalcircles.flames.ux.embeds.errors.ErrorLevel;
//import com.severalcircles.flames.ux.embeds.errors.ErrorMessage;
//import com.severalcircles.flames./**/ux.embeds.errors.ErrorType;
//import discord4j.core.event.domain.message.MessageCreateEvent;
//import discord4j.core.object.entity.Message;
//import discord4j.core.object.entity.User;
//import discord4j.core.spec.EmbedCreateSpec;
//import discord4j.rest.http.client.ClientException;
//import discord4j.rest.util.Color;

public class MyDataCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("features/UserDataEmbed", Locale.getDefault());
        MessageEmbed embed = new UserDataEmbed(event.getUser(), sender).get();
        event.replyEmbeds(embed).addActionRow(Button.success("mydata", "My Data"), Button.primary("stats", "Stats"), Button.primary("funFacts", "Fun Facts")).queue();

}}
