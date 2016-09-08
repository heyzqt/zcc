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
 * Created by skysoft on 2016/8/3.
 */
public class Type_fragment_adapter2 extends BaseAdapter {
    List<Business> concern_list = null;
    Context mContext;
    LayoutInflater inflater;

    public Type_fragment_adapter2(Context context, List<Business> list) {
        concern_list = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return concern_list.size();
    }

    @Override
    public Object getItem(int position) {
        return concern_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            inflater = LayoutInflater.from(mContext);
            // 按当前所需的样式，确定new的布局
            convertView = inflater.inflate(R.layout.type_list2_item,
                    parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.type_list2_title);
            holder.count = (TextView) convertView.findViewById(R.id.concern_list2_count);
            holder.icon = (ImageView) convertView.findViewById(R.id.concern_list2_icon);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置资源
        holder.title.setText(concern_list.get(position).getName().toString());
        holder.count.setText("￥ "+concern_list.get(position).getPrice().toString());
        holder.icon.setImageResource(Utils.getInstance(mContext).getImgResource(concern_list.get(position).getImgUrl()));

        return convertView;
    }

    public class ViewHolder {
        TextView title;
        TextView count;
        ImageView icon;
    }

}