/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.ConversationEmbed;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "conversation", description = "Get information about the current conversation")
public class ConversationCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        interaction.replyEmbeds(new ConversationEmbed(user.getLocale(), interaction.getChannel()).get()).complete();
    }
}
