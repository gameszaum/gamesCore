package com.gameszaum.core.other.util;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * Copyright (C) gameszaum, all rights reserved, unauthorized
 * utilization or copy of this file, is strictly prohibited and
 * liable to civil and criminal penalties, the project 'gamesCore'
 * is private and the re-sale without contact with me (gameszaum) is not allowed.
 */
public class Util {

    private static final NavigableMap<Double, String> suffixes = new TreeMap<>();

    static {
        //suffixes.put(1000, "K");
        //suffixes.put(1000000, "M");
        suffixes.put(1000000000D, "B");
        suffixes.put(1000000000000D, "T");
        suffixes.put(1000000000000000D, "Q");
        suffixes.put(1000000000000000000D, "QQ");
        suffixes.put(1000000000000000000000D, "S");
    }

    public static String progressBar(double current, double max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = (float) ((float) current / max);
        int progressBars = (int) (totalBars * percent);
        int leftOver = (totalBars - progressBars);
        StringBuilder sb = new StringBuilder();

        sb.append(completedColor);

        for (int i = 0; i < progressBars; i++) {
            sb.append(symbol);
        }
        sb.append(notCompletedColor);

        for (int i = 0; i < leftOver; i++) {
            sb.append(symbol);
        }
        return sb.toString();
        /*res.delete(0, res.length());
        int numPounds = (int) (((max * 100) / current) / totalBars);
        res.append(notCompletedColor);
        for (int i = 0; i != totalBars; i++) {
            res.append(symbol);
        }
        res.append(completedColor);
        for (int i = 0; i < numPounds; i++) {
            res.replace(0, i, symbol);
        }
        return res.toString();*/
    }

    public static String toTime(int time) {
        int m = time / 60;
        int s = time % 60;

        if (m > 0) {
            return m + "m" + (s > 0 ? " " + s + "s" : "");
        } else {
            return s + "s";
        }
    }

    public static String formatPercent(double percent) {
        return new DecimalFormat("###,###,###.##").format(percent) + "%";
    }

    public static String formatValue(double value) {
        if (value < 1000000000) return new DecimalFormat("###,###,###.##").format(value);

        Map.Entry<Double, String> e = suffixes.floorEntry(value);
        String suffix = e.getValue();

        double truncated = value / 10D; //the number part of the output times 10

        return (truncated / 10) + suffix;
    }

    public static String encode(String paramString) {
        byte[] encode = Base64.getEncoder().encode(paramString.getBytes());
        return new String(encode, StandardCharsets.UTF_8);
    }

    public static String decode(String paramString) {
        byte[] decode = Base64.getDecoder().decode(paramString.getBytes());
        return new String(decode, StandardCharsets.UTF_8);
    }

    public static void info(Logger logger, String string) {
        logger.info(string);
    }

    public static void severe(Logger logger, String string) {
        logger.severe(string);
    }

    public static void setOnlineMode(String pkgversion, boolean online) {
        try {
            Class<?> minecraftServer = Class
                    .forName("net.minecraft.server." + pkgversion + ".MinecraftServer");
            Object getServer = minecraftServer.getMethod("getServer").invoke(null);
            getServer.getClass().getMethod("setOnlineMode", boolean.class).invoke(getServer, online);
            Object server = minecraftServer.getDeclaredField("server").get(getServer);
            Field onlineField = server.getClass().getDeclaredField("online");
            onlineField.setAccessible(true);
            Object getOnline = onlineField.get(server);
            Field setValue = getOnline.getClass().getDeclaredField("value");
            setValue.setAccessible(true);
            setValue.set(getOnline, online);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
