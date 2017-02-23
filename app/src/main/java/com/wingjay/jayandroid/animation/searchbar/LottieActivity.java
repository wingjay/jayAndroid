package com.wingjay.jayandroid.animation.searchbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;

/**
 * Airbnb https://github.com/airbnb/lottie-android
 */

public class LottieActivity extends BaseActivity {

  @Bind(R.id.test_animation_view)
  LottieAnimationView lottieAnimationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lottie);

//    lottieAnimationView.setAnimation("test99.json");
    lottieAnimationView.setAnimation("test18.json", LottieAnimationView.CacheStrategy.Weak);
    lottieAnimationView.loop(true);
    final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lottieAnimationView.getLayoutParams();

    lottieAnimationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        Log.d("jaydebug", "onAnimationUpdate progress: " + lottieAnimationView.getProgress() + ", valueAnimator: " + valueAnimator.getAnimatedValue());
        layoutParams.width = 100 + 1000 * (int)valueAnimator.getAnimatedFraction();
        layoutParams.height = 100 + 1000 * (int)valueAnimator.getAnimatedFraction();
        lottieAnimationView.setLayoutParams(layoutParams);
        Log.d("jaydebug", "width: " + lottieAnimationView.getWidth() + ", height: " + lottieAnimationView.getHeight());
      }
    });
    lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {
        Log.d("jaydebug", "onAnimationStart isAnimating: " +lottieAnimationView.isAnimating());
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        Log.d("jaydebug", "onAnimationEnd isAnimating: " +lottieAnimationView.isAnimating());
      }

      @Override
      public void onAnimationCancel(Animator animator) {
        Log.d("jaydebug", "onAnimationCancel isAnimating: " +lottieAnimationView.isAnimating());
      }

      @Override
      public void onAnimationRepeat(Animator animator) {
        Log.d("jaydebug", "onAnimationRepeat isAnimating: " +lottieAnimationView.isAnimating());
      }
    });



    lottieAnimationView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        lottieAnimationView.playAnimation();
      }
    });
  }

  @Override
  protected void onStop() {
    super.onStop();
    lottieAnimationView.cancelAnimation();
  }
}
