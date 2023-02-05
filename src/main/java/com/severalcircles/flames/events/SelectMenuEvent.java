/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.data.user.UserDataDropdown;
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
        FlamesUser flamesUser;
        try {
            flamesUser = FlamesDataManager.readUser(event.getUser());
        } catch (IOException | DataVersionException e) {
            e.printStackTrace();
            return;
        } catch (ConsentException e) {
            e.printStackTrace();
            //noinspection ResultOfMethodCallIgnored
            event.reply("Please agree to the consent prompt first.");
            return;
        }
        String[] menuInputData = event.getValues().toString().replace("[", "").replace("]", "").split(":");
        for (String inputDatum : menuInputData) {
            System.out.println(inputDatum);
        }
        System.out.println("all done :)");
        if (menuInputData[0].equals("DataDropdown")) {
            if (!event.getUser().getId().equals(menuInputData[1])) //noinspection ResultOfMethodCallIgnored
                event.reply(" ");
            else new UserDataDropdown().execute(event, menuInputData, flamesUser);
        }
    }
}
