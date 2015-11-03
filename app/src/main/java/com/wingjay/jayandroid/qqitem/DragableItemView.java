package com.wingjay.jayandroid.qqitem;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wingjay.jayandroid.R;

/**
 * Created by wingjay on 10/26/15.
 */
public class DragableItemView extends FrameLayout {

    private View hiddenPart, shownPart;

    private int mScreenWidth;

    private int hiddenPartWidth;

    private LinearLayout.LayoutParams mLayoutParams;
    private int mDownX;
    private int mDownY;

    private boolean isHidden = true;

    private double sensitivity = 1.0;

    public DragableItemView(Context context) {
        this(context, null);
    }

    public DragableItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragableItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_dragable, this);
        shownPart = findViewById(R.id.dragable_content);
        hiddenPart = findViewById(R.id.dragable_hidden);
        // get screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth =  displayMetrics.widthPixels;

        hiddenPart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("dragable", "DragableItemView onInterceptTouchEvent and action : " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("dragable", "DragableItemView onTouchEvent and action : " + event.getAction());
        //return gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                // get shownPart LayoutParams
                mLayoutParams = (LinearLayout.LayoutParams) shownPart.getLayoutParams();
                mLayoutParams.width = mScreenWidth;
                shownPart.setLayoutParams(mLayoutParams);
                // get half hidden part width for recovery
                hiddenPartWidth = hiddenPart.getWidth();
                Log.i("dragable", "hiddenPartWidth : " + hiddenPartWidth);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int nowX = (int) event.getX();
                int nowY = (int) event.getY();
                if (Math.abs(nowX - mDownX) <= Math.abs(nowY - mDownY)) {
                    return false;
                }
                Log.i("dragable", "Move nowX : " + nowX + ", DownX : " + mDownX + ", leftMargin : " + mLayoutParams.leftMargin);
                if ((isHidden && nowX >= mDownX) || (!isHidden && nowX <= mDownX)) {
                    return true;
                }
                int newleftMargin;
                if (isHidden) {
                    newleftMargin = nowX - mDownX;
                    newleftMargin = (newleftMargin < -hiddenPartWidth) ? -hiddenPartWidth : newleftMargin;
                } else {
                    newleftMargin = -hiddenPartWidth + nowX - mDownX;
                    newleftMargin = (newleftMargin > 0) ? 0 : newleftMargin;
                }
                Log.i("dragable", "newleftMargin : " + newleftMargin);
                mLayoutParams.leftMargin = newleftMargin;
                shownPart.setLayoutParams(mLayoutParams);
                return true;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                // recover item
                int leftMargin = mLayoutParams.leftMargin;
                Log.i("dragable", "UP leftMargin : " + leftMargin + ", ishidden : " + isHidden);
                if (isHidden) {
                    if (-leftMargin >= hiddenPartWidth * (1 - sensitivity)) {
                        show();
                    } else {
                        hide();
                    }
                } else {
                    if (-leftMargin <= hiddenPartWidth * sensitivity) {
                        hide();
                    } else {
                        show();
                    }
                }
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void animateChangeHiddenPart(int newLeftMargin) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) shownPart.getLayoutParams();
        int nowLeftMargin = layoutParams.leftMargin;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(nowLeftMargin, newLeftMargin);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLayoutParams.leftMargin = (Integer) animation.getAnimatedValue();
                shownPart.setLayoutParams(mLayoutParams);
            }
        });
        valueAnimator.start();
    }

    public void show() {
        Log.i("dragable", "show");
        animateChangeHiddenPart(-hiddenPartWidth);
        isHidden = false;
    }

    public void hide() {
        Log.i("dragable", "hide");
        animateChangeHiddenPart(0);
        isHidden = true;
    }

    public void setSensitivity(double sensitivity) {
        if (sensitivity < 0.0) {
            this.sensitivity = 0.0;
        } else if (sensitivity > 1.0) {
            this.sensitivity = 1.0;
        } else {
            this.sensitivity = sensitivity;
        }
    }
}
