/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.data.legacy.user.LegacyFlamesUser;
import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.exception.handle.FlamesExceptionHandler;
import com.severalcircles.flames.frontend.FlamesDropdown;
import com.severalcircles.flames.frontend.data.user.mgmt.DestructiveDropdown;
import com.severalcircles.flames.frontend.data.user.mgmt.UserManagementDropdown;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.component.GenericSelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectMenuEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public static final Map<String, FlamesDropdown> dropdownMap = Map.of(
            "usr-mgmt", new UserManagementDropdown(),
            "destructive-dropdown", new DestructiveDropdown()
    );
    public void register(JDA api) {
        api.addEventListener(this);
    }

    @Override
    public void onGenericSelectMenuInteraction(@NotNull GenericSelectMenuInteractionEvent event) {
        super.onGenericSelectMenuInteraction(event);
        String id = event.getComponentId();
        String[] parts = id.split(":");
        FlamesUser user;
        try {
            user = FlamesDataManager.getUser(event.getUser().getId());
        } catch (ConsentException e) {
            event.replyEmbeds(new FlamesExceptionHandler(e).handleThenGetFrontend()).queue();
            return;
        } catch (IOException e) {
            event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).queue();
            return;
        }
        for (String part : parts) {
            System.out.println(part);
        }
        System.out.println(Arrays.toString(parts));
        System.out.println(user.getID() +  "!=" + parts[1] + " is " + (!Objects.equals(user.getID(), parts[1])));
        if (!Objects.equals(user.getID(), parts[1])) {
            event.reply("You can't do that!").setEphemeral(true).queue();
            return;
        }
        dropdownMap.get(parts[0]).execute(event, user);

    }
}
