/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend.data.user.mgmt;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.io.IOException;

public class DeleteAllConfirmButtons implements FlamesButtonAction {
    @Override
    public void execute(ButtonInteractionEvent event, FlamesUser user) throws IOException {
        if (event.getComponentId().contains("delete-all-yes")) {
            FlamesDataManager.deleteUser(user.getID());
            event.reply("Data deleted. Thank you for using Flames.").queue();
        } else {
            event.reply("Data deletion cancelled.").queue();
        }
    }
}
