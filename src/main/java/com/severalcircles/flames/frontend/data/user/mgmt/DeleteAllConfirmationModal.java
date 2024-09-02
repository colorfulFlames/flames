/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.mgmt;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.ModalInteractionEvent;
import com.severalcircles.flames.frontend.FlamesModalInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Locale;
import java.util.ResourceBundle;

public class DeleteAllConfirmationModal implements FlamesModalInteraction {
    @Override
    public void execute(ModalInteractionEvent event, FlamesUser sender) {

    }

    @Override
    public Modal get(FlamesUser user) {
        ResourceBundle local = Flames.local(Locale.forLanguageTag(user.getLang()));
        Modal.create("delete-all-confirmation:" + user.getId(), local.getString("title"))
                .addActionRow(Button.danger("delete-all-yes:" + user.getId(), local.getString("confirm")), Button.secondary("delete-all-no:" + user.getId(), local.getString("cancel")))
                .build();
        return null;
    }
}
