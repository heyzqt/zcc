package com.zcc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcc.activity.AddressManageActivity;
import com.zcc.activity.CollectManageActivity;
import com.zcc.activity.OrderManageActivity;
import com.zcc.activity.R;
import com.zcc.activity.UserInfoActivity;
import com.zcc.widget.RoundImageView;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 用户中心Fragment
 */
public class UserCenterFragment extends Fragment implements View.OnClickListener {

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
    private RelativeLayout mAddressRy;

    /**
     * 我的订单
     */
    private RelativeLayout mOrderRy;

    /**
     * 我的收藏
     */
    private RelativeLayout mCollectRy;

    /**
     * 个人信息
     */
    private RelativeLayout mUserInfoRy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_usercenter, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View convertView) {
        mAddressRy = (RelativeLayout) convertView.findViewById(R.id.ry_address);
        mOrderRy = (RelativeLayout) convertView.findViewById(R.id.ry_order);
        mCollectRy = (RelativeLayout) convertView.findViewById(R.id.ry_collect);
        mUserInfoRy = (RelativeLayout) convertView.findViewById(R.id.ry_userinfo);
        mAddressRy.setOnClickListener(this);
        mOrderRy.setOnClickListener(this);
        mCollectRy.setOnClickListener(this);
        mUserInfoRy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的地址
            case R.id.ry_address:
                startActivity(new Intent(getActivity(), AddressManageActivity.class));
                break;
            //我的订单
            case R.id.ry_order:
                startActivity(new Intent(getActivity(), OrderManageActivity.class));
                break;
            //我的收藏
            case R.id.ry_collect:
                startActivity(new Intent(getActivity(), CollectManageActivity.class));
                break;
            //我的个人中心
            case R.id.ry_userinfo:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
        }
    }
}
