package com.wingjay.jayandroid.perisope;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wingjay.jayandroid.R;

import java.util.Random;

/**
 * Created by jay on 11/4/15.
 */
public class PerisopeView extends FrameLayout {

    private Context context;

    private int width, height;
    private int defaultWidth = 1500, defaultHeight = 1500;

    //for heart icon, which will be bottom center.
    private RelativeLayout.LayoutParams layoutParams;
    private int iconWidth, iconHeight;

    private int heartDrawableCount = 3;
    private Drawable[] heartDrawables = new Drawable[heartDrawableCount];

    private Random random = new Random();
    private float startX, startY;

    private Interpolator line = new LinearInterpolator();//线性
    private Interpolator acc = new AccelerateInterpolator();//加速
    private Interpolator dce = new DecelerateInterpolator();//减速
    private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速
    private Interpolator[] interpolators ;

    public PerisopeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        setBackgroundColor(Color.GRAY);

        // add heart drawables
        heartDrawables[0] = context.getResources().getDrawable(R.drawable.blue_heart);
        heartDrawables[1] = context.getResources().getDrawable(R.drawable.red_heart);
        heartDrawables[2] = context.getResources().getDrawable(R.drawable.yellow_heart);

        iconWidth = heartDrawables[0].getIntrinsicWidth();
        iconHeight = heartDrawables[0].getIntrinsicHeight();

        // init inter polators
        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dce;
        interpolators[3] = accdec;
    }

    public void createHeart() {
        startX = width / 2 - iconWidth/4;
        startY = height - iconHeight/2;

        // create a imageView
        final View view = getRandomHeartDrawableView();
        addView(view);
        // init position
        view.setX(startX);
        view.setY(startY);

        //create reveal animation
        AnimatorSet revealAnimationSet = getRevealAnimationSet(view);

        // create fly animation
        Animator flyAnimationSet = getFlyAnimationSet(view);

        // combine two animation sets
        AnimatorSet finalAnimationSet = new AnimatorSet();
        finalAnimationSet.playSequentially(revealAnimationSet, flyAnimationSet);

        // start animation
        finalAnimationSet.setInterpolator(interpolators[random.nextInt(4)]);
        finalAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(view);
            }
        });
        finalAnimationSet.start();
    }

    private View getRandomHeartDrawableView() {
        ImageView imageView = new ImageView(getContext());
        Drawable heartDrawable = heartDrawables[random.nextInt(heartDrawableCount)];
        imageView.setImageDrawable(heartDrawable);
        LayoutParams layoutParams = new LayoutParams(iconWidth / 2, iconHeight / 2);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    private AnimatorSet getRevealAnimationSet(View view) {
        // alpha and scale
        AnimatorSet revealAnimationSet = new AnimatorSet();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, 0f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0f, 1f);
        revealAnimationSet.play(alphaAnimator).with(scaleXAnimator).with(scaleYAnimator);
        revealAnimationSet.setDuration(500);
        return revealAnimationSet;
    }

    private Animator getFlyAnimationSet(final View view) {
        BezierEvaluator bezierEvaluator =
                new BezierEvaluator(
                        new PointF(random.nextInt(width - 100), random.nextInt(height - 100)),
                        new PointF(random.nextInt(width - 100), random.nextInt(height - 100)));

        PointF startPoint = new PointF(startX, startY);
        PointF endPoint = new PointF(random.nextInt(width), 0);
        Log.i("perisope", "start x " + startX + ", startY " + startY + ", endX " + endPoint.x + ", endY " + endPoint.y);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierEvaluator, startPoint, endPoint);
        valueAnimator.setDuration(5000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF currentPoint = (PointF) animation.getAnimatedValue();
                view.setX(currentPoint.x);
                view.setY(currentPoint.y);
                view.setAlpha(1.0f - animation.getAnimatedFraction());
            }
        });
        return valueAnimator;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getRealWidth(widthMeasureSpec);
        height = getRealHeight(heightMeasureSpec);
        Log.i("perisope", "width " + width + ", height " + height);
        setMeasuredDimension(width, height);
    }

    private int getRealWidth(int widthMeasureSpec) {
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = MeasureSpec.getSize(widthMeasureSpec);

        if (measureMode == MeasureSpec.EXACTLY) {
            return measureSize;
        } else {
            // measureSize is parent view size
            return Math.min(defaultWidth, measureSize);
        }
    }

    private int getRealHeight(int heightMeasureSpec) {
        int measureMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureSize = MeasureSpec.getSize(heightMeasureSpec);

        if (measureMode == MeasureSpec.EXACTLY) {
            return measureSize;
        } else {
            // measureSize is parent view size
            return Math.min(defaultHeight, measureSize);
        }
    }

}
