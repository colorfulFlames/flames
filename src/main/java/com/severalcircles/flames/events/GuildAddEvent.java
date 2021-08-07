package com.severalcircles.flames.events;

import com.severalcircles.flames.data.base.FlamesDatabase;
import com.severalcircles.flames.data.guild.FlamesGuild;
import discord4j.core.object.entity.Guild;

import java.sql.SQLException;

public class GuildAddEvent implements FlamesDiscordEvent {
    @SuppressWarnings("CanBeFinal")
    Guild guild;
    public GuildAddEvent(Guild guild) {
        this.guild = guild;
    }
    public void run() throws SQLException {
        FlamesDatabase fd = new FlamesDatabase();
        fd.addGuild(new FlamesGuild(this.guild));
    }
}
