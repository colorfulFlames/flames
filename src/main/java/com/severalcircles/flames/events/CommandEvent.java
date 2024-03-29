package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.FlamesDiscordEvent;
import com.severalcircles.flames.exception.FlamesException;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CommandEvent extends ListenerAdapter implements FlamesDiscordEvent {

    private final Logger logger = Logger.getLogger(CommandEvent.class.getName());

    public void register(JDA api) {
        api.addEventListener(new CommandEvent());
        logger.log(Level.FINE, "Registering " + MessageEvent.class.getName());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        new Thread(() ->{
            logger.log(Level.FINE, "Slash command detected");

            logger.log(Level.FINE, event.getName());
            Flames.commandMap.entrySet().stream()
                    .filter(entry -> entry.getKey().contains(event.getName()))
                    .findFirst()
                    .ifPresentOrElse(entry -> {
                        try {
                            FlamesUser flu = FlamesDataManager.readUser(event.getUser());
                            entry.getValue().execute(event, flu);
                        } catch (FlamesException e) {
                            event.replyEmbeds(e.getHandler().handleThenGetFrontend()).complete();
                        } catch (Exception e) {
                            event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
                        }
                    }, () -> {
                        logger.log(Level.INFO, "No Command Found.");
                    });
        }).start();
    }
}