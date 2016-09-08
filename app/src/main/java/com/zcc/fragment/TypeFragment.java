package com.zcc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.activity.BusinessInfoActivity;
import com.zcc.activity.R;
import com.zcc.activity.SearchActivity;
import com.zcc.adapter.Type_fragment_adapter1;
import com.zcc.adapter.Type_fragment_adapter2;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;

import java.util.List;

/**
 * Created by heyzqt on 2016/9/6.
 * <p/>
 * 分类Fragment
 */
public class TypeFragment extends Fragment implements AdapterView.OnItemClickListener{
    ListView lv1;
    ListView lv2;
    Type_fragment_adapter1 adapter1;
    Type_fragment_adapter2 adapter2;
    String[] str;
    int ifs = 1;
    private List<Business> businessList1=null;
    private List<Business> businessList2=null;
    private List<Business> businessList3=null;
    ImageView search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_type, null);
        findview(contentView);
        return contentView;
    }

    private void findview(View v) {
        search= (ImageView) v.findViewById(R.id.type_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity (new Intent(getActivity(),SearchActivity.class));
            }
        });
        str = new String[]{"美妆护肤","秋装新品","数码商城","换季特卖","水果超市","数码商城","美妆护肤","服装城",
                "美妆护肤","秋装新品","数码商城","换季特卖","水果超市","数码商城","美妆护肤","服装城"};
        adapter1 = new Type_fragment_adapter1(getActivity(), str);
        lv1 = (ListView) v.findViewById(R.id.type_listview1);
        lv1.setAdapter(adapter1);
        lv1.setOnItemClickListener(this);
        ifs = 1;
        try {
            businessList1 = DBHelper.getInstance(getActivity()).findAll(Selector.from(Business.class).where("type", "=", "美妆护肤"));
            businessList2 = DBHelper.getInstance(getActivity()).findAll(Selector.from(Business.class).where("type", "=", "秋装新品"));
            businessList3 = DBHelper.getInstance(getActivity()).findAll(Selector.from(Business.class).where("type", "=", "数码商城"));
        } catch (DbException e) {
            e.printStackTrace();
        }
        adapter2 = new Type_fragment_adapter2(getActivity(),businessList1);
        lv2 = (ListView) v.findViewById(R.id.type_listview2);
        lv2.setAdapter(adapter2);
        lv2.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.type_listview1:
                adapter1.changeSelected(position);
                if (position % 3 == 0) {
                    adapter2 = new Type_fragment_adapter2(getActivity(),businessList1);
                    ifs = 1;
                    lv2.setAdapter(adapter2);
                } else if(position % 3 == 1){
                    adapter2 = new Type_fragment_adapter2(getActivity(),businessList2);
                    ifs=2;
                    lv2.setAdapter(adapter2);
                }else{
                    adapter2 = new Type_fragment_adapter2(getActivity(),businessList3);
                    ifs=3;
                    lv2.setAdapter(adapter2);
                }
                break;
            case R.id.type_listview2:
                  Toast.makeText(getActivity(), "啊，你点中我了 " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                if(ifs==1){
                    intent.putExtra("businessId", businessList1.get(position).getId() + "");
                }else if(ifs==2){
                    intent.putExtra("businessId", businessList2.get(position).getId() + "");
                }else{
                    intent.putExtra("businessId", businessList2.get(position).getId() + "");
                }
                intent.setClass(getActivity(), BusinessInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
