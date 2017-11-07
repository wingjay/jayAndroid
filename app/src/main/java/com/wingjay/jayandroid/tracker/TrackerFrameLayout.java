package com.wingjay.jayandroid.tracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * TrackerFrameLayout
 *
 * @author wingjay
 * @date 2017/10/26
 */
public class TrackerFrameLayout extends FrameLayout implements GestureDetector.OnGestureListener {
    private GestureDetector mGestureDetector;

    public TrackerFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public TrackerFrameLayout(@NonNull Context context,
                              @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mGestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        TrackerLog.e("dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        TrackerLog.e(String.format("onLayout, changed: %s, left: %s, top: %s, right: %s, bottom : %s",
            changed, left, top, right, bottom));
    }

    /**
     * Scene 3: switch back and forth when press Home button.
     * Scene 4: enter into the next page
     * Scene 5: window replace
     *
     * Called when the window containing this view gains or loses window focus.
     * ViewGroups should override to route to their children.
     *
     * @param hasFocus True if the window containing this view now has focus,
     *        false otherwise.
     */
    @Override
    public void dispatchWindowFocusChanged(boolean hasFocus) {
        super.dispatchWindowFocusChanged(hasFocus);
        TrackerLog.e("dispatchWindowFocusChanged, hasFocus: " + hasFocus);
    }

    /**
     * Scene 6: switch page in the TabActivity
     *
     * Dispatch a view visibility change down the view hierarchy.
     * ViewGroups should override to route to their children.
     * @param changedView The view whose visibility changed. Could be 'this' or
     * an ancestor view.
     * @param visibility The new visibility of changedView: {@link #VISIBLE},
     * {@link #INVISIBLE} or {@link #GONE}.
     */
    @Override
    protected void dispatchVisibilityChanged(View changedView, int visibility) {
        TrackerLog.e(String.format("dispatchVisibilityChanged. changedView: %s, visibility: %s", changedView.getClass().getSimpleName(), visibility));
        super.dispatchVisibilityChanged(changedView, visibility);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Gesture Callback
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onDown(MotionEvent e) {
        TrackerLog.e("onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        TrackerLog.e("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        TrackerLog.e("onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        TrackerLog.e("onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        TrackerLog.e("onLongPress");
    }

    /**
     * Scene 2: Scroll ending
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        TrackerLog.e("onFling");
        return false;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Gesture Callback
    ///////////////////////////////////////////////////////////////////////////
}
