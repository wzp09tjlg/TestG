package com.wuzp.didi.testg.utils;

import android.util.Log;

/**
 * @author wuzhenpeng03 (wuzhenpeng03@didichuxing.com)
 */
public class LogUtil {
    public static boolean DEBUG = true;

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }
}
