/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.data.FlamesDataManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuildAddEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public static void register(JDA api) {
        api.addEventListener(new GuildAddEvent());
        Logger.getGlobal().log(Level.FINE, "Registering " + MessageEvent.class.getName());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        super.onGuildJoin(event);
        try {
            FlamesDataManager.newGuild(event.getGuild());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
