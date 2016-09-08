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

    /**
     * 用户是否登录
     * <p/>
     * 0:未登录 1:已登录
     */
    public final static String LOGIN_KEY = "login";

    public static int IS_LOGIN = 0;

    public final static int USER_LOGIN = 1;

    public final static int USER_NOT_LOGIN = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("zcc", Context.MODE_PRIVATE);
        editor = sp.edit();

        //获取用户登录状态
        IS_LOGIN = sp.getInt(LOGIN_KEY, 0);
    }
}
