package com.zcc;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.zcc.activity.R;

/**
 * Created by heyzqt on 2016/9/6.
 */
public class ZccApplication extends Application {

    public static SharedPreferences sp;

    public static SharedPreferences.Editor editor;

    /**
     * 存储当前用户Id
     *
     * -1: 用户未登录
     */
    public final static String USERID_KEY = "user_id";

    public static int mUserId = -1;

    /**
     * 服务器IP地址
     */
    public static String IP_ADDRESS;

    @Override
    public void onCreate() {
        super.onCreate();

        IP_ADDRESS = getResources().getString(R.string.ip_address);

        sp = getSharedPreferences("zcc", Context.MODE_PRIVATE);
        editor = sp.edit();

        //获取用户登录状态
        mUserId = sp.getInt(USERID_KEY, -1);
    }
}
