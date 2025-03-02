/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.user;

import java.util.*;

public class UserEntities {
    public UserEntities() {
        entities = new HashMap<>();
//        entities.put("Flames", new UserEntity("Flames", "1", 1, 0));
    }
    public List<UserEntity> getTopAndBottom() {
        List<UserEntity> topAndBottom = new LinkedList<>();
            List<UserEntity> highCandidates = new LinkedList<>();
            List<UserEntity> lowCandidates = new LinkedList<>();
            entities.forEach((k,v) -> {
                if (v.getHappyCount() > v.getSadCount()) highCandidates.add(v);
                else lowCandidates.add(v);
            });
            highCandidates.sort(Comparator.comparingInt(UserEntity::getHappyCount).reversed());
            if (highCandidates.size() < 3) {
                for (int i = highCandidates.size(); i < 3; i++) {
                    highCandidates.add(new UserEntity("", 0, 0, 0));
                }
            }
            if (lowCandidates.size() < 3) {
                for (int i = lowCandidates.size(); i < 3; i++) {
                    lowCandidates.add(new UserEntity("", 0, 0, 0));
                }
            }
            lowCandidates.sort(Comparator.comparingInt(UserEntity::getSadCount).reversed());
            topAndBottom.add(highCandidates.get(0));
            topAndBottom.add(highCandidates.get(1));
            topAndBottom.add(highCandidates.get(2));
            topAndBottom.add(lowCandidates.get(0));
            topAndBottom.add(lowCandidates.get(1));
            topAndBottom.add(lowCandidates.get(2));
        return topAndBottom;
    }
    private Map<String, UserEntity> entities;

    public Map<String, UserEntity> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, UserEntity> entities) {
        this.entities = entities;
    }

    public void addEntity(String name, boolean happy) {
        if (entities.containsKey(name)) {
            UserEntity entity = entities.get(name);
            if (happy) {
                entity.setHappyCount(entity.getHappyCount() + 1);
            } else {
                entity.setSadCount(entity.getSadCount() + 1);
            }
        } else {
            if (happy) {
                entities.put(name, new UserEntity(name, 1, 1, 0));
            } else {
                entities.put(name, new UserEntity(name, 1, 0, 1));
            }
        }
    }
    public Properties createData() {
        Properties properties = new Properties();
        entities.forEach((key, value) -> {
            properties.put(key + ".name", value.getName());
            properties.put(key + ".count", String.valueOf(value.getCount()));
            properties.put(key + ".happyCount", String.valueOf(value.getHappyCount()));
            properties.put(key + ".sadCount", String.valueOf(value.getSadCount()));
        });
//        System.out.println("d" + properties);
        return properties;
    }

}
