package com.wingjay.jayandroid.gesture;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;

/**
 * Created by wingjay on 9/15/15.
 */
public class GestureActivity extends BaseActivity{

    private View container;
    private ScrollImageView image;

    @Bind(R.id.move_image)
    Button moveImageBtn;

    ActionBar actionBar;

    private Scroller scroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gesture);
        container = findViewById(R.id.container);
        image = (ScrollImageView) findViewById(R.id.image);

        image.setOnTouchListener(onTouchListener);

        actionBar = getSupportActionBar();

        scroller = new Scroller(this);
        image.setScroller(scroller);
    }

    private int lastX, lastY;
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastX = (int) event.getX();
                    lastY = (int) event.getY();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    int currentX = (int) event.getX();
                    int currentY = (int) event.getY();
                    int offsetX = currentX - lastX;
                    int offsetY = currentY - lastY;

                    int minSlop = ViewConfiguration.get(GestureActivity.this).getScaledEdgeSlop();
                    if (Math.abs(offsetX) < minSlop) {
                        offsetX = 0;
                    }
                    if (Math.abs(offsetY) < minSlop) {
                        offsetY = 0;
                    }

                    moveByScroll(v, offsetX, offsetY);

                    lastX = currentX;
                    lastY = currentY;
                    return true;
                case MotionEvent.ACTION_UP:
                    // move imageView.So we need to do scrollTo on its parentView container.
                    smoothMoveByScroller(container.getScrollX(), container.getScrollY(),
                            -container.getScrollX(), -container.getScrollY());

                    // because before we use scrollBy to move imageView, So actually imageView's
                    // position is still in left top. If we want to get the distance, we need to use
                    // parentView getScrollX, getScrollY
//                    smoothMoveByAnimation(v,
//                            0, 0,
//                            container.getScrollX(), container.getScrollY());
                    return false;
            }
            return false;
        }
    };

    private void moveByLayoutParams(View v, int offsetX, int offsetY) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = v.getLeft() + offsetX;
        layoutParams.topMargin = v.getTop() + offsetY;
        v.setLayoutParams(layoutParams);
    }

    private void moveByScroll(View v, int offsetX, int offsetY) {
        // scrollBy will make the content of caller view move
        // if a imageView call scrollBy fun, it will make its drawable move but the imageView won't
        // so if we hope this imageView move, we need to use its parent view call scrollBy
        // which will move 'all' of its children view.
        // Watch out: the distance need to be negative.
        ((View) v.getParent()).scrollBy(-offsetX, -offsetY);
    }

    private void smoothMoveByScroller(int startX, int startY, int offsetX, int offsetY) {
        // scroller has nothing to do with real view.
        // it acts more like a calculator, and it only calculate the value.
        // In addition, view will call scroller computeScroll fun, and in this function,
        // we need to scroll the view manually, and call invalidate() to redraw.
        scroller.startScroll(startX, startY, offsetX, offsetY, 500);
    }

    private void smoothMoveByAnimation(View target, int startX, int startY, int offsetX, int offsetY) {
        // 1. we can move view by change its translationX, translationY
        // 2. For move smoothly, we can use animator
        Log.i(TAG, startX + ", " + startY + ", " + offsetX + ",  " + offsetY);
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(target, "translationX", startX, offsetX).setDuration(500);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(target, "translationY", startY, offsetY).setDuration(500);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xAnimator, yAnimator);
        animatorSet.start();
    }

}
