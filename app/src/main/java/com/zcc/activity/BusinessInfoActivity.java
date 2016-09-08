package com.zcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zcc.ZccApplication;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;
import com.zcc.entity.Collect;
import com.zcc.entity.Order;
import com.zcc.entity.ShoppingCar;
import com.zcc.utils.Utils;

import java.util.Calendar;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 商品详情页面
 */
public class BusinessInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RelativeLayout rl_collect;
    private RelativeLayout rl_car;
    private RelativeLayout rl_buy;
    String businessId="0";
    Business business;
    String userId="0";

    TextView b_name;
    TextView b_price;
    ImageView b_img;
    Calendar now;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessinfo);
        back= (ImageView) findViewById(R.id.businessInfo_back);
        rl_buy= (RelativeLayout) findViewById(R.id.businessInfo_buy);
        rl_car= (RelativeLayout) findViewById(R.id.businessInfo_car);
        rl_collect= (RelativeLayout) findViewById(R.id.businessInfo_collect);
        b_name= (TextView) findViewById(R.id.businessInfo_name);
        b_price= (TextView) findViewById(R.id.businessInfo_price);
        b_img= (ImageView) findViewById(R.id.businessInfo_img);
        initData();
        back.setOnClickListener(this);
        rl_car.setOnClickListener(this);
        rl_buy.setOnClickListener(this);
        rl_collect.setOnClickListener(this);
    }

    private void initData() {
        now = Calendar.getInstance();
        userId=ZccApplication.sp.getString("user_id","1");//暂时写成1
        businessId=getIntent().getStringExtra("businessId");
        business=new Business();
        try {
            business= DBHelper.getInstance(getApplicationContext()).findFirst(Selector.from(Business.class).where("id","=",businessId));
            b_name.setText(business.getName());
            b_price.setText("￥ "+business.getPrice());
            b_img.setImageResource(Utils.getInstance(this).getImgResource(business.getImgUrl()));
        } catch (DbException e) {
            e.printStackTrace();
        }
        //查找商品对象

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.businessInfo_back:
                finish();
                break;
            case R.id.businessInfo_buy:
                //购买物品添加对象Order
                Order order=new Order();
                order.setCount("1");
                order.setCount(business.getPrice());
                order.setUserId("1");//暂时写成1，
                order.setBusinessId(businessId);
                order.setCreateTime((now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH));
                order.setAddress("西华大学");
                try {
                    DBHelper.getInstance(getApplicationContext()).save(order);
                    Toast.makeText(getApplicationContext(),"购买成功!",Toast.LENGTH_LONG).show();
                    Log.e("DBLog------","添加order成功");
                } catch (DbException e) {
                    e.printStackTrace();
                    Log.e("DBLog------","添加order成功");
                }
                break;
            case R.id.businessInfo_car:
                //需要判断购物车是否有该物品
                ShoppingCar shoppingCar=new ShoppingCar();
                shoppingCar.setBusinessId(businessId);
                shoppingCar.setCount("1");
                shoppingCar.setCreate_time((now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH));
                shoppingCar.setUserId(userId);
                shoppingCar.setPrice(business.getPrice());
                try {
                    DBHelper.getInstance(getApplicationContext()).save(shoppingCar);
                    Toast.makeText(getApplicationContext(),"加入购物车成功!",Toast.LENGTH_LONG).show();
                    Log.e("DBLog------","添加car成功");
                } catch (DbException e) {
                    e.printStackTrace();
                    Log.e("DBerror------","添加car失败");
                }
                //加入购物车添加对象购物车
                break;
            case R.id.businessInfo_collect:
                //加入收藏添加收藏对象
                Collect collect=new Collect();
                collect.setBusinessId(businessId);
                collect.setCollect_time((now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH));
                collect.setUserId(userId);
                try {
                    DBHelper.getInstance(getApplicationContext()).save(collect);
                    Toast.makeText(getApplicationContext(),"收藏成功!",Toast.LENGTH_LONG).show();
                    Log.e("DBLog------","添加collect成功");
                } catch (DbException e) {
                    e.printStackTrace();
                    Log.e("DBerror------","添加collect失败");
                }
                break;
        }

    }
}
