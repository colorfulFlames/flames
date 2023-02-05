/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.thanks;

import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import com.severalcircles.flames.frontend.message.fourhundred.DataVersionErrorMessage;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;
import java.util.Objects;

public class ThanksCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        User thanked = Objects.requireNonNull(event.getOption("who")).getAsUser();
        String msg;
        try {
            msg = Objects.requireNonNull(event.getOption("msg")).getAsString();
        } catch (NullPointerException e) {
            msg = "";
        }
        FlamesUser flt;
        try {
            flt = FlamesDataManager.readUser(thanked);
        } catch (IOException e) {
            e.printStackTrace();
            event.replyEmbeds(new GenericErrorMessage(e).get()).complete();
            return;
        } catch (ConsentException | NullPointerException e) {
            event.reply("That user isn't using Flames yet.").setEphemeral(true).complete();
            return;
        } catch (DataVersionException e) {
            event.replyEmbeds(new DataVersionErrorMessage(e).get()).complete();
            e.printStackTrace();
            return;
        }
        event.replyEmbeds(new ThanksEmbed(thanked, event.getUser(), flt, msg).get()).complete();
        if (ThanksEmbed.success.contains(event.getUser().getId())) {
            event.getChannel().sendMessage(thanked.getAsMention()).complete();
            ThanksEmbed.success.remove(event.getUser().getId());
        }
    }
}
