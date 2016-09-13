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
import com.zcc.adapter.OrderAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Order;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 管理订单界面
 */
public class OrderManageActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    ListView lv;
    ImageView back;
    OrderAdapter typeAdapter;
    List<Order> CollectList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanage);
        back = (ImageView) findViewById(R.id.order_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderManageActivity.this.finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initdata();
        if (CollectList != null && CollectList.size() > 0) {
            typeAdapter = new OrderAdapter(this, CollectList);
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
        lv = (ListView) findViewById(R.id.order_list);
        try {
            CollectList = DBHelper.getInstance(getApplicationContext()).findAll(Selector.from(Order.class)
                    .where("userId","=", ZccApplication.mUserId+""));
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("OrderId", CollectList.get(position).getId() + "");
        intent.setClass(getApplicationContext(), OrderInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("确认删除？");
            dialog.setMessage("请确认是否要删除此条订单记录!");
            dialog.setCancelable(true);
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //  final int pos=position;
                    WhereBuilder wb=WhereBuilder.b("id","=",CollectList.get(position).getId());
                    try {
                        DBHelper.getInstance(OrderManageActivity.this).delete(Order.class,wb);
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

