package com.zcc.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zcc.ZccApplication;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.User;
import com.zcc.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity {

    private ImageView mIvBack;

    private EditText mEdtName;

    private EditText mEdtPhone;

    private EditText mEdtPassword;

    private EditText mEdtPasswordCheck;

    private Button mBtnRegister;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init() {
        mContext = this;
        mIvBack = (ImageView) findViewById(R.id.image_rg_back);
        mEdtName = (EditText) findViewById(R.id.edit_reg_name);
        mEdtPhone = (EditText) findViewById(R.id.edit_reg_phone);
        mEdtPassword = (EditText) findViewById(R.id.edit_reg_password);
        mEdtPasswordCheck = (EditText) findViewById(R.id.edit_reg_password_check);
        mBtnRegister = (Button) findViewById(R.id.button_register);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mEdtName.getText().toString();
                final String phone = mEdtPhone.getText().toString();
                final String password = mEdtPassword.getText().toString();
                String passwordcheck = mEdtPasswordCheck.getText().toString();
                if (name.equals("") || phone.equals("") || password.equals("") || passwordcheck.equals("")) {
                    Toast.makeText(RegisterActivity.this, "不能留空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Utils.isMobileNum(phone)) {
                    Toast.makeText(RegisterActivity.this, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(passwordcheck)) {
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    AsyncHttpClient asyncHttp = new AsyncHttpClient();
                    RequestParams rp = new RequestParams();
                    rp.put("name", name);
                    rp.put("phone", phone);
                    rp.put("password", password);
                    rp.put("sex", "未知");
                    asyncHttp.post(ZccApplication.IP_ADDRESS + "Login/user_reg", rp, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, String s) {
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String result = jsonObject.getString("code");
                                if (result.equals("success")) {
                                    Toast.makeText(RegisterActivity.this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                                    //保存user对象
                                    User user = new User();
                                    user.setName(name);
                                    user.setImgUrl("aliuser_place_holder");
                                    user.setPassword(password);
                                    user.setPhone(phone);
                                    user.setSex("未知");
                                    DBHelper.getInstance(mContext).save(user);

                                    //将userId保存
                                    ZccApplication.mUserId = user.getId()+"";
                                    ZccApplication.editor.putString(ZccApplication.USERID_KEY, ZccApplication.mUserId);
                                    ZccApplication.editor.commit();
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable throwable, String s) {
                            Toast.makeText(RegisterActivity.this, "网络连接失败 :(", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
