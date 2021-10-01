package com.severalcircles.flames.command;

import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.components.Button;

public class HelpCommand implements FlamesCommand {
    @Override
    public void execute(SlashCommandEvent event, FlamesUser sender) {
        event.reply(event.getUser().getName() + ", select a link below.").addActionRow(Button.link("https://flames.severalcircles.com", "Documentation"), Button.link("https://feedback.severalcircles.com", "Suggest a Feature or Report a Bug")).complete();
    }
}
