/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.data.ConsentException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.legacy.LegacyFlamesDataManager;
import com.severalcircles.flames.exception.FlamesException;
import com.severalcircles.flames.exception.handle.ExceptionHandler;
import com.severalcircles.flames.exception.handle.FlamesExceptionHandler;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.data.user.ConsentButtonAction;
import com.severalcircles.flames.frontend.data.user.MyDataButtonAction;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ButtonEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public static final Map<String, FlamesButtonAction> buttonActionMap = new HashMap<>();

    public void register(JDA api) {
        api.addEventListener(new ButtonEvent());
        buttonActionMap.put("consentn't", new ConsentButtonAction());
        buttonActionMap.put("consent", new ConsentButtonAction());
        buttonActionMap.put("noQotd", new ConsentButtonAction());
        buttonActionMap.put("mydata", new MyDataButtonAction());
        Logger.getGlobal().log(Level.FINE, "Registering " + MessageEvent.class.getName());
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        super.onButtonInteraction(event);
        System.out.println(event.getComponentId());
        if (event.getComponentId().equals("consent") | event.getComponentId().equals("consentn't")) {
            try {
                new ConsentButtonAction().execute(event, FlamesDataManager.getUser(event.getUser().getId(), true));
            } catch (FlamesException e) {
                event.replyEmbeds(new FlamesExceptionHandler(e).handleThenGetFrontend()).complete();
            } catch (Exception e) {
                event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
            }
            return;
        }
        for (Map.Entry<String, FlamesButtonAction> entry: buttonActionMap.entrySet()) {
            System.out.println(entry.getKey());
            if (entry.getKey().equals(event.getComponentId())) {
                try {
                    buttonActionMap.get(event.getComponentId()).execute(event, FlamesDataManager.getUser(event.getUser().getId()));
                } catch (IOException e) {
                    event.replyEmbeds(new ExceptionHandler(e).handleThenGetFrontend()).complete();
                } catch (ConsentException e) {
                    event.replyEmbeds(new FlamesExceptionHandler(e).handleThenGetFrontend()).complete();
                }
            }
        }
    }
}