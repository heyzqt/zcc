package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcc.activity.R;
import com.zcc.entity.Business;
import com.zcc.utils.Utils;

import java.util.List;

/**
 * Created by 张艳琴 on 2016/9/7.
 */
public class TypeAdapter extends BaseAdapter {
    List<Business> businessList = null;
    Context mContext;
    LayoutInflater inflater;

    public TypeAdapter(Context context, List<Business> businessList) {
        this.businessList = businessList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return businessList.size();
    }

    @Override
    public Object getItem(int position) {
        return businessList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(mContext);
            // 按当前所需的样式，确定new的布局
            convertView = inflater.inflate(R.layout.type_item, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.type_item_name);
            holder.price = (TextView) convertView.findViewById(R.id.type_item_price);
            holder.img = (ImageView) convertView.findViewById(R.id.type_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(businessList.get(position).getName());
        holder.price.setText("￥ " + businessList.get(position).getPrice());
        holder.img.setImageResource(Utils.getInstance(mContext).getImgResource(businessList.get(position).getImgUrl()));
        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView price;
        ImageView img;
    }
}