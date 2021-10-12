/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.data.base.ConsentException;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MemberAddEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public void register(JDA api) {
        api.addEventListener(new MemberAddEvent());
    }
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        System.out.println("Guild add member");
//        super.onGuildMemberJoin(event);
        FlamesUser user;
        try {
            user = FlamesDataManager.readUser(event.getUser());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ConsentException ignored) {

        }
//    NowEnteringGuild.welcomeUser(user, event.getUser(), event.getGuild());
    }
}
