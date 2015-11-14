package com.wingjay.jayandroid.curve;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * This will draw kinds of math curve.
 */
public class CurveView extends View {

    private int defaultWidth = 1000, defaultHeight = 1000;
    private int width, height;

    private PointF startPoint;

    private PointF currentPoint;

    // used to paint point
    private Paint pointPaint;

    private ValueAnimator valueAnimator;

    public CurveView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        // init curve paint
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.GREEN);
        pointPaint.setStrokeWidth(20);
    }

    public void startDrawCurve() {
        startPoint = new PointF(50, height / 2);
        currentPoint = new PointF(50, height / 2);
        valueAnimator = ValueAnimator.ofFloat(0, 60);
        valueAnimator.setDuration(30000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint.x = startPoint.x + (Float) animation.getAnimatedValue();
                Double y = (double) (Float) animation.getAnimatedValue();
                currentPoint.y = startPoint.y + (float) (200 * Math.sin(5 * (Float) animation.getAnimatedValue()));
                Log.i("curve", "animation x " + (Float) animation.getAnimatedValue() + ", y " + (float) Math.sin(y));
                Log.i("curve", "current x " + currentPoint.x + ", y " + currentPoint.y);
                postInvalidate();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("curve", "onDraw");
        super.onDraw(canvas);
        if (currentPoint == null) {
            return;
        }
        Log.i("curve", "draw point");
        canvas.drawColor(Color.BLUE);
        canvas.drawPoint(currentPoint.x, currentPoint.y, pointPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getRealWidth(widthMeasureSpec);
        height = getRealHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        Log.i("curve", "whole width " + width + ", height " + height);
    }

    private int getRealWidth(int widthMeasureSpec) {
        int measureMode = MeasureSpec.getSize(widthMeasureSpec);
        int measureSize = MeasureSpec.getMode(widthMeasureSpec);

        if (measureMode == MeasureSpec.EXACTLY) {
            return measureSize;
        } else {
            return defaultWidth;
        }
    }

    private int getRealHeight(int heightMeasureSpec) {
        int measureMode = MeasureSpec.getSize(heightMeasureSpec);
        int measureSize = MeasureSpec.getMode(heightMeasureSpec);

        if (measureMode == MeasureSpec.EXACTLY) {
            return measureSize;
        } else {
            return defaultHeight;
        }
    }

    public void stopAnimation() {
        valueAnimator.end();
    }

}
