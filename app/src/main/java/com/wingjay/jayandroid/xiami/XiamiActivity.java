package com.wingjay.jayandroid.xiami;

import android.os.Bundle;
import com.wingjay.jayandroid.BaseActivity;

/**
 * XiamiActivity
 *
 * @author wingjay
 * @date 2017/09/13
 */
public class XiamiActivity extends BaseActivity {
    boolean loading = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_test);
        //final PlayerProgressBar playerProgressBar = (PlayerProgressBar) findViewById(R.id.playerbar);
        //
        //playerProgressBar.setLoading(loading);
        //playerProgressBar.setTime(100, 300);
        //playerProgressBar.setOnPlayerDragListener(new OnPlayerDragListener() {
        //    @Override
        //    public void onDrag(@IntRange(from = 0, to = 100) int progress, boolean isDragEnd) {
        //        Log.e("jaydebug", "drag progress: " + progress + ", isDragEnd: " + isDragEnd);
        //    }
        //});
        //findViewById(R.id.action).setOnClickListener(new OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        loading = !loading;
        //        playerProgressBar.setLoading(loading);
        //    }
        //});
    }
}
