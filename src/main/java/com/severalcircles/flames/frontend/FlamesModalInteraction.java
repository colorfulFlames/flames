/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.events.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.modals.Modal;

public interface FlamesModalInteraction {
    void execute(ModalInteractionEvent event, FlamesUser sender);
    Modal get(FlamesUser user);
}
