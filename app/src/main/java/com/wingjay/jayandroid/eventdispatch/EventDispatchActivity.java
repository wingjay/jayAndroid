package com.wingjay.jayandroid.eventdispatch;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wingjay on 10/26/15.
 */
public class EventDispatchActivity extends BaseActivity {

    @Bind(R.id.border_1)
    View border1;

    @Bind(R.id.border_2)
    View border2;

    @Bind(R.id.border_3)
    View border3;

    @Bind(R.id.border_4)
    View border4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_dispatch);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBgColor(v);
            }
        };
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String viewName = "unknown";
                if (v == border1) {
                    viewName = "border1";
                } else if (v == border2) {
                    viewName = "border2";
                } else if (v == border3) {
                    viewName = "border3";
                } else if (v == border4) {
                    viewName = "border4";
                }
                Log.i("eventDispatch", "viewName : " + viewName + ", action : " + event.getAction());
                return false;
            }
        };
        border1.setOnClickListener(listener);
        border2.setOnClickListener(listener);
        border3.setOnClickListener(listener);
        border4.setOnClickListener(listener);
        border1.setOnTouchListener(onTouchListener);
        border2.setOnTouchListener(onTouchListener);
        border3.setOnTouchListener(onTouchListener);
        border4.setOnTouchListener(onTouchListener);
    }

    private void setBgColor(View view) {
        border1.setBackgroundColor((view == border1) ? Color.RED : Color.parseColor("#000000"));
        border2.setBackgroundColor((view == border2) ? Color.RED : Color.parseColor("#333333"));
        border3.setBackgroundColor((view == border3) ? Color.RED : Color.parseColor("#777777"));
        border4.setBackgroundColor((view == border4) ? Color.RED : Color.parseColor("#aaaaaa"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("eventDispatch", "EventDispatchActivity, action : " + event.getAction());
        return super.onTouchEvent(event);
    }

}
