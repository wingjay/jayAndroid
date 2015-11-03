package com.wingjay.jayandroid.drawable;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wingjay.jayandroid.BaseActivity;

/**
 * Created by wingjay on 10/30/15.
 */
public class DrawableActivity extends BaseActivity {

    private MyShapeDrawableView myShapeDrawableView;
    private MyLayerDrawableView myLayerDrawableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        FrameLayout opacityBg = new FrameLayout(this);
        opacityBg.setLayoutParams(new FrameLayout.LayoutParams(500, 500));
        TextView bgTextView = new TextView(this);
        bgTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bgTextView.setText("lalalalalalala test opacity");
        bgTextView.setGravity(Gravity.CENTER);

        myShapeDrawableView = new MyShapeDrawableView(this);
        myLayerDrawableView = new MyLayerDrawableView(this);

        opacityBg.addView(bgTextView);
        //opacityBg.addView(myShapeDrawableView);
        opacityBg.addView(myLayerDrawableView);

        final EditText editText = new EditText(this);
        editText.setHint("enter value");

        Button brightnessBtn = new Button(this);
        brightnessBtn.setText("change brightness");
        brightnessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myShapeDrawableView.setBrightnessFactor(Float.parseFloat(editText.getText().toString()));
            }
        });

        Button transparencyBtn = new Button(this);
        transparencyBtn.setText("change transparency");
        transparencyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myShapeDrawableView.setTransparencyFactor(Float.parseFloat(editText.getText().toString()));
            }
        });

        linearLayout.addView(editText);
        linearLayout.addView(brightnessBtn);
        linearLayout.addView(transparencyBtn);
        linearLayout.addView(opacityBg);

        scrollView.addView(linearLayout);
        setContentView(scrollView);

    }

}
