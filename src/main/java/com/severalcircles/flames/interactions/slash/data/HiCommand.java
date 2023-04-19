/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash.data;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.WelcomeBackEmbed;
import com.severalcircles.flames.interactions.slash.FlamesCommand;
import com.severalcircles.flames.interactions.slash.FlamesSlashCommand;
import com.severalcircles.flames.system.exception.ExceptionID;
import com.severalcircles.flames.system.exception.flames.AlreadyCollectedException;
import com.severalcircles.flames.system.manager.secondary.UserDataManager;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "hi", description = "Collect your daily bonus")
@ExceptionID("810")
public class HiCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        double bonus = 100 * user.getBonusMultiplier();
        try {
            user.addScore(user.collectBonus());
            new UserDataManager().saveUser(user);
        } catch (AlreadyCollectedException e) {
            interaction.reply("You've already collected your bonus for today!").queue();
        }
        interaction.replyEmbeds(new WelcomeBackEmbed(user.getLocale()).get(bonus, user)).queue();
    }
}
