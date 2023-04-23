/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.GlobalDataEmbed;
import com.severalcircles.flames.interactions.slash.FlamesCommand;
import com.severalcircles.flames.interactions.slash.FlamesSlashCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "globaldata", description = "See the average and global score")
public class GlobalDataCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        interaction.replyEmbeds(new GlobalDataEmbed(user.getLocale()).get()).queue();
    }
}
