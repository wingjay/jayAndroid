package com.wingjay.jayandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wingjay.jayandroid.contentprovider.ContentResolverActivity;
import com.wingjay.jayandroid.curve.CurveActivity;
import com.wingjay.jayandroid.customizeview.CustomizeViewActivity;
import com.wingjay.jayandroid.drag.DragActivity;
import com.wingjay.jayandroid.drag.DragChooseActivity;
import com.wingjay.jayandroid.drawable.DrawableActivity;
import com.wingjay.jayandroid.eventdispatch.EventDispatchActivity;
import com.wingjay.jayandroid.fastblur.FastBlurActivity;
import com.wingjay.jayandroid.fulltextview.FullTextViewActivity;
import com.wingjay.jayandroid.gesture.GestureActivity;
import com.wingjay.jayandroid.perisope.PerisopeActivity;
import com.wingjay.jayandroid.qqitem.DragableActivity;

import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.contentProvider).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContentResolverActivity.class));
            }
        });

        findViewById(R.id.gesture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GestureActivity.class));
            }
        });
    }

    @OnClick(R.id.drag_helper)
    void drag(View view) {
        startActivity(new Intent(MainActivity.this, DragChooseActivity.class));
    }

    @OnClick(R.id.drag_able)
    void dragAble() {
        startActivity(new Intent(MainActivity.this, DragableActivity.class));
    }

    @OnClick(R.id.event_dispatch)
    void eventDispatch() {
        startActivity(new Intent(MainActivity.this, EventDispatchActivity.class));
    }

    @OnClick(R.id.full_text)
    void fullText() {
        startActivity(new Intent(MainActivity.this, FullTextViewActivity.class));
    }

    @OnClick(R.id.drawable)
    void drawable() {
        startActivity(new Intent(MainActivity.this, DrawableActivity.class));
    }

    @OnClick(R.id.customize_view)
    void customizeView() {
        startActivity(new Intent(MainActivity.this, CustomizeViewActivity.class));
    }

    @OnClick(R.id.perisope_view)
    void perisipeView() {
        startActivity(new Intent(MainActivity.this, PerisopeActivity.class));
    }

    @OnClick(R.id.curve_view)
    void curveView() {
        startActivity(new Intent(MainActivity.this, CurveActivity.class));
    }

    @OnClick(R.id.fast_blur)
    void fastBlur() {
        startActivity(new Intent(MainActivity.this, FastBlurActivity.class));
    }


}
