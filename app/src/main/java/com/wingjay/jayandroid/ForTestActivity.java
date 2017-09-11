package com.wingjay.jayandroid;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.wingjay.jayandroid.xiami.PlayerProgressBar;
import com.wingjay.jayandroid.xiami.PlayerProgressBar.OnPlayerDragListener;

/**
 * Created by Jay on 5/10/17.
 */

public class ForTestActivity extends BaseActivity {
  boolean loading = true;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    final PlayerProgressBar playerProgressBar = (PlayerProgressBar) findViewById(R.id.playerbar);

    playerProgressBar.setLoading(loading);
    playerProgressBar.setTime(100, 300);
    playerProgressBar.setOnPlayerDragListener(new OnPlayerDragListener() {
      @Override
      public void onDrag(@IntRange(from = 0, to = 100) int progress, boolean isDragEnd) {
        Log.e("jaydebug", "drag progress: " + progress + ", isDragEnd: " + isDragEnd);
      }
    });
    findViewById(R.id.action).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        loading = !loading;
        playerProgressBar.setLoading(loading);
      }
    });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
