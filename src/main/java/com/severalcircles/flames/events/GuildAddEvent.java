package com.severalcircles.flames.events;

import com.severalcircles.flames.data.base.FlamesDatabase;
import com.severalcircles.flames.data.guild.FlamesGuild;
import discord4j.core.object.entity.Guild;
import discord4j.rest.entity.RestGuild;

import java.sql.SQLException;

public class GuildAddEvent implements FlamesDiscordEvent {
    Guild guild;
    public GuildAddEvent(Guild guild) {
        this.guild = guild;
    }
    public void run() throws SQLException {
        FlamesDatabase fd = new FlamesDatabase();
        fd.addGuild(new FlamesGuild(this.guild));
    }
}
