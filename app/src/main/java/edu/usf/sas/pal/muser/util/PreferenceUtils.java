package edu.usf.sas.pal.muser.util;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;

import com.simplecity.amp_library.ShuttleApplication;

/**
 * A class that contains utility methods related to Shared Preferences.
 */
public class PreferenceUtils {
    @TargetApi(9)
    private static void saveString(SharedPreferences prefs, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveString(String key, String value) {
        saveString(ShuttleApplication.getPrefs(), key, value);
    }

    public static String getString(String key){
        return ShuttleApplication.getPrefs().getString(key, null);
    }

    @TargetApi(9)
    public static void saveLong(SharedPreferences prefs, String key, long value) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putLong(key, value);
        edit.apply();
    }
    public static void saveLong(String key, long value) {
        saveLong(ShuttleApplication.getPrefs(), key, value);
    }

    public static long getLong(String key, long defaultValue) {
        return ShuttleApplication.getPrefs().getLong(key, defaultValue);
    }
}