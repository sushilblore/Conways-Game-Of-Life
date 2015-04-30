package com.quandoo.gameoflife.util;

import android.util.Log;

/**
 * Created by sushil.jha on 27-04-2015.
 */
public class Utils {

    public static void debug(Object source, String message, Object ... args) {
        Log.i(/*source.getClass().getSimpleName()*/"Sushil", String.format(message, args));
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException("Sleep interrupted", e);
        }
    }
}
