package com.zcc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zcc.activity.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_shoppingcar, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View view){
        mShoppingCarLv = (ListView) view.findViewById(R.id.lv_shoppingcar);
    }
}
