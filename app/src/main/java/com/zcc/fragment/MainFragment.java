package com.zcc.fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.zcc.activity.BusinessInfoActivity;
import com.zcc.activity.BusinessTypeActivity;
import com.zcc.activity.R;
import com.zcc.activity.SearchActivity;
import com.zcc.adapter.BinnerAdapter;
import com.zcc.adapter.TypeAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.AdDomain;
import com.zcc.entity.Business;
import com.zcc.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * MainFragment
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private ImageView mIvSearch;
    private static final String KEY_CONTENT = "要闻";
    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合
    private List<Business> mListBusiness = null;
    private NoScrollListView listView;
    TypeAdapter typeAdapter;

    private List<View> dots; // 图片标题正文的那些点
    private List<View> dotList;
    private TextView tv_topic;
    private int currentItem = 0;    // 当前图片的索引号
    // 定义的三个指示点
    private View dot0;
    private View dot1;
    private View dot2;
    private ScheduledExecutorService scheduledExecutorService;
    // 轮播banner的数据
    private List<AdDomain> adList;
    BinnerAdapter binnerAdapter;
    ArrayList<HashMap<String, String>> list = null;
    private RelativeLayout rl_1, rl_2, rl_3, rl_4, rl_5, rl_6;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            adViewPager.setCurrentItem(currentItem);
        }

    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_main, null);
        findView(contentView);
        startAd();
        return contentView;
    }


    private void findView(View v) {
        // 广告数据
        imageViews = new ArrayList<ImageView>();
        //    点
        dots = new ArrayList<View>();
        dotList = new ArrayList<View>();
        dot0 = v.findViewById(R.id.v_dot0);
        dot1 = v.findViewById(R.id.v_dot1);
        dot2 = v.findViewById(R.id.v_dot2);
        dots.add(dot0);
        dots.add(dot1);
        dots.add(dot2);
        tv_topic = (TextView) v.findViewById(R.id.binner_tv_topic);
        try {
            mListBusiness = DBHelper.getInstance(getActivity()).findAll(Business.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        adList = getBannerAd();
        adViewPager = (ViewPager) v.findViewById(R.id.vp);
        binnerAdapter = new BinnerAdapter(adList, imageViews, getContext());
        adViewPager.setAdapter(binnerAdapter);
        // 设置一个监听器，当ViewPager中的页面改变时调用
        adViewPager.setOnPageChangeListener(new MyPageChangeListener());
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(adList.get(i).getImgUrl());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
            dots.get(i).setVisibility(View.VISIBLE);
            dotList.add(dots.get(i));
        }

        rl_1 = (RelativeLayout) v.findViewById(R.id.rl_01);
        rl_1.setOnClickListener(this);
        rl_2 = (RelativeLayout) v.findViewById(R.id.rl_02);
        rl_2.setOnClickListener(this);
        rl_3 = (RelativeLayout) v.findViewById(R.id.rl_03);
        rl_3.setOnClickListener(this);
        rl_4 = (RelativeLayout) v.findViewById(R.id.rl_04);
        rl_4.setOnClickListener(this);
        rl_5 = (RelativeLayout) v.findViewById(R.id.rl_05);
        rl_5.setOnClickListener(this);
        rl_6 = (RelativeLayout) v.findViewById(R.id.rl_06);
        rl_6.setOnClickListener(this);
        mIvSearch = (ImageView) v.findViewById(R.id.Main_search);
        mIvSearch.setOnClickListener(this);
        listView = (NoScrollListView) v.findViewById(R.id.main_list);
        typeAdapter = new TypeAdapter(getActivity(), mListBusiness);
        listView.setAdapter(typeAdapter);
    }

    private void startAd() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每五秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,
                TimeUnit.SECONDS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_01:
            case R.id.rl_04://进入分类的Activity 护肤
                Intent intent = new Intent();
                intent.putExtra("type", "美妆护肤");
                intent.setClass(getActivity(), BusinessTypeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_02:
            case R.id.rl_05://进入分类的Activity  衣服
                Intent intent1 = new Intent();
                intent1.putExtra("type", "秋装新品");
                intent1.setClass(getActivity(), BusinessTypeActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_03:
            case R.id.rl_06://进入分类的Activity  数码
                Intent intent2 = new Intent();
                intent2.putExtra("type", "数码商城");
                intent2.setClass(getActivity(), BusinessTypeActivity.class);
                startActivity(intent2);
                break;
            case R.id.Main_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }

    }

    private class ScrollTask implements Runnable {

        @Override
        public void run() {
            synchronized (adViewPager) {
                currentItem = currentItem + 1;
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            AdDomain adDomain = adList.get(position % 3);
            tv_topic.setText(adDomain.getTopic());//设置TextView数据
            dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            dots.get(position % 3).setBackgroundResource(R.drawable.dot_focused);
            oldPosition = position % 3;
        }
    }

    /**
     * 轮播广播模拟数据
     *
     * @return
     */
    public List<AdDomain> getBannerAd() {
        List<AdDomain> adList = new ArrayList<AdDomain>();
        for (int i = 0; i < 3; i++) {
            AdDomain adDomain = new AdDomain();
            adDomain.setTopic(mListBusiness.get(i).getName());
            adDomain.setImgUrl(getResource(mListBusiness.get(i).getImgUrl()));
            adDomain.setId(mListBusiness.get(i).getId() + "");
            adList.add(adDomain);
        }
        return adList;
    }

    public int getResource(String imageName) {
        int resId = getResources().getIdentifier(imageName, "mipmap", getActivity().getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}
