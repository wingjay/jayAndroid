package com.wingjay.jayandroid.readmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * When text is too long, it will hide and keep only several lines.
 * Then show 'read more' button below. When click, expand all the text and 'read less' below.
 * Click 'read less', Go back.
 */
public class ReadMoreView extends LinearLayout{
    public ReadMoreView(Context context) {
        super(context);
    }

    public ReadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
