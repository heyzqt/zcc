package com.zcc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.User;


/**
 * Created by zyq on 2016/9/6.
 * 个人信息界面
 */
public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    LinearLayout linearLayout_name;
    LinearLayout linearLayout_sex;
    Button bt_exit;
    TextView tv_name;
    TextView tv_phone;
    TextView tv_sex;
    User user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        back = (ImageView) findViewById(R.id.user_info_back);
        linearLayout_name = (LinearLayout) findViewById(R.id.liner_center_name);
        linearLayout_sex = (LinearLayout) findViewById(R.id.liner_center_sex);
        bt_exit= (Button) findViewById(R.id.button_center_exit);
        tv_name= (TextView) findViewById(R.id.Text_center_name);
        tv_phone= (TextView) findViewById(R.id.Text_center_phone);
        tv_sex= (TextView) findViewById(R.id.Text_center_sex);
        initData();
        back.setOnClickListener(this);
        linearLayout_name.setOnClickListener(this);
        linearLayout_sex.setOnClickListener(this);
        bt_exit.setOnClickListener(this);
    }

    private void initData() {
        try {
            user = DBHelper.getInstance(this).findFirst(Selector.from(User.class).where("id", "=", ZccApplication.mUserId));
            tv_sex.setText(user.getSex());
            tv_name.setText(user.getName());
            tv_phone.setText(user.getPhone());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_back:
                this.finish();
                break;
            case R.id.liner_center_name:
                //出现对话框
                inputTitleDialog();
                break;
            case R.id.liner_center_sex:
                //出现对话框
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("选择性别");
                final String[] items1 = new String[]{"男", "女", "未知"};
                final boolean[] checkedItems1 = new boolean[]{false, false, false};
                int choose = 2;
                for(int ins=0;ins<2;ins++){
                    if(user.getSex().equals(items1[ins])){
                        choose=ins;
                    }
                }
                checkedItems1[choose] = true;
                builder1.setMultiChoiceItems(items1, checkedItems1, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        for (int i = 0; i < checkedItems1.length; i++) {
                            checkedItems1[i] = false;
                        }
                        checkedItems1[which] = true;
                        user.setSex(items1[which]);
                        try {
                            DBHelper.getInstance(UserInfoActivity.this).saveOrUpdate(user);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        tv_sex.setText(items1[which]);
                        dialog.dismiss();
                    }
                });
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder1.show();

                break;
            case R.id.button_center_exit:
                //退出登录
                if (!ZccApplication.mUserId.equals("-1")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("退出登录");
                    dialog.setMessage("请确认是否要退出登录!");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ZccApplication.mUserId = "-1";
                            ZccApplication.editor.putString(ZccApplication.USERID_KEY, "-1");
                            ZccApplication.editor.commit();
                            UserInfoActivity.this.finish();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                //用户未登录
                else {
                    startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                }
                //退出登录
                break;

        }
    }
    private void inputTitleDialog() {

        final EditText inputServer = new EditText(this);
        inputServer.setPadding(5,0,5,0);
        inputServer.setHintTextColor(getResources().getColor(R.color.light_gray));
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请输入新的昵称：").setView(inputServer).setNegativeButton(
               "取消", null);
        builder.setPositiveButton("保存",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String inputName = inputServer.getText().toString();
                        user.setName(inputName);
                        try {
                            DBHelper.getInstance(getApplicationContext()).saveOrUpdate(user);
                            tv_name.setText(inputName);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder.show();
    }

}
