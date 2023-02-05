/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.frontend.FlamesUserContext;
import com.severalcircles.flames.frontend.data.user.UserContextData;
import com.severalcircles.flames.frontend.thanks.ThanksContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserContextEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public static final Map<String, FlamesUserContext> userContexts = new HashMap<>();

    public void register(JDA api) {
        userContexts.put("User Data", new UserContextData());
        Flames.commandDataList.add(Commands.context(Command.Type.USER, "User Data"));
        userContexts.put("Give Thanks", new ThanksContext());
        Flames.commandDataList.add(Commands.context(Command.Type.USER, "Give Thanks"));
        api.addEventListener(new UserContextEvent());
    }
    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        Logger.getGlobal().log(Level.INFO, "Recieved UCON interaction: " + event.getName());
        super.onUserContextInteraction(event);
        userContexts.forEach((key, value) -> {System.out.println(key + value); if (key.equals(event.getName())) { value.execute(event);} });
    }
}
