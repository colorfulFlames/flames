/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.frontend;

//import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;


import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public interface FlamesDropdown {
    void execute(GenericSelectMenuInteractionEvent event, FlamesUser sender);
    StringSelectMenu get(FlamesUser user);
}
