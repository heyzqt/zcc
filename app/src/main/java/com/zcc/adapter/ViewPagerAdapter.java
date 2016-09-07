package com.zcc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/7.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mDatas;

    public ViewPagerAdapter(FragmentManager fragmentManager, List<Fragment> lists) {
        super(fragmentManager);
        this.mDatas = lists;
    }


    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
