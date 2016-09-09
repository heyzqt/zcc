package com.zcc.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Toast;


import com.zcc.activity.BusinessInfoActivity;
import com.zcc.entity.AdDomain;

import java.util.List;

/**
 * Created by skysoft on 2016/8/5.
 */
public class BinnerAdapter extends PagerAdapter {
    private List<AdDomain> adList;
    private List<ImageView> imageViews;
    private Context context;

    public BinnerAdapter(List<AdDomain> adList, List<ImageView> imageViews, Context context) {
        this.adList = adList;
        this.imageViews = imageViews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView(imageViews.get(position % imageViews.size()));//
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return super.getItemPosition(object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ViewParent parent = imageViews.get(position % imageViews.size()).getParent();//.getParent();
        if (parent != null) {
            ((ViewPager) container).removeView(imageViews.get(position % imageViews.size()));
        }
        ImageView iv = imageViews.get(position % imageViews.size());
        ((ViewPager) container).addView(iv, 0);
        final AdDomain adDomain = adList.get(position % imageViews.size());
        // 在这个方法里面设置图片的点击事件
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 处理点击图片事件
                Toast.makeText(context, adList.get(position % imageViews.size()).getTopic().toString(), Toast.LENGTH_LONG).show();
                String id = adList.get(position % imageViews.size()).getId().toString();
                Intent intent = new Intent();
                intent.putExtra("businessId", id + "");
                intent.setClass(context, BusinessInfoActivity.class);
                context.startActivity(intent);
            }
        });
        return iv;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }

}
