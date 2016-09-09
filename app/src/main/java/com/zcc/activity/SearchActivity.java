package com.zcc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by heyzqt on 2016/9/7.
 */
public class SearchActivity extends Activity {

    private ImageView mIvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
