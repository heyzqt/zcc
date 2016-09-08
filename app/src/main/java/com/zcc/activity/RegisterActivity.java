package com.zcc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText mEdtName;

    private EditText mEdtPhone;

    private EditText mEdtPassword;

    private EditText mEdtPasswordCheck;

    private Button mBtnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        mEdtName = (EditText) findViewById(R.id.edit_reg_name);
        mEdtPhone = (EditText) findViewById(R.id.edit_reg_phone);
        mEdtPassword = (EditText) findViewById(R.id.edit_reg_password);
        mEdtPasswordCheck = (EditText) findViewById(R.id.edit_reg_password_check);
        mBtnRegister = (Button) findViewById(R.id.button_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdtName.getText().toString();
                String password = mEdtPassword.getText().toString();
                String passwordcheck = mEdtPasswordCheck.getText().toString();
                if(!password.equals(passwordcheck)){
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                }else{
                    AsyncHttpClient asyncHttp = new AsyncHttpClient();
                    RequestParams rp = new RequestParams();
                    //rp.put("username",);
                    //asyncHttp.post(mIPAddress,rp);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
