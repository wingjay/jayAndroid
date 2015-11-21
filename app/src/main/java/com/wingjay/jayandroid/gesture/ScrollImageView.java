package com.wingjay.jayandroid.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Scroller;

/**
 * Created by jay on 11/21/15.
 */
public class ScrollImageView extends ImageView {

    private Scroller scrollerInParent;
    public ScrollImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScroller(Scroller scroller) {
        scrollerInParent = scroller;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // use scroller value to move this ImageView and redraw
        if (scrollerInParent == null) {
            return;
        }
        if (scrollerInParent.computeScrollOffset()) {
            // use its parenteView to call scrollTo fun and thus the content of its parent will move
            ((View) getParent()).scrollTo(scrollerInParent.getCurrX(), scrollerInParent.getCurrY());
        }
        invalidate();
    }
}
