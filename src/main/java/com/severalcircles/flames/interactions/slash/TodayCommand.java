/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.TodayEmbed;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "today", description = "Find out what today is all about")
public class TodayCommand extends FlamesSlashCommand{
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        interaction.replyEmbeds(new TodayEmbed(user.getLocale()).get()).queue();
    }
}
