/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito.frontend;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesDropdown;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

public class AmiguitoSelectMenu implements FlamesDropdown {
    @Override
    public void execute(GenericSelectMenuInteractionEvent event, FlamesUser sender) {

    }

    @Override
    public StringSelectMenu get(FlamesUser user) {
        return StringSelectMenu.create(user.getDiscordId() + ":amiguito")
                .addOption("Amiguito Information", "amiInfo")
                .addOption("Kitchen", "amiKitchen")
                .build();
    }
}
