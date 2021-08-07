package com.severalcircles.flames.data.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserStatsTest {
    private static UserStats stats = new UserStats();
    @Test
    void getExp() {
        assert stats.getExp() == 0;
    }

    @Test
    void getLevel() {
        assert stats.getLevel() == 1;
    }

    @Test
    void addExp() {
        assert stats.addExp(1);
        stats = new UserStats();
    }

    @Test
    void checkLevelUp() {
        assertFalse(stats.checkLevelUp());
    }
}