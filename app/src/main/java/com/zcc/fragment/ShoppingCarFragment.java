package com.zcc.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.activity.LoginActivity;
import com.zcc.activity.R;
import com.zcc.adapter.ShoppingCarAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Order;
import com.zcc.entity.ShoppingCar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 购物车Fragemnt
 */
public class ShoppingCarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView mTvNull;

    /**
     * 购物车列表
     */
    private ListView mShoppingCarLv;

    /**
     * 全选图标
     */
    private ImageView mImgChooseAll;

    /**
     * 价格
     */
    private TextView mTvPrice;

    /**
     * 结算
     */
    private Button mBtnAccount;

    /**
     * 商品列表
     */
    private List<ShoppingCar> mShoppingLists = new ArrayList<>();

    private ShoppingCarAdapter mShoppingAdapter;

    /**
     * 判断购物车里面是否全选
     */
    private boolean mIsChecked = false;

    /**
     * 记录商品的点中状态
     * <p/>
     * 0：未选中  1：选中
     */
    private List<String> mRecordLists;

    private double mTotalPrice = 0;

    private Context mContext;

    private Calendar nowTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_shoppingcar, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View view) {
        nowTime = Calendar.getInstance();
        mContext = getActivity();
        mImgChooseAll = (ImageView) view.findViewById(R.id.img_circle_all);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mBtnAccount = (Button) view.findViewById(R.id.btn_account);
        mTvNull = (TextView) view.findViewById(R.id.tv_listview_is_null);
        mShoppingCarLv = (ListView) view.findViewById(R.id.lv_shoppingcar);

        if (mIsChecked) {
            mImgChooseAll.setImageResource(R.mipmap.checked_circle);
        } else {
            mImgChooseAll.setImageResource(R.mipmap.check_circle);
        }
        mImgChooseAll.setOnClickListener(this);
        mBtnAccount.setOnClickListener(this);
        mShoppingCarLv.setOnItemClickListener(this);
        mShoppingCarLv.setOnItemLongClickListener(this);
    }

    @Override
    public void onResume() {
        //初始化数据
        try {
            mShoppingLists = DBHelper.getInstance(getActivity()).findAll(Selector.from(ShoppingCar.class).where("userId", "=", ZccApplication.mUserId + ""));
            mRecordLists = new ArrayList<>();
            if (mShoppingLists.size() != 0) {
                String check;
                for (int i = 0; i < mShoppingLists.size(); i++) {
                    check = "0";
                    mRecordLists.add(check);
                }
                mShoppingAdapter = new ShoppingCarAdapter(getActivity(), mShoppingLists, mRecordLists, R.layout.item_listview_shoppingcar);
                mShoppingCarLv.setAdapter(mShoppingAdapter);
                mShoppingCarLv.setVisibility(View.VISIBLE);
                mTvNull.setVisibility(View.GONE);
            } else {
                mShoppingCarLv.setVisibility(View.GONE);
                mTvNull.setVisibility(View.VISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView checkImg = (ImageView) view.findViewById(R.id.img_circle);
        String isChecked = mRecordLists.get(position);
        if (isChecked.equals("0")) {
            mRecordLists.set(position, "1");
        } else {
            mRecordLists.set(position, "0");
        }
        mShoppingAdapter.notifyDataSetChanged();
        int count = 0;
        double price = 0;
        mTotalPrice = 0;
        for (int i = 0; i < mRecordLists.size(); i++) {
            if (mRecordLists.get(i).equals("1")) {
                count++;
                price = Double.parseDouble(mShoppingLists.get(i).getPrice()) * Integer.parseInt(mShoppingLists.get(i).getCount());
                mTotalPrice = price + mTotalPrice;
            }
        }
        mBtnAccount.setText("结算(" + count + ")");
        mTvPrice.setText("￥" + mTotalPrice);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("删除购物车");
        dialog.setMessage("请确认是否删除当前商品！");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mShoppingLists.remove(position);
                mRecordLists.remove(position);
                mShoppingAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //全选
            case R.id.img_circle_all:
                if (mShoppingLists.size() == 0) {
                    return;
                }

                if (mIsChecked) {
                    for (int i = 0; i < mRecordLists.size(); i++) {
                        mRecordLists.set(i, "0");
                    }
                    mIsChecked = false;
                    mImgChooseAll.setImageResource(R.mipmap.check_circle);
                    mShoppingAdapter.notifyDataSetChanged();
                    mBtnAccount.setText("结算(0)");
                    mTvPrice.setText("￥0");
                } else {
                    mTotalPrice = 0;
                    double price = 0;
                    for (int i = 0; i < mRecordLists.size(); i++) {
                        mRecordLists.set(i, "1");
                        price = Double.parseDouble(mShoppingLists.get(i).getPrice()) * Integer.parseInt(mShoppingLists.get(i).getCount());
                        mTotalPrice = price + mTotalPrice;
                    }
                    mIsChecked = true;
                    mImgChooseAll.setImageResource(R.mipmap.checked_circle);
                    mShoppingAdapter.notifyDataSetChanged();
                    mBtnAccount.setText("结算(" + mRecordLists.size() + ")");
                    mTvPrice.setText("￥" + mTotalPrice);
                }
                break;
            //结算
            case R.id.btn_account:
                if (ZccApplication.mUserId.equals("-1")) {
                    Toast.makeText(mContext, "请先登录！！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }

                boolean checkBusinessChoose = false;
                for (int i = 0; i < mRecordLists.size(); i++) {
                    if (mRecordLists.get(i).equals("1")) {
                        checkBusinessChoose = true;
                        break;
                    }
                }

                if (!checkBusinessChoose) {
                    Toast.makeText(mContext, "请选择商品!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(mContext, "结算成功!", Toast.LENGTH_SHORT).show();

                //加入订单列表
                List<Order> orderLists = new ArrayList<Order>();
                Order order;
                for (int i = 0; i < mShoppingLists.size(); i++) {
                    if (mRecordLists.get(i).equals("1")) {
                        order = new Order();
                        order.setUserId(ZccApplication.mUserId);
                        order.setCount(mShoppingLists.get(i).getCount());
                        order.setBusinessId(mShoppingLists.get(i).getBusinessId());
                        order.setPrice(mShoppingLists.get(i).getPrice());
                        order.setCreateTime((nowTime.get(Calendar.YEAR)) + "-" + (nowTime.get(Calendar.MONTH) + 1) + "-" + nowTime.get(Calendar.DAY_OF_MONTH));
                        orderLists.add(order);
                    }
                }

                //更新UI
                //处理list数据
                mBtnAccount.setText("结算(0)");
                mTvPrice.setText("￥0");
                List<ShoppingCar> checkShoppingCarLists = new ArrayList<>();
                List<ShoppingCar> uncheckedShoppingCarLists = new ArrayList<>();
                List<String> recordLists = new ArrayList<>();
                if (mIsChecked) {
                    mImgChooseAll.setImageResource(R.mipmap.check_circle);
                    mIsChecked = false;
                    //保存订单数据 删除数据库购物车列表
                    try {
                        DBHelper.getInstance(mContext).saveAll(orderLists);
                        DBHelper.getInstance(mContext).deleteAll(mShoppingLists);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    mShoppingLists.clear();
                    mRecordLists.clear();
                } else {
                    Iterator<String> iterator = mRecordLists.iterator();
                    int count = 0;
                    while (iterator.hasNext()) {
                        String str = iterator.next();
                        if (str.equals("1")) {
                            checkShoppingCarLists.add(mShoppingLists.get(count));
                        } else {
                            uncheckedShoppingCarLists.add(mShoppingLists.get(count));
                            recordLists.add(str);
                        }
                        count++;
                    }

                    //保存订单数据 删除数据库购物车列表
                    try {
                        DBHelper.getInstance(mContext).saveAll(orderLists);
                        DBHelper.getInstance(mContext).deleteAll(checkShoppingCarLists);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    mShoppingLists = uncheckedShoppingCarLists;
                    mRecordLists = recordLists;
                }
                mShoppingAdapter.setDatas(mShoppingLists);
                mShoppingAdapter.setRecordLists(mRecordLists);
                mShoppingAdapter.notifyDataSetChanged();

                if (mShoppingLists.size() == 0) {
                    mShoppingCarLv.setVisibility(View.GONE);
                    mTvNull.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
