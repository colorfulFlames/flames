/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.frontend.info;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
//import net.dv8tion.jda.api.interactions.components.Button;

public class HelpCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandInteractionEvent event, FlamesUser sender) {
        event.reply(event.getUser().getName() + ", select a link below.").addActionRow(Button.link("https://flames.severalcircles.com", "Documentation"), Button.link("https://github.com/colorfulFlames/flames/issues/new/choose", "Suggest a Feature or Report a Bug"), Button.link("https://discord.gg/eDbwAYzN6B", "Join the Support Server")).complete();
    }
}
