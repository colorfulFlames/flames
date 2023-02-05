/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import com.severalcircles.flames.frontend.message.fourhundred.ConsentErrorMessage;
import com.severalcircles.flames.frontend.message.fourhundred.DataVersionErrorMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
        System.out.println("Slash command detected");
        //noinspection ResultOfMethodCallIgnored
        event.deferReply(true);
        System.out.println(event.getName());
        for (Map.Entry<String, FlamesCommand> entry : Flames.commandMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getKey().contains(event.getName()));
            if (entry.getKey().contains(event.getName())) {
                try {
                    System.out.println(Flames.commandMap.get(entry.getKey()));
                    Flames.commandMap.get(entry.getKey()).execute(event, FlamesDataManager.readUser(event.getUser()));
                } catch (IOException e) {
                    e.printStackTrace();
                    Flames.incrementErrorCount();
                } catch (IllegalStateException e) {
                    Logger.getGlobal().log(Level.INFO, "Somebody pressed a button :3");
                } catch (ConsentException e) {
                    //noinspection ResultOfMethodCallIgnored
                    event.replyEmbeds(new ConsentErrorMessage(e).get());
                } catch (DataVersionException e) {
                    event.replyEmbeds(new DataVersionErrorMessage(e).get()).complete();
                    e.printStackTrace();
                    return;
                }
            }
        }
    }}
