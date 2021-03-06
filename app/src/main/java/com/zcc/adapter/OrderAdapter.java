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
import com.zcc.entity.Order;
import com.zcc.utils.Utils;

import java.util.List;

/**
 * Created by 张艳琴 on 2016/9/9.
 */
public class OrderAdapter extends BaseAdapter {
    List<Order> businessList = null;
    Context mContext;
    LayoutInflater inflater;

    public OrderAdapter(Context context, List<Order> businessList) {
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
            convertView = inflater.inflate(R.layout.order_list_item, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.order_item_name);
            holder.price = (TextView) convertView.findViewById(R.id.order_item_price);
            holder.img = (ImageView) convertView.findViewById(R.id.order_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Business business=new Business();
        try {
            business= DBHelper.getInstance(mContext).findFirst(Selector.from(Business.class)
                    .where("id","=",businessList.get(position).getBusinessId()+""));
        } catch (DbException e) {
            e.printStackTrace();
        }
        holder.title.setText(business.getName());
        double sum=Double.parseDouble(business.getPrice())*Double.parseDouble(businessList.get(position).getCount());
        holder.price.setText("共"+businessList.get(position).getCount()+"件商品\t\t实付款："+"￥ " +new java.text.DecimalFormat("#.00").format(sum));
        holder.img.setImageResource(Utils.getInstance(mContext).getImgResource(business.getImgUrl()));
        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView price;
        ImageView img;
    }
}