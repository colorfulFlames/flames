package com.severalcircles.flames.command;

import com.severalcircles.flames.data.user.FlamesUser;
import discord4j.core.event.domain.message.MessageCreateEvent;

public interface Command {
    void execute(MessageCreateEvent event, FlamesUser sender);
}
