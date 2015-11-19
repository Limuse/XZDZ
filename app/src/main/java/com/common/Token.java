package com.common;

import android.content.Context;

import com.leo.base.util.LSharePreference;

/**
 * Created by admin on 2015/9/6.
 */
public class Token {


    public static String get(Context context) {
        return LSharePreference.getInstance(context).getString("token","67a74d37afc24bd68c850ae6ebdfdd7d");
    }

    public static void set(Context context, String token) {
        LSharePreference.getInstance(context).setString("token", token);
    }
}
