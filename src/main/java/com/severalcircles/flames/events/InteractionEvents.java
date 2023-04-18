/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.interactions.button.ConsentButtons;
import com.severalcircles.flames.interactions.slash.ConsentCommand;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.UserDataManager;
import com.severalcircles.flames.system.manager.FlamesInteractionManager;
import com.severalcircles.flames.system.exception.flames.ConsentException;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
@ExceptionID("601")
public class InteractionEvents extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        Flames.getFlogger().fine("Received slash command interaction " + event.getName());
        if (event.getName().equals("consent")) {
            try {
                new ConsentCommand().execute(event, new UserDataManager().loadUser(event.getUser(), true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ConsentException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FlamesInteractionManager.getCommandInteraction(event.getName()).execute(event, new UserDataManager().loadUser(event.getUser(), false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConsentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
        Flames.getFlogger().fine("Received button interaction " + event.getComponentId());
        if (event.getComponentId().equals("noQuotes") | event.getComponentId().equals("decline") | event.getComponentId().equals("consent")) {
            try {
                new ConsentButtons().execute(event, new UserDataManager().loadUser(event.getUser(), true));
            } catch (IOException | ConsentException e) {
                e.printStackTrace();
            }
        }
        try {
            FlamesInteractionManager.getButtonInteraction(event.getComponentId()).execute(event, new UserDataManager().loadUser(event.getUser(), false));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConsentException ignored) {
        }
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        super.onModalInteraction(event);
        Flames.getFlogger().fine("Received modal interaction " + event.getModalId());
        try {
            FlamesInteractionManager.getModalInteraction(event.getModalId()).execute(event, new UserDataManager().loadUser(event.getUser(), false));
        } catch (IOException | ConsentException e) {
            throw new RuntimeException(e);
        }

    }
}
