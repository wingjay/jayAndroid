package com.wingjay.jayandroid.drawable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.wingjay.jayandroid.R;

/**
 * Created by wingjay on 10/30/15.
 */
public class MyLayerDrawableView extends View {

    public MyLayerDrawableView(Context context) {
        this(context, null);
    }

    public MyLayerDrawableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLayerDrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ShapeDrawable bottomLayer = new ShapeDrawable(new RectShape());
        bottomLayer.getPaint().setColor(Color.BLUE);

        ShapeDrawable midLayer = new ShapeDrawable(new RectShape());
        midLayer.getPaint().setColor(Color.GREEN);
        midLayer.getPaint().setStyle(Paint.Style.STROKE);//空心,only display the border line and transparent content

        Drawable[] layer = new Drawable[] {
                bottomLayer,
                midLayer,
                getContext().getResources().getDrawable(R.drawable.ic_fab_star)
        };
        LayerDrawable layerDrawable = new LayerDrawable(layer);
        // left, top, right, bottom. the distance with outer border of whole layout.
        layerDrawable.setLayerInset(0, 10, 10, 10, 10);
        layerDrawable.setLayerInset(1, 20, 20, 20, 20);

        setBackgroundByVersion(layerDrawable);
    }

    @SuppressWarnings("deprecation")
    private void setBackgroundByVersion(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

}
