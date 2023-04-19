/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.modal;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesExceptionEmbed;
import com.severalcircles.flames.system.exception.java.FlamesDataException;
import com.severalcircles.flames.system.manager.secondary.FlamesQuestionManager;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

import java.io.IOException;
import java.util.Locale;

public class QuestionModal extends FlamesModalInteraction {
    @Override
    public void execute(ModalInteraction interaction, FlamesUser user) {
        try {
//            Flames.getFlogger().fine(interaction.getModalId());
            FlamesQuestionManager.answerQuestion(interaction.getModalId(), interaction.getValue("answer").getAsString(), user);
        } catch (IOException e) {
            interaction.replyEmbeds(new FlamesExceptionEmbed(Locale.ROOT, new FlamesDataException(e.getMessage())).get()).setEphemeral(true).queue();
        }
        interaction.reply("Thank you for your answer.").setEphemeral(true).queue();
    }
}
