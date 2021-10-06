/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.api;

import com.severalcircles.flames.data.base.ConsentException;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.data.global.GlobalData;
import com.severalcircles.flames.system.Flames;
import com.severalcircles.flames.system.WhatTheFuckException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DataController {
    @GetMapping("/user/{id}")
    public String user(@PathVariable String id) {
        try {
            return FlamesDataManager.readUser(Flames.api.getUserById(id)).createData().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConsentException e) {
            return "looks like somebody forgot to ask for consent and got punished 🙈";
        } catch (NullPointerException e) {
            return "no that's not real";
        }
        return "whoops";
    }
    @GetMapping("/global")
    public String global() {
        try {
            GlobalData.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("participants", GlobalData.participants);
        jsonObject.put("globalScore", GlobalData.globalScore);
        jsonObject.put("averageScore", GlobalData.averageScore);
        return jsonObject.toString();
    }
}
