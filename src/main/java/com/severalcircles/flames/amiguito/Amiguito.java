/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.amiguito;

import com.severalcircles.flames.Flames;
import com.severalcircles.flames.amiguito.thoughts.Thought;
import com.severalcircles.flames.data.FlamesDataManager;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.exception.ConsentException;
import com.severalcircles.flames.external.analysis.FinishedAnalysis;
import net.dv8tion.jda.api.entities.User;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;

public class Amiguito {
    // MAX = highest a value can go
    // MIN = lowest a value can go
    // INIT = value when starting with a new Amiguito
    // THRESH = value at which a thought is added
    // TIME = how much a value changes each time the runMoodTime() function is called
     static final double MAX_FOOD = 100d;
     static final double MIN_FOOD = 0d;
     static final double INIT_FOOD = 0d;
     static final double HUNGRY_THRESH = 25d;
     static final double FOOD_TIME = -0.5d;
    // ---
     static final double MAX_WATER = 100d;
     static final double MIN_WATER = 0d;
    public static final double INIT_WATER = 0d;
      static final double THIRSTY_THRESH = 25d;
     static final double WATER_TIME = -0.5d;
    // ---
     static final double MAX_BOND = 100000d;
     static final double MIN_BOND = -MAX_BOND;
     static final double INIT_BOND = 0d;
    // ---
     static final double MAX_MOOD = 100d;
     static final double MIN_MOOD = -MAX_MOOD;
     static final double INIT_MOOD = 0d;
     static final double MOOD_TIME = 1d;
     static final double MOOD_TIME_TARGET = 0d;
    // ---
     static final double MAX_ENERGY = 100d;
     static final double MIN_ENERGY = -MAX_ENERGY;
     static final double INIT_ENERGY = 0d;
     static final double ENERGY_TIME = 0.25d;
    // ---
     static final double MAX_ENTITY = 1000d;
     static final double MIN_ENTITY = -MAX_ENTITY;
    // ---
     static final double MAX_FRIEND = 100000d;
     static final double MIN_FRIEND = -MAX_FRIEND;
    // ---
     static final double DEVIANCE_MAX = 5d;
     static final double DEVIANCE_MIN = -DEVIANCE_MAX;
     static final double DEVIANCE_INIT = 0d;
     static final double DEVIANCE_TIME_TARGET = 0d;
     static final double DEVIANCE_THRESH = 0.5d;
    // ---
     static final double THOUGHT_CHANCE = 10d; // The chance of Amiguito generating a thought from agreeing with the user
    static final double THOUGHT_MAX = 5; // The maximum number of thoughts that can be active at once;
    // ---
     static final double RUN_TIME = 1d; // The time at which Amiguito's mood will be updated in minutes
    private FlamesUser caretaker; // FlamesUser Amiguito is associated with
    private double bond; // How strong the bond is between Amiguito and its caretaker
    private double mood; // Amiguito's overall mood
    private double energy;  // Associated with Amiguito's mood, helps seperate "sad" from "angry"
    private double food; // How much food is in Amiguito's stomach
    private double water; // How much fluid is in Amiguito's stomach
    private double deviance; // Amiguito's tendancy to not agree with the caretaker
    private Map<String, Double> entities; // Amiguito's likes and dislikes
    private Map<String, Double> friends; // Other user's Amiguitos that Amiguito has doubleeracted with
    private List<Thought> thoughts;
    private String name;
    private Instant created;

    public Amiguito(FlamesUser caretaker, double bond, double mood, double energy, double food, double water, Map<String, Double> entities, Map<String, Double> friends, String name, double deviance, Instant created){
        this.caretaker = caretaker;
        this.bond = bond;
        this.mood = mood;
        this.energy = energy;
        this.food = food;
        this.water = water;
        this.entities = entities;
        this.friends = friends;
//        this.thoughts = thoughts;
        this.name = name;
        this.deviance = deviance;
        thoughts = new ArrayList<>();
        this.created = created;
    }
    public Amiguito(FlamesUser caretaker, String name) {
        this.caretaker = caretaker;
        if (this.caretaker == null) throw new IllegalArgumentException("Caretaker cannot be null. Why are you making me sad?");
        this.bond = INIT_BOND;
        this.mood = INIT_MOOD;
        this.energy = INIT_ENERGY;
        this.food = INIT_FOOD;
        this.water = INIT_WATER;
        this.entities = new HashMap<>();
        this.friends = new HashMap<>();
        this.thoughts = new ArrayList<>();
        this.name = name;
        this.deviance = DEVIANCE_INIT;
        this.created = Instant.now();
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public static Amiguito fromProperties(Properties properties) throws ConsentException, IOException {
        System.out.println(properties.toString());
        FlamesUser caretaker = FlamesDataManager.readUser(properties.getProperty("caretaker"), false);
        double bond = Double.parseDouble(properties.getProperty("bond"));
        double mood = Double.parseDouble(properties.getProperty("mood"));
        double energy = Double.parseDouble(properties.getProperty("energy"));
        double food = Double.parseDouble(properties.getProperty("food"));
        double water = Double.parseDouble(properties.getProperty("water"));
        double deviance = Double.parseDouble(properties.getProperty("deviance"));
        Instant created = Instant.ofEpochMilli(Long.parseLong(properties.getProperty("created")));
        Map<String, Double> entities = new HashMap<>();
        Map<String, Double> friends = new HashMap<>();
        String name = properties.getProperty("name");
        properties.forEach((key, value) -> {
            if (key.toString().startsWith("entity.")) {
                entities.put(key.toString().substring(7), Double.parseDouble(value.toString()));
            } else if (key.toString().startsWith("friend.")) {
                friends.put(key.toString().substring(7), Double.parseDouble(value.toString()));
            }
        });
        return new Amiguito(caretaker, bond, mood, energy, food, water, entities, friends, name, deviance, created);
    }

    /**
     * Checks that stats are within their defined ranges and corrects them if not.
     */
    public void validateStats() {
        food = Math.min(food, MAX_FOOD);
        food = Math.max(food, MIN_FOOD);
        water = Math.min(water, MAX_WATER);
        water = Math.max(water, MIN_WATER);
        bond = Math.min(bond, MAX_BOND);
        bond = Math.max(bond, MIN_BOND);
        mood = Math.min(mood, MAX_MOOD);
        mood = Math.max(mood, MIN_MOOD);
        energy = Math.min(energy, MAX_ENERGY);
        energy = Math.max(energy, MIN_ENERGY);
        friends.forEach((friend, value) -> {
            friends.put(friend, Math.min(value, MAX_FRIEND));
            friends.put(friend, Math.max(value, MIN_FRIEND));
        });
        entities.forEach((entity, value) -> {
            entities.put(entity, Math.min(value, MAX_ENTITY));
            entities.put(entity, Math.max(value, MIN_ENTITY));
        });

    }
    public FlamesUser getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(FlamesUser caretaker) {
        this.caretaker = caretaker;
    }

    public double getBond() {
        return bond;
    }

    public void setBond(double bond) {
        this.bond = bond;
    }
    public void addBond(double bond) {
        this.bond += bond;
    }
    public double getMood() {
        return mood;
    }

    public void setMood(double mood) {
        this.mood = mood;
    }
    public void addMood(double mood) {
        this.mood += mood;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
    public void addEnergy(double energy) {
        this.energy += energy;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }
    public void addFood(double food) {
        this.food += food;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }
    public void addWater(double water) {
        this.water += water;
    }

    public Map<String, Double> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, Double> entities) {
        this.entities = entities;
    }
    public void addEntity(String entity, double emotion) {
        if (entities.containsKey(entity)) {
            entities.put(entity, entities.get(entity) + emotion);
        } else entities.put(entity, emotion);
    }

    public Map<String, Double> getFriends() {
        return friends;
    }

    public void setFriends(Map<String, Double> friends) {
        this.friends = friends;
    }
    public void addFriend(String friendId, double bond) {
        if (friends.containsKey(friendId)) {
            friends.put(friendId, friends.get(friendId) + bond);
        } else {
            friends.put(friendId, bond);
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void runMoodTime() {
        if (mood < MOOD_TIME_TARGET) mood += MOOD_TIME;
        else if (mood > MOOD_TIME_TARGET) mood -= MOOD_TIME;
        if (deviance < DEVIANCE_TIME_TARGET) deviance += new Random().nextDouble(DEVIANCE_MAX);
        else if (deviance > DEVIANCE_TIME_TARGET) deviance -= new Random().nextDouble(DEVIANCE_MIN);
        food += FOOD_TIME;
        water += WATER_TIME;
        energy += ENERGY_TIME;
    }
    public Properties createProperties() {
        Properties properties = new Properties();
        properties.setProperty("caretaker", caretaker.getDiscordId());
        properties.setProperty("bond", String.valueOf(bond));
        properties.setProperty("mood", String.valueOf(mood));
        properties.setProperty("energy", String.valueOf(energy));
        properties.setProperty("food", String.valueOf(food));
        properties.setProperty("water", String.valueOf(water));
        properties.setProperty("deviance", String.valueOf(deviance));
        properties.setProperty("created", String.valueOf(created.toEpochMilli()));
        entities.forEach((entity, value) -> properties.setProperty("entity." + entity, String.valueOf(value)));
        friends.forEach((friend, value) -> properties.setProperty("friend." + friend, String.valueOf(value)));
        properties.setProperty("name", name);
        return properties;
    }
    public void processMessage(FinishedAnalysis finishedAnalysis) {
        double amiguitoScore = (finishedAnalysis.getSentiment().getScore() * finishedAnalysis.getSentiment().getMagnitude()) + deviance;
        double regScore = finishedAnalysis.getSentiment().getScore() * finishedAnalysis.getSentiment().getMagnitude();
        amiguitoScore += deviance;
        this.mood += amiguitoScore;
        this.energy += regScore;
        if (amiguitoScore > regScore + DEVIANCE_THRESH) {
            addThought(Thought.DEVIANCE_DISAGREE);
        } else if (amiguitoScore < regScore - DEVIANCE_THRESH) {
            addThought(Thought.DEVIANCE_DISAGREE);
        } else if (new Random().nextDouble(100) < THOUGHT_CHANCE) {
            addThought(Thought.DEVIANCE_AGREE);
        }
    }

    public double getDeviance() {
        return deviance;
    }

    public void setDeviance(double deviance) {
        this.deviance = deviance;
    }

    public List<Thought> getThoughts() {
        return thoughts;
    }

    public void setThoughts(List<Thought> thoughts) {
        this.thoughts = thoughts;
    }
    public void addThought(Thought thought) {
            thoughts.add(thought);
            thought.getEffect().apply(this);
            refreshThoughts();
        }
    public void refreshThoughts() {
        if (!thoughts.contains(Thought.HUNGRY) && food < HUNGRY_THRESH) {
            thoughts.add(Thought.HUNGRY);
            Thought.HUNGRY.getEffect().apply(this);
        }
        if (!thoughts.contains(Thought.THIRSTY) && water < THIRSTY_THRESH) {
            thoughts.add(Thought.THIRSTY);
            Thought.THIRSTY.getEffect().apply(this);
        }
        thoughts.forEach(thought -> {
            switch (thought) {
                case HUNGRY:
                    if (food > HUNGRY_THRESH) {
                        thought.getEffect().clear(this);
                        thoughts.remove(thought);
                    }
                case THIRSTY:
                    if (water > THIRSTY_THRESH) {
                        thought.getEffect().clear(this);
                        thoughts.remove(thought);
                    }
            }
        });
        while (thoughts.size() > THOUGHT_MAX) { // If more than the max number of thoughts are active, remove the oldest one
            thoughts.get(0).getEffect().clear(this);
            thoughts.remove(0);
        }
    }
}