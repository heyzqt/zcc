package com.zcc.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private ImageView mMainImg;

    private TextView mMainTv;

    /**
     * 分类
     */
    private RelativeLayout mTypeRy;

    private ImageView mTypeImg;

    private TextView mTypeTv;

    /**
     * 购物车
     */
    private RelativeLayout mShoppingRy;

    private ImageView mShoppingImg;

    private TextView mShoppingTv;

    /**
     * 个人中心
     */
    private RelativeLayout mUserCenterRy;

    private ImageView mUserCenterImg;

    private TextView mUserCenterTv;

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
        //首页
        mMainRy = (RelativeLayout) findViewById(R.id.rl_main);
        mMainImg = (ImageView) findViewById(R.id.img_main);
        mMainTv = (TextView) findViewById(R.id.tv_main);
        //分类
        mTypeRy = (RelativeLayout) findViewById(R.id.rl_type);
        mTypeImg = (ImageView) findViewById(R.id.img_type);
        mTypeTv = (TextView) findViewById(R.id.tv_type);
        //购物车
        mShoppingRy = (RelativeLayout) findViewById(R.id.rl_shoppingcar);
        mShoppingImg = (ImageView) findViewById(R.id.img_shoppingcar);
        mShoppingTv = (TextView) findViewById(R.id.tv_shoppingcar);
        //个人中心
        mUserCenterRy = (RelativeLayout) findViewById(R.id.rl_usercenter);
        mUserCenterImg = (ImageView) findViewById(R.id.img_usercenter);
        mUserCenterTv = (TextView) findViewById(R.id.tv_usercenter);

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

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    //首页
                    case 0:
                        clearBottomView();
                        mMainImg.setImageResource(R.mipmap.guide_home_on);
                        mMainTv.setTextColor(getResources().getColor(R.color.color_main));
                        break;
                    //分类
                    case 1:
                        clearBottomView();
                        mTypeImg.setImageResource(R.mipmap.guide_discover_on);
                        mTypeTv.setTextColor(getResources().getColor(R.color.color_main));
                        break;
                    //购物车
                    case 2:
                        clearBottomView();
                        mShoppingImg.setImageResource(R.mipmap.guide_cart_on);
                        mShoppingTv.setTextColor(getResources().getColor(R.color.color_main));
                        break;
                    //个人中心
                    case 3:
                        clearBottomView();
                        mUserCenterImg.setImageResource(R.mipmap.guide_account_on);
                        mUserCenterTv.setTextColor(getResources().getColor(R.color.color_main));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //启动app时，底部导航栏的图标显示
        clearBottomView();
        mMainImg.setImageResource(R.mipmap.guide_home_on);
        mMainTv.setTextColor(ContextCompat.getColor(this,R.color.color_main));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页
            case R.id.rl_main:
                mViewPager.setCurrentItem(0);
                clearBottomView();
                mMainImg.setImageResource(R.mipmap.guide_home_on);
                mMainTv.setTextColor(ContextCompat.getColor(this,R.color.color_main));
                break;
            //分类
            case R.id.rl_type:
                mViewPager.setCurrentItem(1);
                clearBottomView();
                mTypeImg.setImageResource(R.mipmap.guide_discover_on);
                mTypeTv.setTextColor(getResources().getColor(R.color.color_main));
                break;
            //购物车
            case R.id.rl_shoppingcar:
                mViewPager.setCurrentItem(2);
                clearBottomView();
                mShoppingImg.setImageResource(R.mipmap.guide_cart_on);
                mShoppingTv.setTextColor(getResources().getColor(R.color.color_main));
                break;
            //个人中心
            case R.id.rl_usercenter:
                mViewPager.setCurrentItem(3);
                clearBottomView();
                mUserCenterImg.setImageResource(R.mipmap.guide_account_on);
                mUserCenterTv.setTextColor(getResources().getColor(R.color.color_main));
                break;
        }
    }

    /**
     * 清除图片文字效果
     */
    private void clearBottomView() {
        mMainImg.setImageResource(R.mipmap.guide_home_nm);
        mTypeImg.setImageResource(R.mipmap.guide_discover_nm);
        mShoppingImg.setImageResource(R.mipmap.guide_cart_nm);
        mUserCenterImg.setImageResource(R.mipmap.guide_account_nm);
        mMainTv.setTextColor(getResources().getColor(R.color.color_font));
        mTypeTv.setTextColor(getResources().getColor(R.color.color_font));
        mShoppingTv.setTextColor(getResources().getColor(R.color.color_font));
        mUserCenterTv.setTextColor(getResources().getColor(R.color.color_font));
    }


}
