/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend.data;

import com.severalcircles.flames.conversations.ConversationsController;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class ConversationCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) throws ConsentException, IOException {
        //noinspection ResultOfMethodCallIgnored
        event.deferReply(true);
        if (ConversationsController.activeConversations.containsKey(event.getChannel().getId())) {
            event.replyEmbeds(new ConversationEmbed(event.getUser(), FlamesDataManager.readUser(event.getUser()), ConversationsController.activeConversations.get(event.getChannel().getId())).get()).queue();
        }
        event.reply("There isn't a conversation in this channel yet. Why not start one?").queue();
    }
}
