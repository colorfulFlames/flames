package com.severalcircles.flames.events.discord;

import net.dv8tion.jda.api.JDA;

import java.io.IOException;

public interface FlamesDiscordEvent {
    void register(JDA api);
//    void run() throws IOException;
}
