package com.zcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.adapter.TypeAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 某类所有商品显示界面
 */
public class BusinessTypeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    ImageView back;
    TypeAdapter typeAdapter;
    List<Business> businessList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesstype);
        Toast.makeText(getApplicationContext(), "" + getIntent().getStringExtra("type"), Toast.LENGTH_LONG).show();
        back = (ImageView) findViewById(R.id.type_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusinessTypeActivity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
        if (businessList != null && businessList.size() > 0) {
            typeAdapter = new TypeAdapter(this, businessList);
            lv.setAdapter(typeAdapter);
            lv.setOnItemClickListener(this);
            typeAdapter.notifyDataSetChanged();
        }
        if (businessList.size() == 0) {
            lv.setVisibility(View.GONE);
        }
    }

    private void initdata() {
        lv = (ListView) findViewById(R.id.type_list);
        try {
            // businessList=DBHelper.getInstance(getApplicationContext()).findAll(userInfo.class);
            businessList = DBHelper.getInstance(getApplicationContext()).findAll(Selector.from(Business.class).where("type", "=", getIntent().getStringExtra("type")));
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("businessId", businessList.get(position).getId() + "");
        intent.setClass(getApplicationContext(), BusinessInfoActivity.class);
        startActivity(intent);
    }


}
