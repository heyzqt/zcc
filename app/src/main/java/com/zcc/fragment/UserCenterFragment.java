package com.zcc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcc.activity.R;
import com.zcc.widget.RoundImageView;

/**
 * Created by heyzqt on 2016/9/6.
 *
 * 用户中心Fragment
 */
public class UserCenterFragment extends Fragment {

    /**
     * 用户头像
     */
    private RoundImageView mUserHeadView;

    /**
     * 用户名
     */
    private TextView mUsernameTv;

    /**
     * 我的地址
     */
    private LinearLayout mAddressLy;

    /**
     * 我的订单
     */
    private LinearLayout mOrderLy;

    /**
     * 我的收藏
     */
    private LinearLayout mCollectLy;

    /**
     * 个人信息
     */
    private LinearLayout mUserInfoLy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_usercenter, null);
        return contentView;
    }
}
