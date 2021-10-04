/*
 * Copyright (c) 2021.
 */

package com.severalcircles.flames.api;

import com.severalcircles.flames.data.base.FlamesData;
import com.severalcircles.flames.data.global.GlobalData;
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
            return FlamesData.getUserData(id);
        } catch (WhatTheFuckException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fuck";
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
