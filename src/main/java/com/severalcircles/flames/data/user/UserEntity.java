/*
 * Copyright (c) 2024 Several Circles.
 */

package com.severalcircles.flames.data.user;

public class UserEntity {
    private String name;
    private int count;
    private int happyCount;
    private int sadCount;

    public UserEntity(String name, int count, int happyCount, int sadCount) {
        this.name = name;
        this.count = count;
        this.happyCount = happyCount;
        this.sadCount = sadCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getHappyCount() {
        return happyCount;
    }

    public void setHappyCount(int happyCount) {
        this.happyCount = happyCount;
    }

    public int getSadCount() {
        return sadCount;
    }

    public void setSadCount(int sadCount) {
        this.sadCount = sadCount;
    }
}
