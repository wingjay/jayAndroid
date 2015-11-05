package com.wingjay.jayandroid.perisope;

import android.os.Bundle;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jay on 11/4/15.
 */
public class PerisopeActivity extends BaseActivity {

    @Bind(R.id.perisope_view)
    PerisopeView perisopeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perisope);
    }

    @OnClick(R.id.create_heart)
    void createHeart() {
        perisopeView.createHeart();
    }


}
