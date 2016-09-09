package com.zcc.utils;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 张艳琴 on 2016/9/7.
 * <p/>
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

    /**
     * 判断电话号码是否正确
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNum(String mobile) {
        Pattern p = Pattern
                .compile("^[1]([3][0-9]{1}|59|58|88|89|84)[0-9]{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
