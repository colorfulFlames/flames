/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.AboutEmbed;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

@FlamesCommand(name = "about", description = "About Flames")
public class AboutCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        interaction.replyEmbeds(new AboutEmbed(user.getLocale()).get()).complete();
    }
}
