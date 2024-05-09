/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.frontend;

import com.severalcircles.flames.amiguito.Amiguito;
import com.severalcircles.flames.amiguito.AmiguitoDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;
import java.util.Objects;

public class AmiguitoNewCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) throws ConsentException, IOException {
        if (AmiguitoDataManager.load(sender.getDiscordId()) != null) {
            event.reply("You already have an Amiguito!").queue();
            return;
        }
        if (event.getOption("name") == null) {
            event.reply("You need to provide a name for your Amiguito!").queue();
            return;
        }
        AmiguitoDataManager.save(new Amiguito(sender, Objects.requireNonNull(event.getOption("name")).getAsString()));
        event.reply("Amiguito created!").queue();
    }
}
