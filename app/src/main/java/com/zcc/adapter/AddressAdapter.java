package com.zcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.activity.R;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Address;
import com.zcc.entity.User;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/9.
 * <p/>
 * 我的地址adapter
 */
public class AddressAdapter extends BaseAdapter {

    private Context mContext;

    private List<Address> mDatas;

    private int mLayoutId;

    public AddressAdapter(Context context, List<Address> lists, int layoutId) {
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
            convertView = LayoutInflater.from(mContext).inflate(mLayoutId, null);
            vh.mTvName = (TextView) convertView.findViewById(R.id.tv_name);
            vh.mTvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            vh.mTvPhone = (TextView) convertView.findViewById(R.id.tv_phone);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        //找到User
        Address address = mDatas.get(position);
        try {
            User user = DBHelper.getInstance(mContext).findFirst(Selector.from(User.class).where("id", "=", address.getUserId()));
            vh.mTvName.setText(user.getName());
            vh.mTvAddress.setText(address.getAddress());
            vh.mTvPhone.setText(user.getPhone());
        } catch (DbException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    static class ViewHolder {
        TextView mTvName;
        TextView mTvAddress;
        TextView mTvPhone;
    }
}
