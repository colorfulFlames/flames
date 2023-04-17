/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.exception.FlamesExceptionHandler;
import com.severalcircles.flames.system.exception.flames.FlamesException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "test", description = "Run a test by ID.", options = {
        @FlamesCommandOption(name = "id", description = "The ID of the test to run.", type = OptionType.STRING, required = true)
})
public class TestCommand extends FlamesSlashCommand{
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        switch (interaction.getOption("id").getAsString()) {
            case "1":
                interaction.replyEmbeds(new FlamesExceptionHandler(new FlamesException("Test exception")).getEmbed().get()).complete();
            default:
                interaction.reply("No command with that ID.").queue();
        }
        interaction.reply("Test command executed.").queue();
    }
}
