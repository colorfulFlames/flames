/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.askflames;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.external.dialog.DialogSession;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class AskCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
//        event.deferReply(true).complete();
        DialogSession session = new DialogSession();
        String[] response;
        try {
            response = session.processMessage(event.getOption("query").getAsString()).split("~");
        } catch (IOException e) {
            e.printStackTrace();
            event.reply("Something went wrong.").complete();
            return;
        }
        event.reply(response[0]).complete();
    }
}
