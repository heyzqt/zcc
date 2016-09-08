package com.zcc.utils;

import android.content.Context;

/**
 * Created by 张艳琴 on 2016/9/7.
 *
 * 程序相关操作类
 */
public class Utils {

    private static Context mContext;

    private static Utils mUtils = null;

    public static Utils getInstance(Context context) {
        synchronized (Utils.class) {
            if (mContext == null) {
                mUtils = new Utils();
                mContext = context;
                return mUtils;
            }
        }
        return mUtils;
    }

    /**
     * 根据图片url找到图片
     *
     * @param imageName
     * @return
     */
    public static int getImgResource(String imageName) {
        int resId = mContext.getResources().getIdentifier(imageName, "mipmap", mContext.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
