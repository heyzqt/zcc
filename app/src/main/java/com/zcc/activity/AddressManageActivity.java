package com.zcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.zcc.adapter.AddressAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 管理我的地址界面
 */
public class AddressManageActivity extends AppCompatActivity {

    private TextView mTvNull;

    private LinearLayout mLyBack;

    private ListView mAddressLv;

    private List<Address> mAddressLists = new ArrayList<>();

    private AddressAdapter mAddressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressmanange);

        init();
    }

    private void init() {
        mLyBack = (LinearLayout) findViewById(R.id.ly_back);
        mTvNull = (TextView) findViewById(R.id.tv_null);
        mAddressLv = (ListView) findViewById(R.id.lv_address_manage);

        try {
            mAddressLists = DBHelper.getInstance(this).findAll(Address.class);
            if (mAddressLists.size() != 0) {
                mAddressAdapter = new AddressAdapter(this, mAddressLists, R.layout.item_listview_addressmanage);
                mAddressLv.setAdapter(mAddressAdapter);
                mTvNull.setVisibility(View.GONE);
                mAddressLv.setVisibility(View.VISIBLE);
            } else {
                mTvNull.setVisibility(View.VISIBLE);
                mAddressLv.setVisibility(View.GONE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        mLyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("addressId", mAddressLists.get(position).getId() + "");
                intent.setClass(getApplicationContext(), AddressInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            mAddressLists = DBHelper.getInstance(this).findAll(Address.class);
            if (mAddressLists.size() != 0) {
                mAddressAdapter = new AddressAdapter(this, mAddressLists, R.layout.item_listview_addressmanage);
                mAddressLv.setAdapter(mAddressAdapter);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
