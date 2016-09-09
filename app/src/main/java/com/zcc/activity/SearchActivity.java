package com.zcc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.zcc.adapter.TypeAdapter;
import com.zcc.dbutils.DBHelper;
import com.zcc.entity.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyzqt on 2016/9/7.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private ImageView mIvBack;

    private EditText mEdtSearch;

    private TextView mTvSearch;

    private ListView mSearchLv;

    private TextView mTvNull;

    private List<Business> mBusinessLists = new ArrayList<>();

    private TypeAdapter mSearchAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
    }

    private void init() {
        mContext = this;
        mIvBack = (ImageView) findViewById(R.id.img_back);
        mEdtSearch = (EditText) findViewById(R.id.edt_search);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mSearchLv = (ListView) findViewById(R.id.lv_search);
        mTvNull = (TextView) findViewById(R.id.tv_listview_is_null);

        mIvBack.setOnClickListener(this);
        mTvSearch.setOnClickListener(this);

        mSearchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("businessId", mBusinessLists.get(position).getId() + "");
                intent.setClass(getApplicationContext(), BusinessInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.img_back:
                finish();
                break;
            //搜索
            case R.id.tv_search:
                String result = mEdtSearch.getText().toString();
                //模糊查询
                try {
                    mBusinessLists = DBHelper.getInstance(mContext).findAll(Selector.from(Business.class).where("name", "like", "%" + result + "%"));
                    if (mBusinessLists.size() != 0) {
                        mSearchAdapter = new TypeAdapter(mContext, mBusinessLists);
                        mSearchLv.setAdapter(mSearchAdapter);
                        mTvNull.setVisibility(View.GONE);
                        mSearchLv.setVisibility(View.VISIBLE);
                    } else {
                        mTvNull.setVisibility(View.VISIBLE);
                        mSearchLv.setVisibility(View.GONE);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
