/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.data.user.embed.GetAlongEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;
import java.util.Objects;

public class GetAlongCommand implements FlamesCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        User victim = Objects.requireNonNull(event.getOption("user")).getAsUser();
        try {
            FlamesUser flvictim = FlamesDataManager.readUser(victim);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConsentException e) {
            event.replyEmbeds(e.getHandler().handleThenGetFrontend()).complete();
            e.printStackTrace();
        }
        @SuppressWarnings("SuspiciousMethodCalls") int score = sender.getRelationships().getRelationships().get(victim);
        event.replyEmbeds(new GetAlongEmbed(event.getUser(), sender, victim, score).get()).complete();
    }
}
