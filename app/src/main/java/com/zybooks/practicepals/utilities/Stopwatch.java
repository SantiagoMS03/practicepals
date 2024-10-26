package com.zybooks.practicepals.utilities;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Stopwatch {

    private static Stopwatch instance;

    private long startTime = 0;
    private long currentTime = 0;
    private long elapsedTime = 0;
    private boolean running = false;

    private Stopwatch() {
        // Private constructor to prevent instantiation
    }

    // Singleton instance getter
    public static synchronized Stopwatch getInstance() {
        if (instance == null) {
            instance = new Stopwatch();
        }
        return instance;
    }

    // Start the stopwatch
    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    // Stop the stopwatch and calculate elapsed time
    public void stop() {
        if (running) {
            currentTime = getCurrentTime();
            elapsedTime += currentTime;
            running = false;
        }
    }

    public static long calculateHours(long elapsedTimeMilis) {
        return TimeUnit.MILLISECONDS.toHours(elapsedTimeMilis);
    }

    public static long calculateMinutes(long elapsedTimeMillis, long hours) {
        return TimeUnit.MILLISECONDS.toMinutes(elapsedTimeMillis)
                - TimeUnit.HOURS.toSeconds(hours);
    }

    public static long calculateSeconds(long elapsedTimeMillis, long minutes, long hours) {
        return TimeUnit.MILLISECONDS.toSeconds(elapsedTimeMillis)
                - TimeUnit.MINUTES.toSeconds(minutes)
                - TimeUnit.HOURS.toSeconds(hours);
    }

    public static String formatHoursMinutesSeconds(long timePracticed) {
        String res = "";
        long hours = calculateHours(timePracticed);
        long minutes = calculateMinutes(timePracticed, hours);
        long seconds = calculateSeconds(timePracticed, minutes, hours);
        if (hours >= 1) {
            res += hours + " hours, ";
        }
        if (minutes >= 1) {
            res += minutes + " minutes, ";
        }
        res += seconds + " seconds";
        return res;
    }


    // Reset the stopwatch
    public void reset() {
        startTime = 0;
        elapsedTime = 0;
        running = false;
    }

    private long getCurrentTime() {
        return System.currentTimeMillis() - startTime;
    }

    // Get the elapsed time in milliseconds
    public long getElapsedTime() {
        if (running) {
            return elapsedTime + getCurrentTime();
        }
        return elapsedTime;
    }

    // Check if the stopwatch is running
    public boolean isRunning() {
        return running;
    }
}
