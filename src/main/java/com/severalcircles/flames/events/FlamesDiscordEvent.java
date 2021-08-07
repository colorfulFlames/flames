package com.severalcircles.flames.events;

import java.sql.SQLException;

public interface FlamesDiscordEvent {

    public void run() throws SQLException;
}
