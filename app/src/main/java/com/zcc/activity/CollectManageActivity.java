package com.zcc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.adapter.CollectAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Collect;

import java.util.List;

/**
 * Created by zyq on 2016/9/9.
 * <p>
 * 我的收藏界面
 */
public class CollectManageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    ListView lv;
    ImageView back;
    CollectAdapter typeAdapter;
    List<Collect> CollectList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectmanage);
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
            typeAdapter = new CollectAdapter(this, CollectList);
            lv.setAdapter(typeAdapter);
            lv.setOnItemClickListener(this);
            lv.setOnItemLongClickListener(this);
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
            CollectList = DBHelper.getInstance(getApplicationContext()).findAll(Selector.from(Collect.class)
                    .where("userId","=", ZccApplication.mUserId+""));
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
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("确认删除？");
        dialog.setMessage("请确认是否要删除此条收藏记录!");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  final int pos=position;
                WhereBuilder wb=WhereBuilder.b("id","=",CollectList.get(position).getId());
                try {
                    DBHelper.getInstance(CollectManageActivity.this).delete(Collect.class,wb);
                    CollectList.remove(position);
                    typeAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return true;
    }

}
