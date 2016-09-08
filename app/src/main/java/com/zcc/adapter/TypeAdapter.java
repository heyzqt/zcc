package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcc.activity.R;
import com.zcc.entity.Business;

import java.util.List;

/**
 * Created by 张艳琴 on 2016/9/7.
 */
public class TypeAdapter extends BaseAdapter {
    List<Business> businessList = null;
    Context mContext;
    LayoutInflater inflater;

    public TypeAdapter (Context context, List<Business> businessList) {
        this.businessList = businessList;
        mContext = context;
    }
// return businessList.size();
    @Override
    public int getCount() {
        return 10;
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
//            holder.rl_car= (RelativeLayout) convertView.findViewById(R.id.type_item_rl_car);
//            holder.car= (ImageView) convertView.findViewById(R.id.type_image_car);
//            holder.tv_car= (TextView) convertView.findViewById(R.id.type_item_car);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(businessList.get(position).getName());
        holder.price.setText("￥ "+businessList.get(position).getPrice());
        holder.img.setImageResource(getResource(businessList.get(position).getImgUrl()));
        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView price;
        ImageView img;
//        ImageView car;
//        TextView tv_car;
//        RelativeLayout rl_car;
    }

    public int getResource(String imageName) {
        int resId = mContext.getResources().getIdentifier(imageName, "mipmap", mContext.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }
}