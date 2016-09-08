package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.activity.R;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;
import com.zcc.entity.ShoppingCar;
import com.zcc.utils.Utils;

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
            vh.mTvBusinessPrice = (TextView) convertView.findViewById(R.id.tv_businessprice);
            vh.mTvBusinessNum = (TextView) convertView.findViewById(R.id.tv_businessnum);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        //先找到对应商品对象
        ShoppingCar shoppingCar = mDatas.get(position);
        Business business = new Business();
        String businessId = shoppingCar.getBusinessId();
        try {
            business = DBHelper.getInstance(mContext).findFirst(Selector.from(Business.class).where("id", "=", businessId));
        } catch (DbException e) {
            e.printStackTrace();
        }

        //设置商品数据
        vh.mImgBusiness.setImageResource(Utils.getInstance(mContext).getImgResource(business.getImgUrl()));
        vh.mTvBusinessName.setText(business.getName());
        vh.mTvBusinessPrice.setText("￥" + business.getPrice());
        vh.mTvBusinessNum.setText("x" + shoppingCar.getCount());
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
