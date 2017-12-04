package com.wingjay.jayandroid.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * RecyclerViewActivity
 *
 * @author wingjay
 * @date 2017/11/22
 */
public class RecyclerViewActivity extends BaseActivity {
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(getLayoutManager());
    }

    private LayoutManager getLayoutManager() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 0;
            }
        });

        return layoutManager;
    }
}
