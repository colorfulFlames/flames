/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.thanks;

import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesUserContext;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import com.severalcircles.flames.frontend.message.fourhundred.DataVersionErrorMessage;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.io.IOException;
import java.util.Objects;

public class ThanksContext implements FlamesUserContext {
    @Override
    public void execute(UserContextInteractionEvent event) {
        User thanked = event.getTarget();
        FlamesUser sender;
        String msg;
        try {
            msg = Objects.requireNonNull(event.getOption("msg")).getAsString();
        } catch (NullPointerException e) {
            msg = "";
        }
        FlamesUser flt;
        try {
            flt = FlamesDataManager.readUser(thanked);
            sender = FlamesDataManager.readUser(event.getUser());
        } catch (IOException e) {
            e.printStackTrace();
            event.replyEmbeds(new GenericErrorMessage(e).get()).complete();
            return;
        } catch (ConsentException | NullPointerException e) {
            event.reply("That user isn't using Flames yet.").complete();
            return;
        } catch (DataVersionException e) {
            event.replyEmbeds(new DataVersionErrorMessage(e).get()).complete();
            e.printStackTrace();
            return;
        }
        event.replyEmbeds(new ThanksEmbed(thanked, event.getUser(), flt, msg).get()).complete();
        ThanksEmbed.success.remove(event.getUser().getId());
    }
}
