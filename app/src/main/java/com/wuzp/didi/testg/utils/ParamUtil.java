package com.wuzp.didi.testg.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wuzhenpeng03 (wuzhenpeng03@didichuxing.com)
 */
public class ParamUtil {

    String name = "TestG";

    Context mcontext = null;

    private SharedPreferences sp = mcontext.getSharedPreferences(name, Context.MODE_PRIVATE);

    private static ParamUtil INSTANCE;

    private ParamUtil(Context context) {
        mcontext = context;
    }

    public static ParamUtil getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ParamUtil(context);
        }
        return INSTANCE;
    }

    public static void put(String key, String value) {
        SharedPreferences.Editor editor = INSTANCE.sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String get(String key, String defaultStr) {
        return INSTANCE.sp.getString(key, defaultStr);
    }
}
