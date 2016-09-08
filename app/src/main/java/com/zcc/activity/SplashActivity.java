package com.zcc.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by zyq on 2016/9/6.
 * <p/>
 * 启动界面
 */
public class SplashActivity extends Activity {

    int cc = 0, count = 0;
    private int LENG = 3000;
    private Runnable rr, ru;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences("count",
                MODE_WORLD_READABLE);
        count = preferences.getInt("count", 0);
        final int cc = count;
        rr = new Runnable() {
            @Override
            public void run() {//进入主界面
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        };

        handler = new Handler();
        ru = new Runnable() {
            @Override
            public void run() {//进入引导界面
                if (cc == 0) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), GuideActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();

                }
                // 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
            }
        };
        SharedPreferences.Editor editor = preferences.edit();
        // 存入数据
        editor.putInt("count", ++count);
        // 提交修改
        editor.commit();
        if (cc == 0)
            handler.postDelayed(ru, LENG);// 跳入引导界面
        if (count != 1)
            handler.postDelayed(rr, LENG);// 跳入主界面

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (cc == 0 && count == 1) {//进入引导界面
                Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
            if (count != 1) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            if (rr != null)
                handler.removeCallbacks(rr);
            if (ru != null)
                handler.removeCallbacks(ru);
        }

        return super.onTouchEvent(event);
    }

}
