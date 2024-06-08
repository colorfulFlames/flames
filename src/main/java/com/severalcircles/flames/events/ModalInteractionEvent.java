/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.events;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ModalInteractionEvent extends ListenerAdapter implements FlamesDiscordEvent {
    @Override
    public void onModalInteraction(@NotNull net.dv8tion.jda.api.events.interaction.ModalInteractionEvent event) {
        super.onModalInteraction(event);
        String name;
    }
}
