/*
 * Copyright (c) 2023 Several Circles.
 */

package com.severalcircles.flames.frontend.today;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodayTest {
    @Test
    void todayTest() {
        new ResetTodayRunnable().run();
        assertEquals(0, Today.highScore);
        assertEquals("Nobody yet!", Today.highUser);
        assertEquals(Today.quoteEmotion, 0);
        assertFalse(Today.isThanksgiving);
    }

}