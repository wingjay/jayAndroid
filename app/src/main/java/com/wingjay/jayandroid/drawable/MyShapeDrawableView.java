package com.wingjay.jayandroid.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wingjay on 10/30/15.
 */
public class MyShapeDrawableView extends View {

    private ShapeDrawable shapeDrawable;

    public MyShapeDrawableView(Context context) {
        this(context, null);
    }

    public MyShapeDrawableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyShapeDrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(0x6674AC23);
        shapeDrawable.setBounds(0, 0, 500, 500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("drawable", "onDraw");
        super.onDraw(canvas);
        shapeDrawable.draw(canvas);
    }

    public void setBrightnessFactor(float factor) {
        // change brightness
        int argb = shapeDrawable.getPaint().getColor();
        float[] hsv = new float[3];
        Color.colorToHSV(argb, hsv);
        Log.i("drawable", "origin hsv : " + hsv[0] + ", " + hsv[1] + ", " + hsv[2]);
        hsv[2] = Math.min(hsv[2] * factor, 1.0f);
        hsv[2] = Math.max(0.01f, hsv[2]);
        Log.i("drawable", "after hsv : " + hsv[0] + ", " + hsv[1] + ", " + hsv[2]);
        shapeDrawable.getPaint().setColor(Color.HSVToColor(hsv));
        invalidate();
    }

    public void setTransparencyFactor(float factor) {
        int argb = shapeDrawable.getPaint().getColor();
        Log.i("drawable", "origin alpha : " + Color.alpha(argb));
        shapeDrawable.getPaint().setColor(Color.argb(
                (int) (Color.alpha(argb) * factor),
                Color.red(argb),
                Color.green(argb),
                Color.blue(argb)
        ));
        Log.i("drawable", "after alpha : " + Color.alpha(argb) * factor);
        invalidate();
    }

}
