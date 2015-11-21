package com.wingjay.jayandroid.statusbar;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;

/**
 * Created by jay on 11/20/15.
 */
public class StatusBarActivity extends BaseActivity {

    @Bind(R.id.tool_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);

        setSupportActionBar(toolbar);
        toolbar.setTitle("ToolBar");
        toolbar.setVisibility(View.GONE);

//        getWindow().setStatusBarColor();

    }
}
