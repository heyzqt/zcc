package com.zcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zcc.ZccApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 用户名
     */
    private EditText mEdtUsername;

    /**
     * 用户密码
     */
    private EditText mEdtPassword;

    /**
     * 登录按钮
     */
    private Button mBtnLogin;

    /**
     * 注册按钮
     */
    private TextView mTvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mEdtUsername = (EditText) findViewById(R.id.edit_name);
        mEdtUsername = (EditText) findViewById(R.id.edit_psw);
        mBtnLogin = (Button) findViewById(R.id.button_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);

        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //登录按钮
            case R.id.button_login:
                String username = mEdtUsername.getText().toString();
                String password = mEdtPassword.getText().toString();
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                RequestParams rp = new RequestParams();
                rp.put("phone", username);
                rp.put("password", password);
                asyncHttpClient.post(ZccApplication.IP_ADDRESS, rp, new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(Throwable throwable, String s) {
                        Toast.makeText(LoginActivity.this, "网络连接失败 :(", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int i, String s) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(s);
                            String result = jsonObject.getString("code");
                            if (result.equals("success")) {
                                //更新用户中心的数据
                                //ZccApplication.editor.putInt(ZccApplication.LOGIN_KEY, ZccApplication.USER_LOGIN);
                                //ZccApplication.editor.commit();

                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            //注册按钮
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
