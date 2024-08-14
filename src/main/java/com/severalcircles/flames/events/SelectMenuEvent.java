/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.ConsentException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectMenuEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public void register(JDA api) {
        api.addEventListener(this);
    }

    @Override
    public void onGenericSelectMenuInteraction(@NotNull GenericSelectMenuInteractionEvent event) {
        Logger.getGlobal().log(Level.INFO, "Dropdown recieved");
        super.onGenericSelectMenuInteraction(event);
        LegacyFlamesUser legacyFlamesUser;
        try {
            legacyFlamesUser = LegacyFlamesDataManager.readUser(event.getUser());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ConsentException e) {
            e.printStackTrace();
            event.reply("Please agree to the consent prompt first.").complete();
            return;
        }
        String[] menuInputData = event.getValues().toString().replace("[", "").replace("]", "").split(":");
        for (String inputDatum : menuInputData) {
            System.out.println(inputDatum);
        }
    }
}
