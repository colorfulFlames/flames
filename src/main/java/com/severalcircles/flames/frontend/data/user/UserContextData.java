/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user;

import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesUserContext;
import com.severalcircles.flames.frontend.data.user.embed.UserDataEmbed;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserContextData implements FlamesUserContext {
    @Override
    public void execute(UserContextInteractionEvent event) {
        Logger.getGlobal().log(Level.INFO, "Executing UDATA");
        FlamesUser target;
        try {
            target = FlamesDataManager.readUser(event.getTarget());
        } catch (IOException | DataVersionException e) {
            e.printStackTrace();
            return;
        } catch (ConsentException e) {
            e.printStackTrace();
            event.reply("That user isn't using Flames yet.").queue();
            return;
        }
        event.replyEmbeds(new UserDataEmbed(event.getTarget(), target).get()).queue();
    }
}
