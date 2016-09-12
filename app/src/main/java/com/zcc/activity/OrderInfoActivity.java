package com.zcc.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Address;
import com.zcc.entity.Business;
import com.zcc.entity.Order;
import com.zcc.entity.User;
import com.zcc.utils.Utils;

/**
 * Created by heyzqt on 2016/9/6.
 * <p>
 * 订单详情界面
 */
public class OrderInfoActivity extends AppCompatActivity implements View.OnClickListener {
    String orderId = "0";
    ImageView IvDelete, IvBack;
    TextView Tvname, Tvuser_name, Tvphone, Tvaddress, Tv_count, Tv_price, Tv_SumPrice, TvTime;
    ImageView Iv_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderinfo);
        orderId = getIntent().getStringExtra("OrderId").toString();
        IvDelete = (ImageView) findViewById(R.id.icon_delete);
        IvBack = (ImageView) findViewById(R.id.order_info_back);
        Tv_count = (TextView) findViewById(R.id.order_tv_count);
        Tvname = (TextView) findViewById(R.id.order_tv_name);
        Tv_price = (TextView) findViewById(R.id.order_tv_price);
        Tvphone = (TextView) findViewById(R.id.order_tv_phone);
        Tv_SumPrice = (TextView) findViewById(R.id.order_tv_sum_price);
        Tvaddress = (TextView) findViewById(R.id.order_tv_address);
        Tvuser_name = (TextView) findViewById(R.id.order_tv_user);
        TvTime = (TextView) findViewById(R.id.order_tv_time);
        Iv_img = (ImageView) findViewById(R.id.order_info_img);
        initDatas();
        IvBack.setOnClickListener(this);
        IvDelete.setOnClickListener(this);
    }

    private void initDatas() {
//从数据库查询数据，进行添加
        Business business = new Business();
        User user = new User();
        Order order = new Order();
        Address address=new Address();
        try {
            order = DBHelper.getInstance(this).findFirst(Selector.from(Order.class).where("id", "=", orderId));
            address=DBHelper.getInstance(this).findFirst(Address.class);
            user = DBHelper.getInstance(this).findFirst(Selector.from(User.class).where("id", "=", order.getUserId() + ""));
            business = DBHelper.getInstance(this).findFirst(Selector.from(Business.class).where("id", "=", order.getBusinessId() + ""));
            Iv_img.setImageResource(Utils.getInstance(this).getImgResource(business.getImgUrl()));
            Tvname.setText(business.getName());
            Tvuser_name.setText(user.getName());
            Tvphone.setText(user.getPhone());
            Tvaddress.setText(address.getAddress());
            Tv_count.setText("x"+order.getCount());
            Tv_price.setText("￥ "+order.getPrice());
            double sum=Double.parseDouble(order.getPrice())*Double.parseDouble(order.getCount());
            Tv_SumPrice.setText("￥ "+new java.text.DecimalFormat("#.00").format(sum));
            TvTime.setText(order.getCreateTime());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_delete:
                //先删除数据，再退出本Activity
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("删除订单");
                dialog.setMessage("请确认是否要删除此订单!");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            WhereBuilder wb = WhereBuilder.b("id", "=", "" + orderId);
                            DBHelper.getInstance(getApplicationContext()).delete(Order.class, wb);
                            OrderInfoActivity.this.finish();
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
                break;
            case R.id.order_info_back:
                this.finish();
                break;
        }

    }
}
