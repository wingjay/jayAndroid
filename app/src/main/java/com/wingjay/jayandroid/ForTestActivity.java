package com.wingjay.jayandroid;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.wingjay.jayandroid.xiami.XiamiPlayerBar;

/**
 * Created by Jay on 5/10/17.
 */

public class ForTestActivity extends BaseActivity {
  ValueAnimator valueAnimator = new ValueAnimator();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_test);
    final XiamiPlayerBar xiamiPlayerBar = (XiamiPlayerBar) findViewById(R.id.playerbar);

    xiamiPlayerBar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        valueAnimator.setIntValues(0, 357);
        valueAnimator.setDuration(10000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
            xiamiPlayerBar.setCurrentTime((int) animation.getAnimatedValue());
          }
        });
        valueAnimator.start();
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (valueAnimator.isRunning()) {
      valueAnimator.cancel();
    }
  }
}
