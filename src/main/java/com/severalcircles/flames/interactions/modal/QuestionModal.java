/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.modal;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.FlamesExceptionEmbed;
import com.severalcircles.flames.system.manager.FlamesQuestionManager;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;

import java.io.IOException;
import java.util.Locale;

public class QuestionModal extends FlamesModalInteraction {
    @Override
    public void execute(ModalInteraction interaction, FlamesUser user) {
        try {
            FlamesQuestionManager.answerQuestion(interaction.getModalId(), String.valueOf(interaction.getValue("answer")), user);
        } catch (IOException e) {
            new FlamesExceptionEmbed(Locale.ROOT, e);
        }
    }
}
