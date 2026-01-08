package com.riaylibrary.utils.dataStrore;

import android.content.Context;
import android.content.SharedPreferences;

public final class Prefs {

    private static final String PREF_NAME = "app_prefs_riay";
    private static SharedPreferences prefs;

    private Prefs() {
        // no instance
    }

    /** Initialize once in Application class */
    public static void init(Context context) {
        if (prefs == null) {
            prefs = context.getApplicationContext()
                    .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }


    private static void checkInit() {
        if (prefs == null) {
            throw new IllegalStateException(
                    "Prefs is not initialized. Call Prefs.init(context) in Application class."
            );
        }
    }

    public static void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public static void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public static void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    // ----------------- GET -----------------

    public static String getString(String key, String def) {
        return prefs.getString(key, def);
    }

    public static int getInt(String key, int def) {
        return prefs.getInt(key, def);
    }

    public static boolean getBoolean(String key, boolean def) {
        return prefs.getBoolean(key, def);
    }

    public static long getLong(String key, long def) {
        return prefs.getLong(key, def);
    }

    // ----------------- REMOVE / CLEAR -----------------

    public static void remove(String key) {
        prefs.edit().remove(key).apply();
    }

    public static void clear() {
        prefs.edit().clear().apply();
    }
    public static boolean contains(String key) {
        checkInit();
        return prefs.contains(key);
    }
}