package com.severalcircles.flames.events;

import java.sql.SQLException;

public interface FlamesDiscordEvent {

    void run() throws SQLException;
}
