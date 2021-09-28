package com.severalcircles.flames.buttonaction.data.deleteuserdata;

import com.severalcircles.flames.buttonaction.ButtonAction;
import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.system.WhatTheFuckException;
import com.severalcircles.flames.system.updater.FlamesUpdater;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;

import java.io.IOException;

public class FixUserDataButtonAction implements ButtonAction {
    @Override
    public void execute(ButtonClickEvent event, FlamesUser user) throws IOException {
        event.deferEdit();
        try {
            FlamesData.readUser(user.getDiscordId(), true);
        } catch (WhatTheFuckException e) {
            e.printStackTrace();
        }
    event.editMessage("Your User Data is up to date.").complete();
    }
}
