/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.thanks;

import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.exception.FlamesMetaException;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.frontend.FlamesUserContext;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.io.IOException;
import java.util.Objects;

public class ThanksContext implements FlamesUserContext {
    @Override
    public void execute(UserContextInteractionEvent event) {
        User thanked = event.getTarget();
        LegacyFlamesUser sender;
        String msg;
        try {
            msg = Objects.requireNonNull(event.getOption("msg")).getAsString();
        } catch (NullPointerException e) {
            msg = "";
        }
        LegacyFlamesUser flt;
        try {
            flt = LegacyFlamesDataManager.readUser(thanked);
            LegacyFlamesDataManager.readUser(event.getUser());
        } catch (IOException e) {
            e.printStackTrace();
            event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
            return;
        } catch (ConsentException e) {
            event.replyEmbeds(e.getHandler().handleThenGetFrontend()).complete();
            return;
        } catch (FlamesMetaException e) {
            event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
            return;
        }
        event.replyEmbeds(new ThanksEmbed(thanked, event.getUser(), flt, msg).get()).complete();
        ThanksEmbed.success.remove(event.getUser().getId());
    }
}
