/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.frontend.conversations.SparkVoteMessageContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageContextEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public static final Map<String, SparkVoteMessageContext> messageContexts = new HashMap<>();

    public void register(JDA api) {
        messageContexts.put("Spark: Vote", new SparkVoteMessageContext());
//        Flames.commandDataList.add(Commands.context(Command.Type.MESSAGE, "Spark: Vote"));
        Flames.commandDataList.add(Commands.context(Command.Type.MESSAGE, "Spark: Vote"));
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        Logger.getGlobal().log(Level.INFO, "Recieved MSGCON interaction: " + event.getName());
        super.onMessageContextInteraction(event);
        for (Map.Entry<String, SparkVoteMessageContext> entry: messageContexts.entrySet()) {
            if (event.getName().equals(entry.getKey())) {
                entry.getValue().execute(event);
            }
        }
    }
}
