/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.ConsentManager;
import com.severalcircles.flames.system.manager.UserDataManager;
import com.severalcircles.flames.system.manager.FlamesInteractionManager;
import com.severalcircles.flames.system.exception.flames.ConsentException;
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
        try {
            FlamesInteractionManager.getCommandInteraction(event.getName()).execute(event, new UserDataManager().loadUser(event.getUser()));
        } catch (IOException | ConsentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
        Flames.getFlogger().fine("Received button interaction " + event.getComponentId());
        try {
            FlamesInteractionManager.getButtonInteraction(event.getComponentId()).execute(event, new UserDataManager().loadUser(event.getUser()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ConsentException e) {
            ConsentManager.handleConsentException(event.getUser(), e.getConsent());
        }
    }
}
