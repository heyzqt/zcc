package com.zcc;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by heyzqt on 2016/9/6.
 */
public class ZccApplication extends Application {

    public static SharedPreferences sp;

    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("zcc", Context.MODE_PRIVATE);
        editor  = sp.edit();
    }
}
