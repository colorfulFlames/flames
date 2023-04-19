/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.MyDataEmbed;
import com.severalcircles.flames.interactions.slash.FlamesCommand;
import com.severalcircles.flames.interactions.slash.FlamesSlashCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "mydata", description = "View your user data")
public class MyDataCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        interaction.replyEmbeds(new MyDataEmbed(user).get()).complete();
    }
}
