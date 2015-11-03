package com.wingjay.jayandroid.customizeview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wingjay on 11/1/15.
 */
public class ProportionView extends View {

    private final static String TAG = "ProportionView";

    private int proportion;// 0 - 100
    private final static int DEFAULT_PROPORTION = 50;
    private String proprotionString;
    private int backgroundColor = Color.TRANSPARENT;

    private int animationLoadingDuration = 1000;

    private Paint textPaint;
    private int textColor = Color.WHITE;
    private Rect textSizeRect; // get current text width/height
    private int textSize = 25;

    private Paint circlePaint;
    private int circleColor = getContext().getResources().getColor(android.R.color.holo_blue_light);
    private int width = 400, height = 400;
    private int circleX, circleY, radius;

    private int distance = 50;

    private int proportionColor = Color.BLACK;
    private Paint outArcPaint;
    private RectF outArcRect;
    private float startAngle = 0, sweepAngle;

    public ProportionView(Context context) {
        super(context);
    }

    public ProportionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProportionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        setValue();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        setValue();
    }

    private void setValue() {
        circleX = width / 2;
        circleY = height / 2;
        radius = width / 5;
    }

    private void init() {
        setBackgroundColor(backgroundColor);
        setProportion(DEFAULT_PROPORTION);

        setPaint();
    }

    private void setPaint() {
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        textSizeRect = new Rect();

        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        outArcRect = new RectF(distance, distance, width - distance, height - distance);
        outArcPaint = new Paint();
        outArcPaint.setColor(proportionColor);
        outArcPaint.setStyle(Paint.Style.STROKE);
        outArcPaint.setStrokeWidth(distance);
        outArcPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(outArcRect, startAngle, sweepAngle, false, outArcPaint);

        canvas.drawCircle(circleX, circleY, radius, circlePaint);

        textPaint.getTextBounds(proprotionString, 0, proprotionString.length(), textSizeRect);
        float textWidth = textPaint.measureText(proprotionString);
        float textHeight = textSizeRect.height();
        canvas.drawText(proprotionString, circleX - textWidth / 2, circleY + textHeight / 2, textPaint);

        super.onDraw(canvas);
    }

    public void setProportion(int proportion) {
        checkProportion(proportion);
        this.proportion = proportion;
        this.proprotionString = String.valueOf(proportion) + "%";
        this.sweepAngle = proportion * 3.6f;
        invalidate();
    }

    private void checkProportion(int proportion) {
        if (proportion < 0 || proportion > 100) {
            throw new IllegalArgumentException("The proportion value must between 0 and 100");
        }
    }

    public void setProportionWithAnimation(int proportion) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, proportion);
        valueAnimator.setDuration(animationLoadingDuration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setProportion((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    public void setAnimationLoadingDuration(int animationLoadingDuration) {
        if (animationLoadingDuration < 0) {
            throw new IllegalArgumentException("animation duration mustn't less than 0");
        }
        this.animationLoadingDuration = animationLoadingDuration;
    }

    private int measureWidth(int widthMeasureSpec) {
        int resultWidth = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            resultWidth = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            int setSize = width;
            resultWidth = Math.min(setSize, specSize);
        }
        return resultWidth;
    }

    private int measureHeight(int heightMeasureSpec) {
        int resultHeight = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            resultHeight = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            int setSize = height;
            resultHeight = Math.min(setSize, specSize);
        }
        return resultHeight;
    }

}
