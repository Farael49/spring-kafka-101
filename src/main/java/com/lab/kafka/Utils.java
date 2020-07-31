package com.lab.kafka;

public class Utils {

    //ignore exception handling for teh poc, never do this on prod :)
    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
