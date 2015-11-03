package com.wingjay.jayandroid.customizeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by wingjay on 11/1/15.
 */
public class ScreenScrollViewGroup extends FrameLayout {

    private final static String TAG = "ScreenScrollViewGroup";
    private Context mContext;

    private Scroller mScroller;

    private int mScreenHeight;
    private int mChildCount;
    private int childViewMinWidth, childViewMinHeight;
    private int mLastY;

    private int mStart, mEnd;
    public ScreenScrollViewGroup(Context context) {
        this(context, null);
    }

    public ScreenScrollViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenScrollViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mScreenHeight = point.y;

        childViewMinWidth = point.x;
        childViewMinHeight = mScreenHeight;
        mScroller = new Scroller(mContext);

        //setOrientation(VERTICAL);

        View firstView = new View(mContext);
        firstView.setBackgroundColor(Color.RED);

        View secondView = new View(mContext);
        secondView.setBackgroundColor(Color.GREEN);

        View thirdView = new View(mContext);
        thirdView.setBackgroundColor(Color.BLUE);

        addView(firstView);
        addView(secondView);
        addView(thirdView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure width : " + MeasureSpec.getSize(widthMeasureSpec) + " mode :" +
                MeasureSpec.getMode(widthMeasureSpec) +
                ", height : " + MeasureSpec.getSize(heightMeasureSpec) + " mode :" +
                MeasureSpec.getMode(heightMeasureSpec));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildCount = getChildCount();
        for (int i=0; i< mChildCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            Log.i(TAG, "omMeasure child view width " + childView.getWidth() + ", height " + childView.getHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.i(TAG, "onLayout left : " + left
                + ", top : " + top
                + ", right : " + right
                + ", bottom : " + bottom);
        //set height
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = mScreenHeight * getChildCount();
        setLayoutParams(marginLayoutParams);
        for (int i=0; i< mChildCount; i++) {
            View childView = getChildAt(i);
            Log.i(TAG, i + " childView width " + childView.getWidth() + ", height " + childView.getHeight());
            if (childView.getVisibility() != GONE) {
                childView.layout(left, top + i * mScreenHeight,
                        right ,top + (i + 1) * mScreenHeight);
                Log.i(TAG, i + " childView left " + getLeft()
                        + " top " + (top + i * mScreenHeight)
                        + " right " + right
                        + " bottom " + (top + (i + 1) * mScreenHeight));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) event.getY();
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                Log.i(TAG, " action move ScrollY : " + getScaleY());
                if (getScrollY() < 0) {
                    dy = 0; // already in the top and nothing above
                }
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;// already in the bottom and nothing below
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY(); // the real y in the top.
                int dScrollY = mEnd - mStart;
                Log.i(TAG, "mStart : " + mStart + ", mEnd : " + mEnd + " dScrollY " + dScrollY + ", screenHeight " + mScreenHeight);
                if (dScrollY > 0) {
                    //push up
                    if (dScrollY < mScreenHeight / 3) {
                        // startScroll doesn't mean real scrolling, it just set the parameters and waiting for scrolling.
                        mScroller.startScroll(0, mEnd, 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, mEnd, 0, mScreenHeight - dScrollY);
                    }
                } else {
                    // pull down
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, mEnd, 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, mEnd, 0, -mScreenHeight - dScrollY);
                    }
                }
                break;

        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        Log.i(TAG, "computeScroll  currY " + mScroller.getCurrY() + ", currX " + mScroller.getCurrX());
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
