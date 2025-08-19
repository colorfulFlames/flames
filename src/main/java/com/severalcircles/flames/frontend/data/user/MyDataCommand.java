/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.data.user.embed.UserDataEmbed;
import com.severalcircles.flames.frontend.message.ConsentExceptionDialog;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;
import java.util.Locale;


public class MyDataCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("features/UserDataEmbed", flamesUser.getConfig().getLocale()));
        MessageEmbed embed;
        try {
            embed = new UserDataEmbed(event.getOption("target").getAsUser(), FlamesDataManager.getUser(event.getOption("target").getAsUser().getId())).get();
        } catch (NullPointerException e) {
            embed = new UserDataEmbed(event.getUser(), sender).get();
//        event.replyEmbeds(new ExceptionHandler(new Exception()).handleThenGetFrontend()).complete();
        } catch (ConsentException e) {
            embed = new ConsentExceptionDialog(Locale.forLanguageTag(sender.getLang())).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        event.replyEmbeds(embed).queue();

}}
