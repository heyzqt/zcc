package com.zcc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by heyzqt on 2016/9/6.
 *
 * 成功创建订单或者失败界面
 */
public class CreateOrderSucceedOrFailedActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createorder_succeedorfailed);
    }
}
