package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * ListView GridView 的BaseAdapter
 */
public abstract class BusinessBaseAdapter<T> extends BaseAdapter {

    private List<T> mLists;

    private int mLayoutId;

    private Context mContext;

    public BusinessBaseAdapter(Context context, List<T> lists, int layoutId) {
        this.mContext = context;
        this.mLists = lists;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public T getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        initView(position, convertView, parent);
        return view;
    }

    public abstract void initView(int position, View convertView, ViewGroup parent);
}
