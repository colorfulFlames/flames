/*
 * Copyright (c) 2021 Several Circles.
 */

package com.severalcircles.flames.data.user;

import com.severalcircles.flames.data.FlamesData;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserRelationships implements FlamesData {
    private Map<String, Integer> relationships;
    @Override
    public Properties createData() {
        Properties properties = new Properties();
        if (relationships.size() == 0) {
            relationships.put("0", 0);
        }
        relationships.forEach((key, value) -> properties.put(key + "", value + ""));
        return properties;
    }

    public UserRelationships(Map<String, Integer> relationships) {
        this.relationships = relationships;
    }
    public UserRelationships() {
        this.relationships = new HashMap<>();
        this.relationships.put("0", 0);
    }
    public void addRelationship(String id, int score) {
        if (!relationships.containsKey(id)) relationships.put(id, score);
        else relationships.put(id, relationships.get(id) + score);
    }
    public void setRelationships(Map<String, Integer> relationships) {
        this.relationships = relationships;
    }

    public Map<String, Integer> getRelationships() {
        return relationships;
    }
}
