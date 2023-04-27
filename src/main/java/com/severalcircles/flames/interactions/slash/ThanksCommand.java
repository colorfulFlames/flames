/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.conversations.today.Today;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.ThanksEmbed;
import com.severalcircles.flames.system.exception.flames.ConsentException;
import com.severalcircles.flames.system.manager.secondary.UserDataManager;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
@FlamesCommand(name = "thanks", description = "Thank someone for any reason, or just because you feel like it.", options = { @FlamesCommandOption(name = "user", description = "The user you want to thank.", type = OptionType.USER, required = true), @FlamesCommandOption(name = "message", description = "A message to send with your thanks.", type = OptionType.STRING, required = false)})
public class ThanksCommand extends FlamesSlashCommand {
    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        FlamesUser thankee = null;
        if (interaction.getOption("user").getAsUser().getId().equals(Flames.getApi().getSelfUser().getId())) {
            interaction.reply("You're very welcome.").setEphemeral(true).queue();
            return;
        }
        try {
            thankee = new UserDataManager().loadUserThrowConsent(interaction.getOption("user").getAsUser());
        } catch (ConsentException e) {
            Flames.getFlogger().warning("User " + interaction.getOption("user").getAsUser().getId() + " has not consented to data collection.");
            interaction.replyEmbeds(e.getEmbed().get()).setEphemeral(true).queue();
        }
        if (thankee == null) return;
        Flames.getFlogger().fine("Comparing " + user.getDiscordUser().getId() + " to " + thankee.getDiscordUser().getId());
        if (user.getDiscordUser().getId().equals(thankee.getDiscordUser().getId())) {
            Flames.getFlogger().finest("(they're the same)");
            interaction.reply("You can't thank yourself!").setEphemeral(true).queue();
            return;
        } else Flames.getFlogger().finest("(they're not the same)");
        if (Today.thanks.contains(user.getDiscordUser().getId())) {
            interaction.reply("You've already thanked someone today!").setEphemeral(true).queue();
            return;
        }
        try {
            String message = interaction.getOption("message").getAsString();
        } catch (NullPointerException e) {
            interaction.replyEmbeds(new ThanksEmbed(user, thankee, null).get()).queue();
            thankee.addScore(Double.parseDouble(ThanksEmbed.values.getString("bonus")));
            new UserDataManager().saveUser(thankee);
            return;
        }
        interaction.replyEmbeds(new ThanksEmbed(user, thankee, interaction.getOption("message").getAsString()).get()).queue();
        thankee.addScore(Double.parseDouble(ThanksEmbed.values.getString("bonus")));
        new UserDataManager().saveUser(thankee);
        Today.thanks.add(user.getDiscordUser().getId());
    }
}
