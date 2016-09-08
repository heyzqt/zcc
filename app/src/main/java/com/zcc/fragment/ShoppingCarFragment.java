package com.zcc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zcc.activity.R;
import com.zcc.adapter.ShoppingCarAdapter;
import com.zcc.entity.ShoppingCar;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 *
 * 购物车Fragemnt
 */
public class ShoppingCarFragment extends Fragment{

    /**
     * 购物车列表
     */
    private ListView mShoppingCarLv;

    /**
     * 商品列表
     */
    private List<ShoppingCar> mShoppingLists;

    private ShoppingCarAdapter mShoppingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_shoppingcar, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View view){
        mShoppingCarLv = (ListView) view.findViewById(R.id.lv_shoppingcar);
        mShoppingAdapter = new ShoppingCarAdapter(getActivity(),mShoppingLists,R.layout.item_listview_shoppingcar);
        mShoppingCarLv.setAdapter(mShoppingAdapter);
    }
}
