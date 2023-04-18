/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames.interactions.button;

import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.frontend.DayPart;
import com.severalcircles.flames.system.exception.ExceptionID;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.ResourceBundle;
@ExceptionID("820")
public class AnswerQuestionButton extends FlamesButtonInteraction {
    private final DayPart dayPart;
    public AnswerQuestionButton(DayPart dayPart) {
        this.dayPart = dayPart;
    }
    @Override
    public void execute(ButtonInteraction interaction, FlamesUser user) {
        ResourceBundle local = ResourceBundle.getBundle("strings/WelcomeBack", user.getLocale());
        TextInput body = TextInput.create("body", local.getString("question." + dayPart), TextInputStyle.SHORT)
                .setPlaceholder(local.getString("defaultMessage." + dayPart))
                .setMinLength(30)
                .setMaxLength(1000)
                .build();


        Modal modal = Modal.create("modmail", "Modmail")
                .addActionRows(ActionRow.of(body))
                .build();

    }
}
