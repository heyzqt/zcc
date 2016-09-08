package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcc.activity.R;
import com.zcc.entity.ShoppingCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/8.
 */
public class ShoppingCarAdapter extends BaseAdapter {

    private Context mContext;

    private List<ShoppingCar> mDatas = new ArrayList<>();

    private int mLayoutId;

    public ShoppingCarAdapter(Context context, List<ShoppingCar> lists, int layoutId) {
        this.mContext = context;
        this.mDatas = lists;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_listview_shoppingcar, null);
            vh.mImgCircle = (ImageView) convertView.findViewById(R.id.img_circle);
            vh.mImgBusiness = (ImageView) convertView.findViewById(R.id.img_business);
            vh.mTvBusinessName = (TextView) convertView.findViewById(R.id.tv_businessname);
            vh.mTvBusinessPrice = (TextView) convertView.findViewById(R.id.tv_price);
            vh.mTvBusinessNum = (TextView) convertView.findViewById(R.id.tv_businessnum);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        //先找到对应商品对象
        //Utils.getInstance(mContext).getImgResource(mDatas.get(position));
//        ShoppingCar shoppingCar = mDatas.get(position);
//        String businessId = shoppingCar.getBusinessId();
        return convertView;
    }

    static class ViewHolder {
        ImageView mImgCircle;
        ImageView mImgBusiness;
        TextView mTvBusinessName;
        TextView mTvBusinessPrice;
        TextView mTvBusinessNum;
    }
}
