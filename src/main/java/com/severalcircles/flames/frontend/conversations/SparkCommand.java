/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.conversations;

import com.severalcircles.flames.conversations.SparkConversation;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Objects;

public class SparkCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        if (!SparkConversation.sparkConversations.isEmpty()) {
            event.reply("There is already a Spark conversation in progress!").setEphemeral(true).complete();
            return;
        }
        if (Objects.requireNonNull(event.getOption("question")).getAsString().length() > 255) {
            event.reply("The question must be less than 255 characters.").setEphemeral(true).complete();
            return;
        }
        SparkConversation.sparkConversations.put(event.getChannel().getId(), new SparkConversation(event.getChannel(), Objects.requireNonNull(event.getOption("question")).getAsString(), Objects.requireNonNull(event.getOption("minutes")).getAsInt()));
        event.replyEmbeds(new SparkStartEmbed(sender, event.getUser(), Objects.requireNonNull(event.getOption("question")).getAsString(), Objects.requireNonNull(event.getOption("minutes")).getAsInt()).get()).complete();
    }
}
