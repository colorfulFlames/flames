/*
 * Copyright (c) 2021 Several Circles.
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AddCommandBlue {
    public static void main(String args[]) throws IOException {
        URL url = new URL("https://discord.com/api/v8/applications/849320259152117882/commands");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bot ODQ5MzIwMjU5MTUyMTE3ODgy.YLZdIQ.SoMMW66q7hmRZwY4HLxjX1RiQLM");
        http.setRequestProperty("Content-Type", "application/json");

        String data = "{\n    \"name\": \"guilddata\",\n    \"type\": 1,\n    \"description\": \"Displays data for a guild\"\n}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();

    }
}
