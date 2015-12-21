package com.wingjay.jayandroid.curve;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * This View will transform circle to rect based on Bezier Curve.
 */
public class CircleToRectView extends View {

    private int width, height;

    private int defaultWidth = 300, defaultHeight = 300;

    private Paint pathPaint;
    private Path path;
    private static final float MAGIC_NUMBER = 0.55228475f;
    private float magicNumber = 0;

    private ValueAnimator transformAnimator;
    private ObjectAnimator rotateAnimator;
    private AnimatorSet animatorSet;
    public CircleToRectView(Context context) {
        this(context, null);
    }

    public CircleToRectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleToRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //pathPaint.setStyle(Paint.Style.STROKE);
        //pathPaint.setStrokeWidth(3);

        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST) {
            width = defaultWidth;
        }
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            height = defaultHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // move to center
        canvas.translate(width/2, height/2);
        path.reset();
        path.moveTo(0, getRadius());
        // four part bezier curve
        float len = getRadius() * magicNumber;
        path.cubicTo(len, getRadius(), getRadius(), len, getRadius(), 0);
        path.cubicTo(getRadius(), -len, len, -getRadius(), 0, -getRadius());
        path.cubicTo(-len, -getRadius(), -getRadius(), -len, -getRadius(), 0);
        path.cubicTo(-getRadius(), len, -len , getRadius(), 0, getRadius());

        canvas.drawPath(path, pathPaint);

    }

    public void startTransform() {
        if (animatorSet != null && animatorSet.isRunning()) {
            return;
        }
        transformAnimator = ValueAnimator.ofFloat(MAGIC_NUMBER, 0);
        //transformAnimator.setInterpolator(new BounceInterpolator());
        transformAnimator.setRepeatCount(ValueAnimator.INFINITE);
        transformAnimator.setRepeatMode(ValueAnimator.REVERSE);
        transformAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                magicNumber = (float) animation.getAnimatedValue();
                Log.i("circle", "magicNumber " + magicNumber);
                invalidate();
            }
        });

        rotateAnimator = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.setRepeatMode(ValueAnimator.REVERSE);

        animatorSet = new AnimatorSet();
        animatorSet.setTarget(this);
        animatorSet.playTogether(transformAnimator, rotateAnimator);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    private float getRadius() {
        return Math.min(width, height) / 2;
    }

    @Override
    protected void onDetachedFromWindow() {
        animatorSet.cancel();
        super.onDetachedFromWindow();
    }
}
