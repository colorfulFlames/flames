package com.severalcircles.flames.events.discord;

import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.flames.FlamesEvent;
import com.severalcircles.flames.features.NowEnteringGuild;
import com.severalcircles.flames.system.WhatTheFuckException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MemberAddEvent extends ListenerAdapter implements FlamesDiscordEvent {
    @Override
    public void register(JDA api) {
        api.addEventListener(new MemberAddEvent());
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        System.out.println("Guild add member");
//        super.onGuildMemberJoin(event);
        FlamesUser user;
        try {
            user = FlamesData.readUser(event.getUser().getId(), false);
        } catch (IOException | WhatTheFuckException e) {
            e.printStackTrace();
            return;
        }
    NowEnteringGuild.welcomeUser(user, event.getUser(), event.getGuild());
    }
}