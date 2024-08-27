/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data;

import com.severalcircles.flames.data.legacy.server.LegacyFlamesServer;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

/**
 * The FlamesServer class represents a Discord server in the Flames system.
 * It extends the FlamesDatatype class.
 */
public class FlamesServer extends FlamesDatatype {
    /**
     * The variable "id" is a string that represents the ID of a FlamesServer object.
     * It is used to uniquely identify a server in the Flames system.
     *
     * This variable is declared in the FlamesServer class, which represents a Discord server in the Flames system.
     * The FlamesServer class extends the FlamesDatatype class and defines various fields and methods related to a server.
     *
     * Below are some related methods in the FlamesServer class:
     *
     * - setId(String id): This method sets the ID of the server.
     *
     * - getId(): This method returns the ID of the server.
     *
     * - getHootenannyScores(): This method returns a map of hootenanny scores for the server.
     *
     * - setHootenannyScores(Map<String, Integer> hootenannyScores): This method sets the map of hootenanny scores for the server.
     *
     * - todayIsHootenannyDay(): This method checks if today is a hootenanny day for the server.
     *
     * The "id" variable is also used in the LegacyFlamesServer class, which is a legacy implementation of the FlamesServer class.
     * The LegacyFlamesServer class defines additional methods and fields related to a server.
     *
     * In this context, the "id" variable is used to store the ID of the server.
     *
     * Therefore, the "id" variable is a common identifier used in the Flames system to uniquely identify servers.
     */
    String id;
    /**
     * The score variable represents the score of a FlamesServer in the Flames system.
     * It is an integer value.
     */
    int score;
    /**
     * The hootenannyDay variable represents the day of the month for the hootenanny event.
     * It is an integer value.
     */
    int hootenannyDay;
    /**
     * Represents the hootenanny scores for a Flames server.
     *
     * The hootenanny scores are stored as a map, where the key is the user ID and the value is the score.
     */
    Map<String, Integer> hootenannyScores;

    /**
     * The FlamesServer class represents a Discord server in the Flames system.
     * It extends the FlamesDatatype class.
     *
     * @param legacyFlamesServer The legacy server object that is being used to initialize the FlamesServer object.
     *                           It contains the score, id, and hootenannyDay of the server.
     */
    public FlamesServer(LegacyFlamesServer legacyFlamesServer) {
        this.id = legacyFlamesServer.getId();
        this.score = legacyFlamesServer.getScore();
        this.hootenannyDay = legacyFlamesServer.getHootenannyDay();
    }

    /**
     * Sets the id of the FlamesServer.
     *
     * @param id the new id to set
     */
    public void setId(String id) {
        this.id = id;
    }
        /**
         * Retrieves the score of this FlamesServer.
         *
         * @return The score of this FlamesServer.
         */
        public int getScore() {
        return score;
    }

    /**
     * Sets the score for the FlamesServer.
     *
     * @param score the score to be set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retrieves the hootenanny day for the FlamesServer.
     *
     * @return The hootenanny day for the FlamesServer.
     */
    public int getHootenannyDay() {
        return hootenannyDay;
    }

    /**
     * Sets the hootenanny day of the FlamesServer.
     *
     * @param hootenannyDay the hootenanny day to set
     */
    public void setHootenannyDay(int hootenannyDay) {
        this.hootenannyDay = hootenannyDay;
    }

    /**
     * Retrieves the ID of the FlamesServer object.
     *
     * @return The ID of the FlamesServer object as a String.
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Constructs a new FlamesServer with the given parameters.
     *
     * @param id               the ID of the server
     * @param score            the score of the server
     * @param hootenannyDay    the hootenanny day of the server
     * @param hootenannyScores the hootenanny scores of the server
     */
    public FlamesServer(String id, int score, int hootenannyDay, Map<String, Integer> hootenannyScores) {
        this.id = id;
        this.score = score;
        this.hootenannyDay = hootenannyDay;
        this.hootenannyScores = hootenannyScores;
    }
    /**
     * Creates a FlamesServer object with the given ID.
     *
     * @param id The ID of the server.
     */
    public FlamesServer(String id) {
        this.id = id;
        this.score = 0;
        this.hootenannyDay = 1;
        this.hootenannyScores = new HashMap<>();
    }
    /**
     * The FlamesServer class represents a Discord server in the Flames system.
     * It extends the FlamesDatatype class.
     */
    public FlamesServer() {
        this.id = null;
    }

    /**
     * Retrieves the ID of the FlamesServer.
     *
     * @return The ID of the FlamesServer.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the hootenanny scores for the Flames server.
     *
     * @return A map containing the hootenanny scores, where the key is the user ID
     *         and the value is the score.
     */
    public Map<String, Integer> getHootenannyScores() {
        return hootenannyScores;
    }

    /**
     * Sets the hootenanny scores for the Flames server.
     *
     * @param hootenannyScores a Map containing the hootenanny scores of individual users
     */
    public void setHootenannyScores(Map<String, Integer> hootenannyScores) {
        this.hootenannyScores = hootenannyScores;
    }
    /**
     * Checks if today is Hootenanny Day based on the hootenannyDay field of the FlamesServer class.
     *
     * @return true if today is Hootenanny Day, false otherwise
     */
    public boolean todayIsHootenannyDay() {
        return hootenannyDay == new java.util.Date().getDate();
    }
}
