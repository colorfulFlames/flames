/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.legacy.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserRelationships {
    private final Map<String, Integer> relationships;
    public Properties createData() {
        Properties properties = new Properties();
        if (relationships.isEmpty()) {
            relationships.put("0", 0);
        }
        relationships.forEach((key, value) -> properties.put(key, String.valueOf(value)));
        return properties;
    }

    public UserRelationships() {
        this.relationships = new HashMap<>();
        this.relationships.put("0", 0);
    }
    public void addRelationship(String id, int score) {
        if (!relationships.containsKey(id)) relationships.put(id, score);
        else relationships.put(id, relationships.get(id) + score);
    }

    public Map<String, Integer> getRelationships() {
        return relationships;
    }
}
