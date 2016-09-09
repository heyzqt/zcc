package com.zcc.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 个人信息界面
 */
public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    LinearLayout linearLayout_name;
    LinearLayout linearLayout_sex;
    Button bt_exit;
    TextView tv_name;
    TextView tv_sex;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        back = (ImageView) findViewById(R.id.user_info_back);
        linearLayout_name = (LinearLayout) findViewById(R.id.liner_center_name);
        linearLayout_sex = (LinearLayout) findViewById(R.id.liner_center_sex);
        bt_exit= (Button) findViewById(R.id.button_center_exit);
        tv_name= (TextView) findViewById(R.id.Text_center_name);
        tv_sex= (TextView) findViewById(R.id.Text_center_sex);
        back.setOnClickListener(this);
        linearLayout_name.setOnClickListener(this);
        linearLayout_sex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_back:
                this.finish();
                break;
            case R.id.liner_center_name:
                //出现对话框
                break;
            case R.id.liner_center_sex:
                //出现对话框
                break;
            case R.id.button_center_exit:
                //退出登录
                break;

        }
    }


}
