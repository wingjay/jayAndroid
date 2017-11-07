package com.wingjay.jayandroid.tracker;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * TrackerActivity 无痕埋点
 *
 * @author wingjay
 * @date 2017/10/26
 */
public class TrackerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrackerLog.e("TrackerActivity onCreate");
        setContentView(R.layout.activity_tracker);
        final View header = findViewById(R.id.header);
        findViewById(R.id.tracker).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                header.setVisibility(header.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TrackerLog.e("TrackerActivity onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TrackerLog.e("TrackerActivity onDestroy");
    }
}
