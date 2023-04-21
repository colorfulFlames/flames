/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.interactions.button.ConsentButtons;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.secondary.UserDataManager;
import com.severalcircles.flames.system.manager.primary.FlamesInteractionManager;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@ExceptionID("601")
public class InteractionEvents extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);
        Flames.getFlogger().fine("Received slash command interaction " + event.getName());
        FlamesInteractionManager.getCommandInteraction(event.getName()).execute(event, new UserDataManager().loadUser(event.getUser()));
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
        Flames.getFlogger().fine("Received button interaction " + event.getComponentId());
        if (event.getComponentId().equals("noQuotes") | event.getComponentId().equals("decline") | event.getComponentId().equals("consent")) {
                new ConsentButtons().execute(event, new UserDataManager().loadUser(event.getUser()));

        }
            FlamesInteractionManager.getButtonInteraction(event.getComponentId()).execute(event, new UserDataManager().loadUser(event.getUser()));
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        super.onModalInteraction(event);
        Flames.getFlogger().fine("Received modal interaction " + event.getModalId());
            FlamesInteractionManager.getModalInteraction(event.getModalId()).execute(event, new UserDataManager().loadUser(event.getUser()));


    }
}
