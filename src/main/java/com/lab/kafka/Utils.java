package com.lab.kafka;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static final String EMPTY = "";

    //ignore exception handling for the poc, never do this on prod :)
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // min <= x < max
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
