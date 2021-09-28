package com.severalcircles.flames.events.discord;

import com.severalcircles.flames.buttonaction.*;
import com.severalcircles.flames.buttonaction.data.FunFactsButtonAction;
import com.severalcircles.flames.buttonaction.data.ManageUserDataButtonAction;
import com.severalcircles.flames.buttonaction.data.MyDataButtonAction;
import com.severalcircles.flames.buttonaction.data.StatsButtonAction;
import com.severalcircles.flames.buttonaction.data.deleteuserdata.DeleteUserDataButtonAction;
import com.severalcircles.flames.buttonaction.data.deleteuserdata.FixUserDataButtonAction;
import com.severalcircles.flames.buttonaction.data.deleteuserdata.NoDontButtonAction;
import com.severalcircles.flames.buttonaction.data.deleteuserdata.ReallyDeleteButtonAction;
import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.system.WhatTheFuckException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ButtonEvent extends ListenerAdapter implements FlamesDiscordEvent {
    public static Map<String, ButtonAction> buttonActionMap = new HashMap<>();
    @Override

    public void register(JDA api) {
        api.addEventListener(new ButtonEvent());
        buttonActionMap.put("consentn't", new ConsentButtonAction());
        buttonActionMap.put("consent", new ConsentButtonAction());
        buttonActionMap.put("gdata", new GlobalDataButton());
        buttonActionMap.put("stats", new StatsButtonAction());
        buttonActionMap.put("funFacts", new FunFactsButtonAction());
        buttonActionMap.put("manageData", new ManageUserDataButtonAction());
        buttonActionMap.put("updateData", new FixUserDataButtonAction());
        buttonActionMap.put("deleteData", new DeleteUserDataButtonAction());
        buttonActionMap.put("reallyDelete", new ReallyDeleteButtonAction());
        buttonActionMap.put("noDelete", new NoDontButtonAction());
        buttonActionMap.put("mydata", new MyDataButtonAction());
    }

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        super.onButtonClick(event);
        System.out.println(event.getComponentId());
        for (Map.Entry<String, ButtonAction> entry: buttonActionMap.entrySet()) {
            System.out.println(entry.getKey());
            if (entry.getKey().contains(entry.getKey())) {
                try {
                    System.out.println(1);
                    buttonActionMap.get(event.getComponentId()).execute(event, FlamesData.readUser(event.getUser().getId(), false));
                } catch (IOException | WhatTheFuckException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}