/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.slash.data;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.interactions.slash.FlamesCommand;
import com.severalcircles.flames.interactions.slash.FlamesSlashCommand;
import com.severalcircles.flames.system.manager.secondary.FlamesQuestionManager;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.ResourceBundle;

@FlamesCommand(name = "question", description = "Answer a question to spice things up for other users")
public class QuestionCommand extends FlamesSlashCommand {

    @Override
    public void execute(SlashCommandInteraction interaction, FlamesUser user) {
        ResourceBundle local = ResourceBundle.getBundle("strings/Questions", user.getLocale());
        String question = FlamesQuestionManager.getQuestion();
        Modal modal = Modal.create("question:" + question + "." + user.getDiscordUser().getId(), local.getString(question))
                .addComponents(
                        ActionRow.of(TextInput.create("answer", local.getString("answer"), TextInputStyle.SHORT).build())
                )
                .build();
        Flames.getFlogger().fine(modal.getId());
        Flames.getFlogger().fine("Sending question " + question + " to " + user.getDiscordUser().getAsTag());
        interaction.replyModal(modal).queue();
        Flames.getFlogger().fine("Sent question " + question + " to " + user.getDiscordUser().getAsTag());
    }
}
