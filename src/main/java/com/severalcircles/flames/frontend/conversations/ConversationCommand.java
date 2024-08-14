/*
 * Copyright (c) 2022-2024 Several Circles.
 */

package com.severalcircles.flames.frontend.conversations;

import com.severalcircles.flames.conversations.ConversationsController;
import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.IOException;

public class ConversationCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, LegacyFlamesUser sender) throws ConsentException, IOException {
        //noinspection ResultOfMethodCallIgnored
        event.deferReply(true);
        if (ConversationsController.activeConversations.containsKey(event.getChannel().getId())) {
            event.replyEmbeds(new ConversationEmbed(event.getUser(), LegacyFlamesDataManager.readUser(event.getUser()), ConversationsController.activeConversations.get(event.getChannel().getId())).get()).queue();
        } else event.reply("There isn't a conversation in this channel yet. Why not start one?").queue();
    }
}
