/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.frontend;

import com.severalcircles.flames.amiguito.AmiguitoDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class AmiguitoInfoCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) throws ConsentException, IOException {
        event.replyEmbeds(new AmiguitoInfoEmbed(AmiguitoDataManager.load(sender.getDiscordId()), sender, event.getUser()).get()).queue();
    }
}
