package com.zcc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lidroid.xutils.exception.DbException;
import com.zcc.activity.R;
import com.zcc.adapter.ShoppingCarAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.ShoppingCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 购物车Fragemnt
 */
public class ShoppingCarFragment extends Fragment {

    /**
     * 购物车列表
     */
    private ListView mShoppingCarLv;

    /**
     * 商品列表
     */
    private List<ShoppingCar> mShoppingLists = new ArrayList<>();

    private ShoppingCarAdapter mShoppingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_shoppingcar, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View view) {
        //初始化数据
        try {
            mShoppingLists = DBHelper.getInstance(getActivity()).findAll(ShoppingCar.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        mShoppingCarLv = (ListView) view.findViewById(R.id.lv_shoppingcar);
        mShoppingAdapter = new ShoppingCarAdapter(getActivity(), mShoppingLists, R.layout.item_listview_shoppingcar);
        mShoppingCarLv.setAdapter(mShoppingAdapter);
    }
}
