/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

public interface FlamesDropdown {
    void execute(SelectMenuInteractionEvent event, String[] data, FlamesUser user);
    SelectMenu getDropdown(FlamesUser user);
}
