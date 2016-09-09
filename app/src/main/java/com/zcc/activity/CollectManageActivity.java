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
import com.zcc.adapter.Collect_Adapter;
import com.zcc.adapter.TypeAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;
import com.zcc.entity.Collect;

import java.util.List;

/**
 * Created by zyq on 2016/9/9.
 * <p>
 * 我的收藏界面
 */
public class CollectManageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    ImageView back;
    Collect_Adapter typeAdapter;
    List<Collect> CollectList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectmanage);
        Toast.makeText(getApplicationContext(), "" + getIntent().getStringExtra("type"), Toast.LENGTH_LONG).show();
        back = (ImageView) findViewById(R.id.collect_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectManageActivity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
        if (CollectList != null && CollectList.size() > 0) {
            typeAdapter = new Collect_Adapter(this, CollectList);
            lv.setAdapter(typeAdapter);
            lv.setOnItemClickListener(this);
            typeAdapter.notifyDataSetChanged();
        }
        if (CollectList.size() == 0) {
            lv.setVisibility(View.GONE);
        }
    }

    private void initdata() {
        lv = (ListView) findViewById(R.id.collect_list);
        try {
            // CollectList=DBHelper.getInstance(getApplicationContext()).findAll(userInfo.class);
            CollectList = DBHelper.getInstance(getApplicationContext()).findAll(Collect.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("businessId", CollectList.get(position).getBusinessId() + "");
        intent.setClass(getApplicationContext(), BusinessInfoActivity.class);
        startActivity(intent);
    }


}
