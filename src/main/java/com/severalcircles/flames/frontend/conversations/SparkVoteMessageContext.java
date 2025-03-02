/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.conversations;

import com.severalcircles.flames.conversations.SparkConversation;
import com.severalcircles.flames.exception.AlreadyVotedException;
import com.severalcircles.flames.frontend.FlamesMessageContext;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;

import java.util.Objects;

public class SparkVoteMessageContext implements FlamesMessageContext {
    @Override
    public void execute(MessageContextInteraction event) {
        SparkConversation sparkConversation = SparkConversation.sparkConversations.get(Objects.requireNonNull(event.getChannel()).getId());
        if (sparkConversation != null) {
            event.reply("Thank you!").setEphemeral(true).queue();
            try {
                sparkConversation.addMessageVote(event.getTarget(), event.getUser());
            } catch (AlreadyVotedException e) {
                event.reply("Sorry, you can only vote once.").setEphemeral(true).queue();
            }
        } else {
            event.reply("There is no active spark conversation in this channel.").setEphemeral(true).queue();
        }

    }
}
