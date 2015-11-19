package com.common;

import android.content.Context;

import com.leo.base.util.LSharePreference;

/**
 * Created by admin on 2015/9/6.
 */
public class Token {


    public static String get(Context context) {
        return LSharePreference.getInstance(context).getString("token","33f3e6d26a5bf2fa32c894d76937893c");
    }

    public static void set(Context context, String token) {
        LSharePreference.getInstance(context).setString("token", token);
    }
}
