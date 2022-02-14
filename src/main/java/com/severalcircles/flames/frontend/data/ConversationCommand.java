/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data;

import com.severalcircles.flames.conversations.ConversationsController;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.message.fivehundred.GenericErrorMessage;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.io.IOException;

public class ConversationCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        if (ConversationsController.activeConversations.containsKey(event.getChannel().getId())) {
            try {
                event.replyEmbeds(new ConversationEmbed(event.getUser(), FlamesDataManager.readUser(event.getUser()), ConversationsController.activeConversations.get(event.getChannel().getId())).get()).queue();
            } catch (IOException | ConsentException | DataVersionException e) {
                event.replyEmbeds(new GenericErrorMessage(e).get()).queue();
            }

        }
        event.reply("There isn't a conversation in this channel yet. Why not start one?").queue();
    }
}
