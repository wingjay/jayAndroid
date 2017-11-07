package com.wingjay.jayandroid.tracker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * TrackerView
 *
 * @author wingjay
 * @date 2017/10/26
 */
public class TrackerView extends View {

    public TrackerView(Context context) {
        this(context, null);
    }

    public TrackerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        TrackerLog.e("onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        TrackerLog.e("onDetachedFromWindow");
    }
}
