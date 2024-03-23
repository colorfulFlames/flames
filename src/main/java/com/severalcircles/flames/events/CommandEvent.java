/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.FlamesException;
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
        System.out.println("Slash command detected");
        //noinspection ResultOfMethodCallIgnored
        event.deferReply(true);
        System.out.println(event.getName());
        for (Map.Entry<String, FlamesCommand> entry : Flames.commandMap.entrySet()) {
//            System.out.println(entry.getKey());
//            System.out.println(entry.getKey().contains(event.getName()));
            if (entry.getKey().contains(event.getName())) {
                try {
//                    System.out.println(Flames.commandMap.get(entry.getKey()));
                    FlamesUser fl = FlamesDataManager.readUser(event.getUser());
//                    throw new BadArgumentsException(fl, "This is a shitpost");
                    Flames.commandMap.get(entry.getKey()).execute(event, fl);
                } catch (FlamesException e) {
                    event.replyEmbeds(e.getHandler().handleThenGetFrontend()).complete();
                } catch (Exception e) {
                    event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
                }
            }
        }
    }).start();}}
