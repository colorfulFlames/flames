/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.intents;

import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesIntentResponse;
import com.severalcircles.flames.frontend.data.user.embed.UserDataEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class MyDataIntent implements FlamesIntentResponse {
    @Override
    public void execute(MessageReceivedEvent origMsg) {
        FlamesUser flamesUser;
        try {
            flamesUser = FlamesDataManager.readUser(origMsg.getAuthor());
        } catch (IOException | DataVersionException | ConsentException e) {
            e.printStackTrace();
            return;
        }
        origMsg.getChannel().sendMessageEmbeds(new UserDataEmbed(origMsg.getAuthor(), flamesUser).get()).complete();
    }
}
