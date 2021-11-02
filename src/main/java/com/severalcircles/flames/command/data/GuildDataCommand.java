/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.command.data;

import com.severalcircles.flames.command.FlamesCommand;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.data.guild.FlamesGuild;
import com.severalcircles.flames.data.guild.NewGuildException;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.features.info.data.GuildDataEmbed;
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
            e.printStackTrace();
        } catch (NewGuildException e) {
            // TODO: Add error message
            e.printStackTrace();
        }
        event.replyEmbeds(new GuildDataEmbed(event.getUser(), sender, gdata, guild).get()).complete();
    }
}
