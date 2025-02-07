/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.exception.handle.FlamesExceptionHandler;
import com.severalcircles.flames.frontend.FlamesModalInteraction;
import com.severalcircles.flames.frontend.data.user.mgmt.DeleteAllConfirmationModal;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModalInteractionEvent extends ListenerAdapter implements FlamesDiscordEvent {
    private static final Map<String, FlamesModalInteraction> modalEvents = new HashMap<>();
    public static void register() {
        modalEvents.put("delete-all-confirmation", new DeleteAllConfirmationModal());
    }
    @Override
    public void onModalInteraction(@NotNull net.dv8tion.jda.api.events.interaction.ModalInteractionEvent event) {
        super.onModalInteraction(event);
        String name = event.getModalId();
        String[] parts = name.split(":");
        String id = parts[1];
        FlamesModalInteraction modal = modalEvents.get(parts[0]);
        try {
            modal.execute(this, FlamesDataManager.getUser(id));
        } catch (ConsentException e) {
            event.replyEmbeds(new FlamesExceptionHandler(e).handleThenGetFrontend()).queue();
        } catch (IOException e) {
            event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).queue();
        }

    }
}
