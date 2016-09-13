package com.zcc.activity;

import android.content.DialogInterface;
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
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Address;
import com.zcc.entity.User;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 地址详情界面
 */
public class AddressInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImgBack;

    private TextView mTvName;

    private TextView mTvPhone;

    private LinearLayout mLyEditAddress;

    private TextView mTvAddress;

    private Button mBtnSave;

    private String mAddressId;

    private String mAddressStr = "";

    private Address mAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressinfo);

        init();
    }

    private void init() {
        //获取地址数据
        String mAddressId = getIntent().getStringExtra("addressId");
        User user = new User();
        try {
            //找到地址对象
            mAddress = DBHelper.getInstance(this).findFirst(Selector.from(Address.class).where("id", "=", mAddressId));
            //找到用户对象
            user = DBHelper.getInstance(this).findFirst(Selector.from(User.class).where("id", "=",ZccApplication.mUserId+""));
        } catch (DbException e) {
            e.printStackTrace();
        }

        mImgBack = (ImageView) findViewById(R.id.img_back);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mLyEditAddress = (LinearLayout) findViewById(R.id.ly_address);
        mTvAddress = (TextView) findViewById(R.id.tv_edit_address);
        mBtnSave = (Button) findViewById(R.id.button_save_address);

        mTvName.setText(user.getName());
        mTvPhone.setText(user.getPhone());
        mTvAddress.setText(mAddress.getAddress());

        mImgBack.setOnClickListener(this);
        mLyEditAddress.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.img_back:
                finish();
                break;
            //修改地址
            case R.id.ly_address:
                final EditText inputServer = new EditText(this);
                inputServer.setPadding(5, 0, 5, 0);
                inputServer.setHintTextColor(getResources().getColor(R.color.light_gray));
                inputServer.setFocusable(true);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("请输入新的地址：").setView(inputServer).setNegativeButton(
                        "取消", null);
                builder.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                mAddressStr = inputServer.getText().toString();
                                mTvAddress.setText(mAddressStr);
                            }
                        });
                builder.show();
                break;
            //保存修改
            case R.id.button_save_address:
                mAddress.setAddress(mAddressStr);
                try {
                    DBHelper.getInstance(this).saveOrUpdate(mAddress);
                    Toast.makeText(AddressInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
