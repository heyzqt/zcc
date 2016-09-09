package com.zcc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.activity.R;
import com.zcc.adapter.ShoppingCarAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Order;
import com.zcc.entity.ShoppingCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 购物车Fragemnt
 */
public class ShoppingCarFragment extends Fragment {

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
    private boolean isChecked = false;

    /**
     * 记录商品的点中状态
     * <p/>
     * 0：未选中  1：选中
     */
    private List<String> mRecordLists = new ArrayList<>();

    private double mTotalPrice = 0;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_shoppingcar, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View view) {

        mContext = getActivity();
        mImgChooseAll = (ImageView) view.findViewById(R.id.img_circle_all);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mBtnAccount = (Button) view.findViewById(R.id.btn_account);
        mTvNull = (TextView) view.findViewById(R.id.tv_listview_is_null);
        mShoppingCarLv = (ListView) view.findViewById(R.id.lv_shoppingcar);

        if (isChecked) {
            mImgChooseAll.setImageResource(R.mipmap.checked_circle);
        } else {
            mImgChooseAll.setImageResource(R.mipmap.check_circle);
        }

        //初始化数据
        try {
            mShoppingLists = DBHelper.getInstance(getActivity()).findAll(ShoppingCar.class);
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

        mImgChooseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChecked) {
                    for (int i = 0; i < mRecordLists.size(); i++) {
                        mRecordLists.set(i, "0");
                    }
                    isChecked = false;
                    mImgChooseAll.setImageResource(R.mipmap.check_circle);
                    mShoppingAdapter.notifyDataSetChanged();
                    mBtnAccount.setText("结算(0)");
                    mTvPrice.setText("￥0");
                } else {
                    mTotalPrice = 0;
                    for (int i = 0; i < mRecordLists.size(); i++) {
                        mRecordLists.set(i, "1");
                        mTotalPrice = Double.parseDouble(mShoppingLists.get(i).getPrice()) + mTotalPrice;
                    }
                    isChecked = true;
                    mImgChooseAll.setImageResource(R.mipmap.checked_circle);
                    mShoppingAdapter.notifyDataSetChanged();
                    mBtnAccount.setText("结算(" + mRecordLists.size() + ")");
                    mTvPrice.setText("￥"+mTotalPrice);
                }
            }
        });

        mShoppingCarLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                mTotalPrice = 0;
                for (int i = 0; i < mRecordLists.size(); i++) {
                    if (mRecordLists.get(i).equals("1")) {
                        count++;
                        mTotalPrice = Double.parseDouble(mShoppingLists.get(i).getPrice()) + mTotalPrice;
                    }
                }
                mBtnAccount.setText("结算(" + count + ")");
                mTvPrice.setText("￥"+mTotalPrice);
            }
        });

        mBtnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "结算成功!", Toast.LENGTH_SHORT).show();
                mImgChooseAll.setImageResource(R.mipmap.check_circle);
                mTvPrice.setText("￥0");
                mBtnAccount.setText("结算(0)");

                //加入订单列表
                List<Order> orderLists = new ArrayList<Order>();
                Order order;
                for(int i=0;i<mShoppingLists.size();i++){
                    order = new Order();
                    order.setUserId(ZccApplication.mUserId);
                    order.setBusinessId(mShoppingLists.get(i).getBusinessId());
                    order.setPrice(mShoppingLists.get(i).getPrice());
                    orderLists.add(order);
                }

                //删除购物车列表
                try {
                    DBHelper.getInstance(mContext).saveAll(orderLists);
                    DBHelper.getInstance(mContext).deleteAll(mShoppingLists);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
