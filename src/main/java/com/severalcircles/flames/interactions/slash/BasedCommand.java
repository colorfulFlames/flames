/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.manager.FlamesQuestionManager;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.io.IOException;

@ExceptionID("810")
@FlamesCommand(name = "based", description = "Based")
public class BasedCommand extends FlamesSlashCommand {

    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
            interaction.reply(FlamesQuestionManager.getAnswer("based")).queue();
    }
}
