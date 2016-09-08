package com.zcc.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zcc.ZccApplication;
import com.zcc.activity.AddressManageActivity;
import com.zcc.activity.CollectManageActivity;
import com.zcc.activity.LoginActivity;
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

    /**
     * 请求码
     */
    private final int REQUEST_CODE = 1001;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_usercenter, null);
        initView(contentView);
        return contentView;
    }

    private void initView(View convertView) {
        mUserHeadView = (RoundImageView) convertView.findViewById(R.id.img_user_head);
        mAddressRy = (RelativeLayout) convertView.findViewById(R.id.ry_address);
        mOrderRy = (RelativeLayout) convertView.findViewById(R.id.ry_order);
        mCollectRy = (RelativeLayout) convertView.findViewById(R.id.ry_collect);
        mUserInfoRy = (RelativeLayout) convertView.findViewById(R.id.ry_userinfo);
        mUserHeadView.setOnClickListener(this);
        mAddressRy.setOnClickListener(this);
        mOrderRy.setOnClickListener(this);
        mCollectRy.setOnClickListener(this);
        mUserInfoRy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //用户头像
            case R.id.img_user_head:
                if (ZccApplication.mUserId != -1) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("退出登录");
                    dialog.setMessage("请确认是否要退出登录!");
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(), "确认", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), REQUEST_CODE);
                }
                break;
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
