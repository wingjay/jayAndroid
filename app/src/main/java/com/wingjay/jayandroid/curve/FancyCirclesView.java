package com.wingjay.jayandroid.curve;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by Jay on 1/24/16.
 */
public class FancyCirclesView extends View {

  private final float RADIUS_AX_DEFAULT = 300, RADIUS_AY_DEFAULT = 10;
  private final float RADIUS_BX_DEFAULT = 10, RADIUS_BY_DEFAULT = 300;

  private final float SPEED_OUTER_POINT_DEFAULT = 0.032f, SPEED_INNER_POINT_DEFAULT = 0.005f;

  private final int DURATION_SEC_DEFAULT = 50;
  private final int LOOP_TOTAL_COUNT_DEFAULT = 30;

  private float radiusAX = RADIUS_AX_DEFAULT, radiusAY = RADIUS_AY_DEFAULT;
  private float radiusBX = RADIUS_BX_DEFAULT, radiusBY = RADIUS_BY_DEFAULT;

  private float speedOuterPoint = SPEED_OUTER_POINT_DEFAULT, speedInnerPoint = SPEED_INNER_POINT_DEFAULT;

  private int width, height;
  private int defaultX = 700, defaultY = 700;

  private Paint linePaint;

  float angleA = 0f, angleB = 0f;
  float aX, aY, bX, bY;

  private int backgroundColor = Color.BLACK;
  private int lineColor = Color.WHITE;
  private int lineAlpha = 120;
  private Bitmap bitmap;

  private int durationSec = DURATION_SEC_DEFAULT;
  private int loopTotalCount = LOOP_TOTAL_COUNT_DEFAULT;

  public FancyCirclesView(Context context) {
    this(context, null);
  }

  public FancyCirclesView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FancyCirclesView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setupBackground();
    setupPaint();
    bitmap = Bitmap.createBitmap(defaultX, defaultY, Bitmap.Config.ARGB_8888);
  }

  private void setupBackground() {
    setBackgroundColor(backgroundColor);
  }

  private void setupPaint() {
    linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    linePaint.setColor(lineColor);
    linePaint.setStyle(Paint.Style.FILL);
    linePaint.setStrokeWidth(1);
    linePaint.setAlpha(lineAlpha);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
      width = defaultX;
    } else {
      width = MeasureSpec.getSize(widthMeasureSpec);
    }
    if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
      height = defaultY;
    } else {
      height = MeasureSpec.getSize(heightMeasureSpec);
    }
    setMeasuredDimension(width, height);
  }

  private void setupSize() {
    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    Bitmap tempBitmap = bitmap;
    if (!tempBitmap.isMutable()) {
      tempBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }
    Canvas c = new Canvas(tempBitmap);
    c.translate(width / 2, height / 2);
    aX = (float) Math.cos(angleA) * radiusAX;
    aY = (float) Math.sin(angleA) * radiusAY;
    bX = (float) Math.cos(angleB) * radiusBX;
    bY = (float) Math.sin(angleB) * radiusBY;
    c.drawLine(aX, aY, bX, bY, linePaint);

    canvas.drawBitmap(tempBitmap, 0, 0, linePaint);
  }

  public void setRadius(float radiusAX, float radiusAY, float radiusBX, float radiusBY) {
    setDefaultRadius();
    if (radiusAX > 0) {
      this.radiusAX = radiusAX;
    }
    if (radiusAY > 0) {
      this.radiusAY = radiusAY;
    }
    if (radiusBX > 0) {
      this.radiusBX = radiusBX;
    }
    if (radiusBY > 0) {
      this.radiusBY = radiusBY;
    }
  }

  private void setDefaultRadius() {
    this.radiusAX = RADIUS_AX_DEFAULT;
    this.radiusAY = RADIUS_AY_DEFAULT;
    this.radiusBX = RADIUS_BX_DEFAULT;
    this.radiusBY = RADIUS_BY_DEFAULT;
  }

  public void setDurationSec(int durationSec) {
    if (durationSec > 0) {
      this.durationSec = durationSec;
    } else {
      this.durationSec = DURATION_SEC_DEFAULT;
    }
  }

  public void setLoopTotalCount(int loopTotalCount) {
    if (loopTotalCount > 0) {
      this.loopTotalCount = loopTotalCount;
    } else {
      this.loopTotalCount = LOOP_TOTAL_COUNT_DEFAULT;
    }
  }

  public void setSpeed(int speedOuterPoint, int speedInnerPoint) {
    if (speedOuterPoint > 0) {
      this.speedOuterPoint = (float)speedOuterPoint / 1000.f;
    } else {
      this.speedOuterPoint = SPEED_OUTER_POINT_DEFAULT;
    }
    if (speedInnerPoint > 0) {
      this.speedInnerPoint = (float)speedInnerPoint / 1000.f;
    } else {
      this.speedInnerPoint = SPEED_INNER_POINT_DEFAULT;
    }
  }

  public void startDraw() {
    if (bitmap != null) {
      bitmap.recycle();
      invalidate();
    }
    setupSize();
    Log.i("curve", "radiusAX " + radiusAX + ", radiusAY " + radiusAY
        + ", radiusBX " + radiusBX + ", radiusBY " + radiusBY
        + ", speedOuterPoint " + speedOuterPoint + ", speedInner " + speedInnerPoint
        + ", loopTotalCount " + loopTotalCount + ", duration " + durationSec);
    angleA = 0;
    angleB = 0;
    float endAngleA = loopTotalCount * (float) Math.PI;
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, endAngleA).setDuration(durationSec * 1000);
    valueAnimator.setEvaluator(new MyTypeEvaluator());
    valueAnimator.setInterpolator(new LinearInterpolator());
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        angleA = (float) animation.getAnimatedValue();
        angleB = (angleA / speedOuterPoint) * speedInnerPoint;
        invalidate();
      }
    });
    valueAnimator.start();
  }

  public void setLineColor(int lineColor) {
    this.lineColor = lineColor;
    setupPaint();
    invalidate();
  }

  public void setLineAlpha(int lineAlpha) {
    this.lineAlpha = lineAlpha;
    setupPaint();
    invalidate();
  }

  class MyTypeEvaluator implements TypeEvaluator<Float> {
    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
      float currentValue = (endValue - startValue) * fraction;
      int count = (int) (currentValue / speedOuterPoint);
      return (float) count * speedOuterPoint;
    }
  }

}
