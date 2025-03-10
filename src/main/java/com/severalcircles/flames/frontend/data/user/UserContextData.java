/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.FlamesMetaException;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.exception.handle.FlamesRuntimeExceptionHandler;
import com.severalcircles.flames.frontend.FlamesUserContext;
import com.severalcircles.flames.frontend.data.user.embed.UserDataEmbed;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserContextData implements FlamesUserContext {
    @Override
    public void execute(UserContextInteractionEvent event) {
        FlamesUser target;
        try {
            target = FlamesDataManager.getUser(Objects.requireNonNull(event.getOption("user")).getAsUser().getId());
        } catch (ConsentException e) {
            e.printStackTrace();
            event.reply("That user isn't using Flames yet.").queue();
            return;
        } catch (UnsupportedOperationException e) {
            event.replyEmbeds(new FlamesRuntimeExceptionHandler(new FlamesMetaException("Flames does not have User Data for itself"), UserContextData.class).handleThenGetFrontend()).complete();
            return;
        } catch (IOException e) {
            event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
            return;
        }
        event.replyEmbeds(new UserDataEmbed(event.getTarget(), target).get()).queue();
    }
}
