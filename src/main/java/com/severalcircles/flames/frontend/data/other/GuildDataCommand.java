/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.data.other;

import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.guild.NewGuildException;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.IOException;

public class GuildDataCommand implements FlamesCommand {
//    ResourceBundle resources = ResourceBundle.getBundle("commands/MyDataCommand", Locale.ENGLISH);
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        Guild guild = event.getGuild();
        FlamesGuild gdata = null;
        try {
            gdata = FlamesDataManager.readGuild(event.getGuild().getId());
        } catch (IOException e) {
            event.replyEmbeds(new GenericErrorMessage(e).get()).complete();
            return;
        } catch (NewGuildException e) {
            event.replyEmbeds(new GenericErrorMessage((FlamesError) e).get()).complete();
            return;
        }
        event.replyEmbeds(new GuildDataEmbed(event.getUser(), sender, gdata, guild).get()).complete();
    }
}
