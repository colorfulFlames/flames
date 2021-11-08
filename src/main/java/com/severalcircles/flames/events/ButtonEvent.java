/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.events;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.FlamesError;
import com.severalcircles.flames.data.DataVersionException;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.consent.Consent;
import com.severalcircles.flames.data.user.consent.ConsentException;
import com.severalcircles.flames.frontend.FlamesButtonAction;
import com.severalcircles.flames.frontend.data.user.*;
import com.severalcircles.flames.frontend.message.fourhundred.DataVersionErrorMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
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
//        buttonActionMap.put("gdata", new GlobalDataButton());
        buttonActionMap.put("stats", new StatsButtonAction());
        buttonActionMap.put("funFacts", new FunFactsButtonAction());
//        buttonActionMap.put("manageData", new ManageUserDataButtonAction());
        buttonActionMap.put("mydata", new MyDataButtonAction());
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        super.onButtonClick(event);
        System.out.println(event.getComponentId());
        if (event.getComponentId().equals("consent") | event.getComponentId().equals("consentn't")) {
            try {
                new ConsentButtonAction().execute(event, FlamesDataManager.readUser(event.getUser(), true));
            } catch (IOException | ConsentException e) {
                e.printStackTrace();
                Flames.incrementErrorCount();
            }
            return;
        }
        for (Map.Entry<String, FlamesButtonAction> entry: buttonActionMap.entrySet()) {
            System.out.println(entry.getKey());
            if (entry.getKey().equals(event.getComponentId())) {
                try {
                    System.out.println(1);
                    buttonActionMap.get(event.getComponentId()).execute(event, FlamesDataManager.readUser(event.getUser()));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    Logger.getGlobal().log(Level.INFO, "I don't care anymore");
                } catch (ConsentException e) {
                    if (e.consentLevel == 1) {
                        Consent.getConsent(event.getUser());
                    }
                } catch (DataVersionException e) {
                    event.replyEmbeds(new DataVersionErrorMessage((FlamesError) e).get()).complete();
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}