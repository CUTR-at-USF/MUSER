package com.simplecity.amp_library.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * DialogUtils.java was derived from Shuttle's f-droid fork
 * https://github.com/quwepiro/Shuttle/blob/f-droid/app/src/main/java/com/simplecity/amp_library/utils/DialogUtils.java
 */
public class DialogUtils {


    public static MaterialDialog.Builder getBuilder(Context context) {

        return new MaterialDialog.Builder(context);
    }
}
