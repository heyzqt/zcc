package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcc.activity.R;

/**
 * Created by zyq on 2016/9/8.
 */
public class TypeFragmentAdapterLeft extends BaseAdapter {
    String s[] = null;
    Context mContext;
    LayoutInflater inflater;
    int mSelect=0;

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    public TypeFragmentAdapterLeft(Context context,
                                   String s[]) {
        this.s = s;
        mContext = context;
    }

    @Override
    public int getCount() {
        return s.length;
    }

    @Override
    public Object getItem(int position) {
        return s[position];
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
            convertView = inflater.inflate(R.layout.type_list1_item,
                    parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView
                    .findViewById(R.id.concern_list1_text);
            holder.rl = (RelativeLayout) convertView.findViewById(R.id.concern_list1_rl);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 设置资源

        holder.title.setText(s[position].toString());
        if(mSelect==position){
            holder.rl.setBackgroundResource(R.color.color_white); //选中项背景
            holder.title.setTextColor(mContext.getResources().getColor(R.color.color_main));//setTextColor(R.color.colorMain);getResources().getColor(R.color.colorMain)`
        }else{
            holder.rl.setBackgroundResource(R.color.color_background);;  //其他项背景
            holder.title.setTextColor(mContext.getResources().getColor(R.color.color_black));
        }


        return convertView;
    }

    public class ViewHolder {
        TextView title;
        RelativeLayout rl;
    }
}