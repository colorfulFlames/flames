/*
 * Copyright (c) 2023 Several Circles
 */

package com.severalcircles.flames;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class contains various string-related utilities.
 * @author Several Circles
 * @version 8
 * @since Flames 4
 */
public class Util {
    /**
     * Formats a score into the proper format.
     * @param score
     * @return A formatted score String
     */
    public static String formatScore(double score) {
        score = Math.round(score);
        DecimalFormat formatter = new DecimalFormat("#,###");
        return String.format("%s FP", formatter.format(score));
    }
    /**
     * Formats a date into the proper format.
     * @param instant The date to format
     * @param locale The locale to use
     * @return A formatted date String
     */
    public static String prettyDate(Instant instant, Locale locale) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/YYYY", locale);
        return formatter.format(new Date(instant.toEpochMilli()));
    }
    public static <V, K> Map<V, K> invertMapUsingStreams(Map<K, V> map) {
        Map<V, K> inversedMap = map.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        return inversedMap;
    }
    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
}
