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

    }
}