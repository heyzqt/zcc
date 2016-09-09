package com.zcc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
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
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvBack;

    /**
     * 用户号码
     */
    private EditText mEdtPhone;

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

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mContext = this;

        mIvBack = (ImageView) findViewById(R.id.image_rg_back);
        mEdtPhone = (EditText) findViewById(R.id.edit_name);
        mEdtPassword = (EditText) findViewById(R.id.edit_psw);
        mBtnLogin = (Button) findViewById(R.id.button_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);

        mIvBack.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回按钮
            case R.id.image_rg_back:
                finish();
                break;
            //登录按钮
            case R.id.button_login:
                mBtnLogin.setClickable(false);
                final String phone = mEdtPhone.getText().toString();
                String password = mEdtPassword.getText().toString();

                //判空
                if (phone.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this, "不能留空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                //检查手机号码格式
                if(!Utils.isMobileNum(phone)){
                    Toast.makeText(LoginActivity.this, "手机号码格式错误!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //联网发送数据
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                RequestParams rp = new RequestParams();
                rp.put("phone", phone);
                rp.put("password", password);
                asyncHttpClient.post(ZccApplication.IP_ADDRESS+"Login/user_login", rp, new AsyncHttpResponseHandler() {
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
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                                //存入SharePreference
                                User user = DBHelper.getInstance(mContext).findFirst(Selector.from(User.class).where("phone","=",phone));
                                ZccApplication.mUserId = user.getId()+"";
                                ZccApplication.editor.putString(ZccApplication.USERID_KEY,ZccApplication.mUserId);
                                ZccApplication.editor.commit();

                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                mBtnLogin.setClickable(true);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (DbException e) {
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
