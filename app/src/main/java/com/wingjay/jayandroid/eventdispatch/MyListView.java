package com.wingjay.jayandroid.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * This will control actionBar hide/show.
 */
public class MyListView extends ListView {

    private int lastX, lastY;

    private int detectedDistance = 50;

    private OnScrollUpDownListener onScrollUpDownListener;

    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setOnScrollUpDownListener(OnScrollUpDownListener onScrollUpDownListener) {
        this.onScrollUpDownListener = onScrollUpDownListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int currX = (int) ev.getX();
        int currY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(currX - lastX) > Math.abs(currY - lastY)) {
                    break;
                }
                if (Math.abs(currY - lastY) < detectedDistance) {
                    break;
                }
                if (currY > lastY) {
                    if (onScrollUpDownListener != null) {
                        onScrollUpDownListener.onScrollUp();
                    }
                } else if (currY < lastY) {
                    if (onScrollUpDownListener != null) {
                        onScrollUpDownListener.onScrollDown();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        lastX = currX;
        lastY = currY;
        return super.onTouchEvent(ev);
    }

    public interface OnScrollUpDownListener {
        void onScrollDown();
        void onScrollUp();
    }

}
