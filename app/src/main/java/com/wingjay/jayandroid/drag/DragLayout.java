package com.wingjay.jayandroid.drag;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.wingjay.jayandroid.R;

/**
 * Created by wingjay on 10/25/15.
 */
public class DragLayout extends LinearLayout {

    private final ViewDragHelper mDragHelper;

    private View mDragView1;
    private View mDragView2;

    private boolean mDragEdge;
    private boolean mDragHorizontal;
    private boolean mDragCapture;
    private boolean mDragVertical;
    private boolean mItem;

    private View hiddenPart, shownPart;

    private int mScreenWidth;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
    }

    @Override
    protected void onFinishInflate() {
        mDragView1 = findViewById(R.id.drag1);
        mDragView2 = findViewById(R.id.drag2);
        hiddenPart = findViewById(R.id.dragable_hidden);
        shownPart = findViewById(R.id.dragable_content);
        super.onFinishInflate();
    }

    public void setDragHorizontal(boolean dragHorizontal) {
        mDragHorizontal = dragHorizontal;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragVertical(boolean dragVertical) {
        mDragVertical = dragVertical;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragEdge(boolean dragEdge) {
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mDragEdge = dragEdge;
        mDragView2.setVisibility(View.GONE);
    }

    public void setItemHorizental(boolean item) {
        mItem = item;
    }

    public void setDragCapture(boolean dragCapture) {
        mDragCapture = dragCapture;
    }

    
    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.i("drag", "tryCaptureView + pointerId " + pointerId);
            if (mDragCapture) {
                return child == mDragView1;
            }
            return true;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            // left comes from clampViewPositionHorizontal newLeft
            Log.i("drag", "onViewPositionChanged left" + left + ", top : " + top + ", dx : " + dx + ", dy : " + dy);
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            Log.i("drag", "onViewCaptured activePointerId " + activePointerId);
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.i("drag", "onViewReleased xvel " + xvel + ", yvel " + yvel);
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            Log.i("drag", "onViewReleased edgeFlags " + edgeFlags + ", pointerId  " + pointerId);
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            Log.i("drag", "onEdgeDragStarted edgeFlags " + edgeFlags + ", pointerId  " + pointerId);
            if (mDragEdge) {
                mDragHelper.captureChildView(mDragView1, pointerId);
            }
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.i("drag", "clampViewPositionVertical top " + top + ", dy  " + dy);
            if (mDragVertical) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - mDragView1.getHeight();

                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                Log.i("drag", "clampViewPositionVertical : topBound " + topBound + ", bottomBound " + bottomBound + ", newTop : " + newTop);
                return newTop;
            }
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.i("drag", "clampViewPositionHorizontal left " + left + ", dx  " + dx);
            if (mDragHorizontal || mDragCapture || mDragEdge) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView1.getWidth() - getPaddingRight();

                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                Log.i("drag", "clampViewPositionHorizontal : leftBound " + leftBound + ", rightBound " + rightBound + ", newLeft : " + newLeft);
                return newLeft;
            }
            if (mItem) {
                return left;
            }
            return super.clampViewPositionHorizontal(child, left, dx);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("drag", "onInterceptTouchEvent and action :" + ev.getAction());
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }
//        if (mItem && ev.getAction() == MotionEvent.ACTION_DOWN) {
//            // set width of shownPart as screen width
//            ViewGroup.LayoutParams layoutParams = shownPart.getLayoutParams();
//            layoutParams.width = mScreenWidth;
//            shownPart.setLayoutParams(layoutParams);
//        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("drag", "onTouchEvent and action :" + ev.getAction());
        mDragHelper.processTouchEvent(ev);
        return true;
    }

}

