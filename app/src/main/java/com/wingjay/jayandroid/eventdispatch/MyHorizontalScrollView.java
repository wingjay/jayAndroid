package com.wingjay.jayandroid.eventdispatch;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.wingjay.jayandroid.util.DisplayUtil;

/**
 * Created by jay on 11/21/15.
 */
public class MyHorizontalScrollView extends LinearLayout {

    private static final String TAG = "MyHorizontalScrollView";
    private Context context;

    private int lastX, lastY;

    private int minSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private ValueAnimator valueAnimator;

    private Scroller scroller;

    private int screenWidth = DisplayUtil.getScreenWidth(getContext());

    public MyHorizontalScrollView(Context context) {
        this(context, null);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        scroller = new Scroller(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int currX = (int) ev.getX();
        int currY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if ((Math.abs(currX - lastX) > Math.abs(currY - lastY))) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        lastX = currX;
        lastY = currY;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currX = (int) event.getX();
        int currY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = 0;
                if ((Math.abs(currX - lastX) >= minSlop)) {
                    offsetX = currX - lastX;
                }
                // at the very left
                if (getScrollX() < 0) {
                    offsetX = 0;
                }
                if (getScrollX() > (getRealWidth() - screenWidth)) {
                    offsetX = 0;
                }
                moveHorizontallyByScroller(offsetX);
                break;
            case MotionEvent.ACTION_UP:
                int index = getScrollX() / screenWidth;
                if (getScrollX() > (2 * index + 1) * screenWidth / 2) {
                    if (index < getChildCount() - 1) {
                        index++;
                    }
                }
                smoothScrollToChildAt(index);
                break;
        }
        lastX = currX;
        lastY = currY;
        return true;
    }

    private void moveHorizontallyByScroller(int offsetX) {
        if (!checkBoundByScroller(offsetX)) {
            return;
        }
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
        }
        // scroll immediately
        scrollBy(-offsetX, 0);
    }

    private void smoothScrollToChildAt(int index) {
        if (index < 0 || index > (getChildCount() - 1)) {
            return;
        }
        if (!scroller.isFinished()) {
            scroller.abortAnimation();
        }
        scroller.startScroll(getScrollX(), 0, (index * screenWidth - getScrollX()), 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            invalidate();
        }
    }

    private void moveHorizontally(final int offsetX) {
        if (!checkBoundByParams(offsetX)) {
            return;
        }
        final MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
        final int maxLeftMargin = 0, minLeftMargin = - (getRealWidth() - DisplayUtil.getScreenWidth(getContext()));
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        valueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(250);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                layoutParams.leftMargin += fraction * offsetX;
                if (layoutParams.leftMargin > maxLeftMargin) {
                    layoutParams.leftMargin = 0;
                    animation.cancel();
                }
                if (layoutParams.leftMargin < minLeftMargin) {
                    layoutParams.leftMargin = minLeftMargin;
                    animation.cancel();
                }
                setLayoutParams(layoutParams);
            }
        });
        valueAnimator.start();
    }

    private boolean checkBoundByParams(int offsetX) {
        final int maxLeftMargin = 0, minLeftMargin = - (getRealWidth() - screenWidth);
        final MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
        if (layoutParams.leftMargin >= maxLeftMargin && offsetX > 0) {
            return false;
        }
        if (layoutParams.leftMargin <= minLeftMargin && offsetX < 0) {
            return false;
        }
        return true;
    }

    private boolean checkBoundByScroller(int offsetX) {
        final int minScrollX = 0, maxScrollY = (getRealWidth() - screenWidth);

        if (getScrollX() <= minScrollX && offsetX > 0) {
            // at the very left
            return false;
        }
        if (getScrollX() >= maxScrollY && offsetX < 0) {
            // at the very right
            return false;
        }
        return true;
    }

    private int getRealWidth() {
        int childViewCount = getChildCount();
        int width = 0;
        for (int i=0; i<childViewCount; i++) {
            width += getChildAt(i).getWidth();
        }
        return width;
    }

}
