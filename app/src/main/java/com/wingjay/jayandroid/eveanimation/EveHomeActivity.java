package com.wingjay.jayandroid.eveanimation;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Jay on 11/18/16.
 */

public class EveHomeActivity extends BaseActivity {

  @Bind(R.id.content_root)
  View contentRoot;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_eve_home);
  }

  @OnClick(R.id.button)
  void jumpToNextPage() {
    contentRoot.animate().translationY(contentRoot.getHeight())
        .alpha(1.0f)
        .setDuration(500)
        .setListener(new Animator.AnimatorListener() {
          @Override
          public void onAnimationStart(Animator animation) {

          }

          @Override
          public void onAnimationEnd(Animator animation) {
            EveHomeActivity.this.finish();
            overridePendingTransition(0, android.R.animator.fade_out);
          }

          @Override
          public void onAnimationCancel(Animator animation) {

          }

          @Override
          public void onAnimationRepeat(Animator animation) {

          }
        })
        .start();
  }

}
