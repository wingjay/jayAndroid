package com.wingjay.jayandroid.curve;

import android.os.Bundle;
import android.widget.Button;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jay on 11/5/15.
 */
public class CurveActivity extends BaseActivity {

    @Bind(R.id.change_curve)
    Button changeCurve;

    @Bind(R.id.curve_view)
    CurveView curveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve);
    }

    @OnClick(R.id.change_curve)
    void changeCurve() {
        curveView.startDrawCurve();
    }

    @Override
    protected void onStop() {
        super.onStop();
        curveView.stopAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
