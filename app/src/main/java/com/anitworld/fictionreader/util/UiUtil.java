package com.anitworld.fictionreader.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by vision on 4/24/16.
 */
public class UiUtil {
    private static Toast toast;

    public static void showToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
