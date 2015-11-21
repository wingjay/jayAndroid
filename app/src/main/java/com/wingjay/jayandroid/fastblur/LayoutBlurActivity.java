package com.wingjay.jayandroid.fastblur;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jay on 11/19/15.
 */
public class LayoutBlurActivity extends BaseActivity{

    private final static int BLUR_METHOD_RENDSCRIPT = 1;
    private final static int BLUR_METHOD_STACK_BLUR = 0;
    private static int BLUR_METHOD = BLUR_METHOD_STACK_BLUR;

    @Bind(R.id.blur_layout)
    ViewGroup blurLayout;

    @Bind(R.id.do_blur_layout)
    Button doBlurLayoutBtn;

    @Bind(R.id.change_blur_method)
    Button changeBlurMethodBtn;

    @Bind(R.id.enter_blur_factor)
    EditText blurFactorEditText;

    private ImageView blurImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_layout);

        doBlurLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBlurImageView();
            }
        });

        changeBlurMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMethod();
                Toast.makeText(LayoutBlurActivity.this, "current method " + BLUR_METHOD , Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addBlurImageView() {
        Bitmap layoutBitmap = getViewBitmap(blurLayout);
        Bitmap blurLayoutBm;
        if (getBlurMethod() == BLUR_METHOD_RENDSCRIPT) {
            Log.i("blur", "start renderscript");
            long start = System.currentTimeMillis();
            blurLayoutBm = RenderScriptBlur.apply(this, layoutBitmap, 25);
            Log.i("blur", "end renderscript time consuming " + (System.currentTimeMillis() - start));
        } else {
            Log.i("blur", "start stack blur");
            long start = System.currentTimeMillis();
            blurLayoutBm = FastBlurUtil.doBlur(layoutBitmap, 25, true);
            Log.i("blur", "end stack blur time consuming " + (System.currentTimeMillis() - start));
        }
        blurImageView = new ImageView(this);
        blurImageView.setImageBitmap(blurLayoutBm);
        blurImageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        blurImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBlurOverlay();
            }
        });
        blurLayout.addView(blurImageView);
    }

    public int getBlurMethod() {
        return BLUR_METHOD;
    }

    void toggleMethod() {
        BLUR_METHOD = (BLUR_METHOD == BLUR_METHOD_RENDSCRIPT) ? BLUR_METHOD_STACK_BLUR : BLUR_METHOD_RENDSCRIPT;
    }

    private void removeBlurOverlay() {
        blurLayout.removeView(blurImageView);
    }

    public static Bitmap getViewBitmap(View v) {
        if(v.getWidth() == 0 || v.getHeight() == 0)
            return null;
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public static Bitmap getTransparentBitmap(View parent) {

        //create a bitmap
        if (parent.getWidth() == 0 || parent.getHeight() == 0) {
            return null;
        }
        int width = parent.getWidth();
        int height = parent.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
        shapeDrawable.setAlpha(180);
        shapeDrawable.getPaint().setColor(0x66000000);
        shapeDrawable.setBounds(0, 0, width, height);
        shapeDrawable.draw(canvas);
        return bitmap;
    }

}
