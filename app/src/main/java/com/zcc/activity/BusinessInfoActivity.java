package com.zcc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;
import com.zcc.entity.Collect;
import com.zcc.entity.Order;
import com.zcc.entity.ShoppingCar;
import com.zcc.utils.Utils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p>
 * 商品详情页面
 */
public class BusinessInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private RelativeLayout rl_collect;//添加收藏
    private RelativeLayout rl_car;//添加购物车
    private RelativeLayout rl_buy;//立即购买
    String businessId = "0";
    Business business;
    String userId = "0";
    List<Collect> CollectList = null;
    List<ShoppingCar> shoppingCarList = null;

    TextView b_name;
    TextView b_price;
    ImageView b_img;
    Calendar now;
    TextView tv_soucang;
    ImageView Iv_add, Iv_reduce;//数量的添加和减少
    EditText EdCount;//数量的输入框


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businessinfo);
        userId = ZccApplication.mUserId;
        //实例化界面的控件
        back = (ImageView) findViewById(R.id.businessInfo_back);
        rl_buy = (RelativeLayout) findViewById(R.id.businessInfo_buy);
        rl_car = (RelativeLayout) findViewById(R.id.businessInfo_car);
        rl_collect = (RelativeLayout) findViewById(R.id.businessInfo_collect);
        b_name = (TextView) findViewById(R.id.businessInfo_name);
        b_price = (TextView) findViewById(R.id.businessInfo_price);
        b_img = (ImageView) findViewById(R.id.businessInfo_img);
        tv_soucang = (TextView) findViewById(R.id.text_bus_collect);
        EdCount = (EditText) findViewById(R.id.edit_bus_count);
        Iv_add = (ImageView) findViewById(R.id.icon_add);
        Iv_reduce = (ImageView) findViewById(R.id.icon_reduce);
        initData();
        //绑定监听
        Iv_reduce.setOnClickListener(this);
        Iv_add.setOnClickListener(this);
        back.setOnClickListener(this);
        rl_car.setOnClickListener(this);
        rl_buy.setOnClickListener(this);
        rl_collect.setOnClickListener(this);
    }

    /**
     * 初始化商品的数据
     */
    private void initData() {
        now = Calendar.getInstance();
        userId = ZccApplication.sp.getString(ZccApplication.USERID_KEY, "1");//暂时写成1
        businessId = getIntent().getStringExtra("businessId");
        business = new Business();
        try {
            CollectList = DBHelper.getInstance(getApplicationContext()).findAll(Selector.from(Collect.class)
                    .where("businessId", "=", businessId));

            business = DBHelper.getInstance(getApplicationContext()).findFirst(Selector.from(Business.class).where("id", "=", businessId));
            b_name.setText(business.getName());
            b_price.setText("￥ " + business.getPrice());
            b_img.setImageResource(Utils.getInstance(this).getImgResource(business.getImgUrl()));
        } catch (DbException e) {
            e.printStackTrace();
        }
        //查找商品对象
        tv_soucang.setText("添加收藏");
        if (CollectList.size() > 0) {
            tv_soucang.setText("取消收藏");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.businessInfo_back:
                finish();
                break;
            case R.id.businessInfo_buy:
                //购买物品添加对象Order--》后期添加数量
                if (!ZccApplication.mUserId.equals("-1")) {
                    Order order = new Order();
                    order.setCount(EdCount.getText().toString());
                    order.setPrice(business.getPrice());
                    order.setUserId(ZccApplication.mUserId);
                    order.setBusinessId(businessId);
                    order.setCreateTime((now.get(Calendar.YEAR)) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH));
                    order.setAddress("西华大学");
                    try {
                        int countt=Integer.parseInt(EdCount.getText().toString());
                        if(countt<=0){
                            Toast.makeText(getApplicationContext(), "购买失败!数量不能小于0", Toast.LENGTH_LONG).show();
                        }else {
                            DBHelper.getInstance(getApplicationContext()).save(order);
                            Toast.makeText(getApplicationContext(), "购买成功!", Toast.LENGTH_LONG).show();
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.businessInfo_car:
                if (!ZccApplication.mUserId.equals("-1")) {
                    ShoppingCar shoppingCar = new ShoppingCar();
                    try {
                        WhereBuilder wb = WhereBuilder.b("businessId", "=", businessId).and("userId", "=", ZccApplication.mUserId);
                        shoppingCarList = DBHelper.getInstance(getApplicationContext()).findAll(ShoppingCar.class, wb);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    //需要判断购物车是否有该物品--》后期添加
                    shoppingCar.setBusinessId(businessId);
                    if (shoppingCarList.size() > 0) {
                        int counts = 0;
                        int adds = Integer.parseInt(EdCount.getText().toString());
                        counts = Integer.parseInt(shoppingCarList.get(0).getCount()) + adds;
                        shoppingCar.setCount("" + counts);
                        shoppingCar.setUserId(userId);
                        shoppingCar.setId(shoppingCarList.get(0).getId());
                        shoppingCar.setPrice(business.getPrice());
                    } else {
                        shoppingCar.setCount(EdCount.getText().toString());
                        shoppingCar.setCreate_time((now.get(Calendar.YEAR)) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH));
                        shoppingCar.setUserId(userId);
                        shoppingCar.setPrice(business.getPrice());
                    }
                    try {
                        int countt=Integer.parseInt(EdCount.getText().toString());
                        if(countt<=0){
                            Toast.makeText(getApplicationContext(), "加入购物车失败!数量不能小于0", Toast.LENGTH_LONG).show();
                        }else {
                            DBHelper.getInstance(getApplicationContext()).saveOrUpdate(shoppingCar);
                            Toast.makeText(getApplicationContext(), "加入购物车成功!", Toast.LENGTH_LONG).show();
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                //加入购物车添加对象购物车
                break;
            case R.id.businessInfo_collect:
                if (!ZccApplication.mUserId.equals("-1")) {
                    if (tv_soucang.getText().toString().equals("添加收藏")) {
                        //加入收藏添加收藏对象
                        //   if (!ZccApplication.mUserId.equals("-1")) {
                        Collect collect = new Collect();
                        collect.setBusinessId(businessId);
                        collect.setCollect_time((now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH));
                        collect.setUserId(userId);
                        try {
                            DBHelper.getInstance(getApplicationContext()).save(collect);
                            Toast.makeText(getApplicationContext(), "收藏成功!", Toast.LENGTH_LONG).show();
                            tv_soucang.setText("取消收藏");
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        WhereBuilder wb = WhereBuilder.b("businessId", "=", businessId);
                        try {
                            DBHelper.getInstance(this).delete(Collect.class, wb);
                            tv_soucang.setText("添加收藏");
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    //用户未登录
                    Toast.makeText(this, "请先登录", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.icon_add:
                int couns = Integer.parseInt(EdCount.getText().toString()) + 1;
                EdCount.setText(couns + "");
                break;
            case R.id.icon_reduce:
                int counss = Integer.parseInt(EdCount.getText().toString()) - 1;
                if (counss <= 0) {
                    EdCount.setText("1");
                } else {
                    EdCount.setText(counss + "");
                }
                break;
        }
    }
}
