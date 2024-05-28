/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.frontend;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.amiguito.Amiguito;
import com.severalcircles.flames.amiguito.AmiguitoDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.LayoutComponent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AmiguitoCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) throws ConsentException, IOException {
        ResourceBundle local = Flames.local();
        Amiguito amiguito;
        TextInput nameInput = TextInput.create(local.getString("amiguito.modal.field1.id"), local.getString("amiguito.modal.field1.name"), TextInputStyle.SHORT).build();
        Modal modal = Modal.create(local.getString("id" + sender.getDiscordId()), local.getString("amiguito.modal"))
                .addComponents((LayoutComponent) nameInput)
                .build();
        try {
            amiguito = AmiguitoDataManager.load(event.getUser().getId());
        } catch (FileNotFoundException e) {
            Logger.getGlobal().warning("Amiguito file not found for user " + event.getUser().getId());
            event.replyModal(modal).queue();
            return;
        }

        event.replyEmbeds(new AmiguitoInfoEmbed(amiguito, sender, event.getUser()).get());
    }
}
