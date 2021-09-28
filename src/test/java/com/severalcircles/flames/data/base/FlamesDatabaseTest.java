package com.severalcircles.flames.data.base;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class FlamesDatabaseTest {
    FlamesDatabase fd;
    @Test
    void connectionTest() throws SQLException {
        fd = new FlamesDatabase();
    }

    @Test
    void close() throws SQLException {
        fd.close();
    }
}