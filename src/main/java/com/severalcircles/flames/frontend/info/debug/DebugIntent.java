/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.info.debug;

import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesIntentResponse;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class DebugIntent implements FlamesIntentResponse {
    @Override
    public void execute(MessageReceivedEvent origMsg) {
        FlamesUser flamesUser;
        try {
            flamesUser = FlamesDataManager.readUser(origMsg.getAuthor());
        } catch (IOException | DataVersionException | ConsentException e) {
            e.printStackTrace();
            return;

        }
//        TODO: Fix debug embed
//        origMsg.getMessage().replyEmbeds(new TodayEmbed(origMsg.getAuthor(), flamesUser).get()).complete();
    }
}
