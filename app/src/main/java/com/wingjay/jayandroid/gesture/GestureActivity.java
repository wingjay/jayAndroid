package com.wingjay.jayandroid.gesture;

import android.gesture.GestureOverlayView;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

/**
 * Created by wingjay on 9/15/15.
 */
public class GestureActivity extends BaseActivity{

    private View container;
    private ImageView image;

    private float originX;
    private float originY;
    private float originWidth;
    private float originHalfX;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gesture);
        container = findViewById(R.id.container);
        image = (ImageView) findViewById(R.id.image);


        gestureDetector = new GestureDetector(this, onGestureListener);
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    image.setX(originX);
                    image.setY(originY);
                    image.setScaleX(1);
                }
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });

        originX = image.getX();
        originY = image.getY();
        originWidth = image.getWidth();
        originHalfX = getHalfX();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean smaller = true;
    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    Log.i(TAG, "onDown : X: " + e.getX() + ", Y: " + e.getY());
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    Log.i(TAG, "onShowPress");
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.i(TAG, "onSingleTapUp : " + e.getAction());
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    Log.i(TAG, "onScroll : distanceX : " + distanceX + ", distanceY : " + distanceY);

                    float oldX = image.getX();
                    float newX = oldX - distanceX;
                    image.setX(newX);
                    float nowHalfX = e2.getX();
                    Log.i(TAG, "originHalfX : " + originHalfX + ", nowHalfX : " + nowHalfX + ", originWidth : " + originWidth);
                    float newScaleX = 1 - (Math.abs(nowHalfX - originHalfX) / originWidth);

                    Log.i(TAG, "newScaleX :" + newScaleX);

                    image.setPivotX(getHalfX());
                    image.setScaleX(newScaleX);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Log.i(TAG, "onLongPress");

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    Log.i(TAG, "onFling : velocityX : " + velocityX + ", velocityY : " + velocityY);
                    return false;
                }
            };

    private float getHalfX() {
        return image.getX() + image.getWidth() / 2;
    }

}
