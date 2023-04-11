/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "based", description = "Based")
public class BasedCommand extends FlamesSlashCommand {

    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        interaction.reply("Based").queue();
    }
}
