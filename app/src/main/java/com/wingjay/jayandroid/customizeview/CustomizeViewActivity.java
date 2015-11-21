package com.wingjay.jayandroid.customizeview;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by wingjay on 11/1/15.
 */
public class CustomizeViewActivity extends BaseActivity {

    int currentBgColorIndex = 0;
    int[] bgColor = {Color.LTGRAY, Color.TRANSPARENT, Color.RED};

    @Bind(R.id.proportion_view)
    ProportionView proportionView;

    @Bind(R.id.proportion_value)
    EditText proportionValueEditText;

    @Bind(R.id.animate_duration)
    EditText animateDurationEditText;

    @Bind(R.id.my_view_group)
    ViewGroup myViewGroup;

    private String animateDurationString;
    private String proportionValueString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_view);
    }

    @OnClick(R.id.update_proportion)
    void animate() {
        animateDurationString = animateDurationEditText.getText().toString();
        proportionValueString = proportionValueEditText.getText().toString();
        if (!TextUtils.isEmpty(animateDurationString)) {
            proportionView.setAnimationLoadingDuration(Integer.parseInt(animateDurationString));
        }
        if (!TextUtils.isEmpty(proportionValueString)) {
            proportionView.setProportionWithAnimation(Integer.parseInt(proportionValueString));
        } else {
            proportionView.setProportionWithAnimation(0);
        }
        if (currentBgColorIndex > 2) {
            currentBgColorIndex=0;
        }
        proportionView.setBackgroundColor(bgColor[currentBgColorIndex++]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animate();
    }
}
