/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.Consent;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandEvent extends ListenerAdapter implements FlamesDiscordEvent {

    public void register(JDA api) {
        api.addEventListener(new CommandEvent());
        Logger.getGlobal().log(Level.FINE, "Registering " + MessageEvent.class.getName());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        new Thread(() ->{
        //noinspection ResultOfMethodCallIgnored
        event.deferReply(true);
        for (Map.Entry<String, FlamesCommand> entry : Flames.commandMap.entrySet()) {
            if (entry.getKey().contains(event.getName())) {
                try {
//                    System.out.println(Flames.commandMap.get(entry.getKey()));
                    FlamesUser fl = FlamesDataManager.getUser(event.getUser().getId());
//                    throw new BadArgumentsException(fl, "This is a shitpost");
                    Flames.commandMap.get(entry.getKey()).execute(event, fl);
                } catch (ConsentException e) {
                    if (e.consentLevel == 0) Consent.getConsent(event.getUser());
                    event.replyEmbeds(e.getHandler().handleThenGetFrontend()).complete();
                } catch (Exception e) {
                    event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
                }
            }
        }
    }).start();}}
