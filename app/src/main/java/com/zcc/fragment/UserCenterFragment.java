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

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.ZccApplication;
import com.zcc.activity.AddressManageActivity;
import com.zcc.activity.CollectManageActivity;
import com.zcc.activity.LoginActivity;
import com.zcc.activity.OrderManageActivity;
import com.zcc.activity.R;
import com.zcc.activity.UserInfoActivity;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.User;
import com.zcc.utils.Utils;
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
        mUsernameTv = (TextView) convertView.findViewById(R.id.tv_user_head);
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
    public void onResume() {
        //用户登录
        if (!ZccApplication.mUserId.equals("-1")) {
            //从数据库找出对象
            try {
                User user = DBHelper.getInstance(getActivity()).findFirst(Selector.from(User.class).where("id", "=", ZccApplication.mUserId));
                mUserHeadView.setImageResource(R.mipmap.touxiang);
                mUsernameTv.setText(user.getName());
            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            mUserHeadView.setImageResource(R.mipmap.aliuser_place_holder);
            mUsernameTv.setText("请登录");
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //用户头像
            case R.id.img_user_head:
                //用户登录
                if (!ZccApplication.mUserId.equals("-1")) {
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
                //用户未登录
                else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            //我的地址
            case R.id.ry_address:
                if (!ZccApplication.mUserId.equals("-1")) {
                    startActivity(new Intent(getActivity(), AddressManageActivity.class));
                } else {
                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            //我的订单
            case R.id.ry_order:
                if (!ZccApplication.mUserId.equals("-1")) {
                    startActivity(new Intent(getActivity(), OrderManageActivity.class));
                } else {
                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            //我的收藏
            case R.id.ry_collect:
                if (!ZccApplication.mUserId.equals("-1")) {
                    startActivity(new Intent(getActivity(), CollectManageActivity.class));
                } else {
                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            //我的个人中心
            case R.id.ry_userinfo:
                if (!ZccApplication.mUserId.equals("-1")) {
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                } else {
                    Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }
}
