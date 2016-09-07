package com.zcc.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.zcc.adapter.ViewPagerAdapter;
import com.zcc.fragment.MainFragment;
import com.zcc.fragment.ShoppingCarFragment;
import com.zcc.fragment.TypeFragment;
import com.zcc.fragment.UserCenterFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;

    /**
     * 首页
     */
    private RelativeLayout mMainRy;

    /**
     * 分类
     */
    private RelativeLayout mTypeRy;

    /**
     * 购物车
     */
    private RelativeLayout mShoppingRy;

    /**
     * 个人中心
     */
    private RelativeLayout mUserCenterRy;

    /**
     * viewpager的数据源
     */
    private List<Fragment> mfragmentLists = new ArrayList<>();

    /**
     * viewpager的适配器
     */
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mMainRy = (RelativeLayout) findViewById(R.id.rl_main);
        mTypeRy = (RelativeLayout) findViewById(R.id.rl_type);
        mShoppingRy = (RelativeLayout) findViewById(R.id.rl_shoppingcar);
        mUserCenterRy = (RelativeLayout) findViewById(R.id.rl_usercenter);

        mMainRy.setOnClickListener(this);
        mTypeRy.setOnClickListener(this);
        mShoppingRy.setOnClickListener(this);
        mUserCenterRy.setOnClickListener(this);

        //初始化viewpager
        FragmentManager fragmentManager = getSupportFragmentManager();
        mfragmentLists.add(new MainFragment());
        mfragmentLists.add(new TypeFragment());
        mfragmentLists.add(new ShoppingCarFragment());
        mfragmentLists.add(new UserCenterFragment());
        mViewPagerAdapter = new ViewPagerAdapter(fragmentManager, mfragmentLists);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.rl_main:
                mViewPager.setCurrentItem(0);
                break;
            //分类
            case R.id.rl_type:
                mViewPager.setCurrentItem(1);
                break;
            //购物车
            case R.id.rl_shoppingcar:
                mViewPager.setCurrentItem(2);
                break;
            //个人中心
            case R.id.rl_usercenter:
                mViewPager.setCurrentItem(3);
                break;
        }
    }
}
