/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.api;

import com.severalcircles.flames.data.base.ConsentException;
import com.severalcircles.flames.data.base.FlamesDataManager;
import com.severalcircles.flames.system.Flames;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DataController {
    @GetMapping("/user/{id}")
    public String getUser(String id) {
        try {
            return FlamesDataManager.readUser(Flames.api.getUserById(id)).createData().toString();
        } catch (IOException e) {
            return e.toString();
        } catch (ConsentException e) {
            return "Lol that user really said no";
        }
    }
}
