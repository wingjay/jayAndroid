package com.wingjay.jayandroid.curve;

import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
* Created by jay on 11/5/15.
*/
public class CurveActivity extends BaseActivity {

  @Bind(R.id.change_curve)
  Button changeCurve;

  @Bind(R.id.clock_view)
  ClockView clockView;

  @Bind(R.id.ball_1)
  View ballView1;

  @Bind(R.id.ball_2)
  View ballView2;

  @Bind(R.id.circle_to_rect_view)
  CircleToRectView circleToRectView;

  @Bind(R.id.fancycirclesview)
  FancyCirclesView fancyCirclesView;

  @Bind(R.id.radius_ax)
  EditText radiusAX;

  @Bind(R.id.radius_ay)
  EditText radiusAY;

  @Bind(R.id.radius_bx)
  EditText radiusBX;

  @Bind(R.id.radius_by)
  EditText radiusBY;

  @Bind(R.id.loopTotalCount)
  EditText loopTotalCount;

  @Bind(R.id.durationSeconds)
  EditText durationSeconds;

  @Bind(R.id.speedOuterPoint)
  EditText speedOuterPoint;

  @Bind(R.id.speedInnerPoint)
  EditText speedInnerPoint;

  private ValueAnimator translateYAnimator1;
  private ValueAnimator translateYAnimator2;
  private float startY = 0, endY = -500;
  private long duration = 3000;
  private float currentY = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_curve);
  }

  @OnClick(R.id.circle_to_rect_view)
  void transform() {
      Log.i("circle", "click");
      circleToRectView.startTransform();
  }

  @OnClick(R.id.fancycirclesview)
  void fancycirclesview() {
    fancyCirclesView.setRadius(getIntFromEdittext(radiusAX),
        getIntFromEdittext(radiusAY),
        getIntFromEdittext(radiusBX),
        getIntFromEdittext(radiusBY));
    fancyCirclesView.setDurationSec(getIntFromEdittext(durationSeconds));
    fancyCirclesView.setLoopTotalCount(getIntFromEdittext(loopTotalCount));
    fancyCirclesView.setSpeed(getIntFromEdittext(speedOuterPoint),
        getIntFromEdittext(speedInnerPoint));
    fancyCirclesView.startDraw();
  }

  private int getIntFromEdittext(@NonNull EditText editText) {
    String content = editText.getText().toString();
    if (!TextUtils.isEmpty(content)) {
      return Integer.parseInt(content);
    }
    return -1;
  }

  @OnClick(R.id.change_curve)
  void animate() {
      if (translateYAnimator1 != null && translateYAnimator1.isRunning()) {
          return;
      }
      translateYAnimator1 = ValueAnimator.ofFloat(startY, endY).setDuration(duration);
      translateYAnimator1.setRepeatCount(ValueAnimator.INFINITE);
      translateYAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
              // time fraction
              float timeFraction = animation.getAnimatedFraction();
              // animation fraction
              float animationFraction = timeFraction;
              // calculate the current y
              currentY = ((endY - startY) * animationFraction);
              Log.i("curve", "timeFraction " + timeFraction + ", animationFraction " + animationFraction +
              "currentY " + currentY);
              ballView1.setTranslationY(currentY);
          }
      });


      translateYAnimator2 = ValueAnimator.ofFloat(startY, endY).setDuration(duration);
      translateYAnimator2.setRepeatCount(ValueAnimator.INFINITE);
      translateYAnimator2.setInterpolator(new LinearInterpolator());
      translateYAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          @Override
          public void onAnimationUpdate(ValueAnimator animation) {
              ballView2.setTranslationY((float) animation.getAnimatedValue());
          }
      });

      AnimatorSet set = new AnimatorSet();
      set.playTogether(translateYAnimator1, translateYAnimator2);
      set.setDuration(duration);
      set.start();
  }

  @Override
  protected void onStop() {
      super.onStop();
      if (translateYAnimator1 != null && translateYAnimator1.isRunning()) {
          translateYAnimator1.cancel();
      }
  }

  class MyLinearInterpolator implements TimeInterpolator {
      @Override
      public float getInterpolation(float input) {
          return input;
      }
  }

  class SquareInterpolator implements TimeInterpolator {
      @Override
      public float getInterpolation(float input) {
          return input;
      }
  }




}
