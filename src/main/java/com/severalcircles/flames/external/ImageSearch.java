/*
 * Copyright (c) 2022 Several Circles.
 */

package com.severalcircles.flames.external;

//import jdk.incubator.http.HttpClient;
//import jdk.incubator.http.HttpResponse;
import com.severalcircles.flames.util.BadWordFilter;
import org.json.JSONObject;

import java.io.BufferedReader;
        import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class ImageSearch {
    public static String searchImage(String q) throws IOException {
        URL url = new URL("https://api.unsplash.com/search/photos?content_filter=high&query=" + BadWordFilter.getCensoredText(q).replace(" ", "%20"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Authorization: Client-ID 5BgyDJn2wiCT7SNM8kVyKeFFCWsnBvXM8F1jCEti-t8");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        int status = con.getResponseCode();
        con.disconnect();
        System.out.println(content);
        return new JSONObject(content.toString()).getJSONArray("results").getJSONObject(0).getJSONObject("urls").getString("raw");
    }
}
