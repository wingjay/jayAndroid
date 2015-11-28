package com.wingjay.jayandroid.fastblur;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jay on 11/7/15.
 */
public class FastBlurActivity extends BaseActivity {

    @Bind(R.id.blur_factor)
    EditText blurFactorEditText;

    @Bind(R.id.fast_blur_btn)
    Button fastBlurBtn;

    @Bind(R.id.fast_blur_image)
    ImageView fastBlurImageView;

    @Bind(R.id.blur_image_view)
    BlurImageView blurImageView;

    @Bind(R.id.full_blur_image_view)
    BlurImageView fullBlurImageView;

    boolean alreadyCompelete = false;

    int[] mediumSmRes = {
            R.drawable.medium_sm_1,
            R.drawable.medium_sm_2,
            R.drawable.medium_sm_3,
            R.drawable.medium_sm_4
    };

    int[] mediumNmRes = {
            R.drawable.medium_nm_1,
            R.drawable.medium_nm_2,
            R.drawable.medium_nm_3,
            R.drawable.medium_nm_4
    };

    String[] mediumSmUrl = {
            "https://cdn-images-1.medium.com/freeze/max/60/1*cDmQ2XlMGowTZNIf4oOHjw.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/60/1*hBp9i_LZHGwKfq7plvjWxQ.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/30/1*yd_YDN3dVyrSp_o7YHOKPg.jpeg?q=20",
            "https://cdn-images-1.medium.com/freeze/max/60/1*hMQ9_nBW3LOHCk3rQSRSbw.jpeg?q=20"
    };

    String[] mediumNmUrl = {
            "https://cdn-images-1.medium.com/max/1600/1*cDmQ2XlMGowTZNIf4oOHjw.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*hBp9i_LZHGwKfq7plvjWxQ.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*yd_YDN3dVyrSp_o7YHOKPg.jpeg",
            "https://cdn-images-1.medium.com/max/2000/1*hMQ9_nBW3LOHCk3rQSRSbw.jpeg"
    };

    int currentIndex = 0;
    int getResIndex() {
        if (currentIndex > 3) {
            currentIndex = currentIndex - 4;
        }
        return currentIndex;
    }

    private Target blurTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            startBlur(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fast_blur);

        // Go to layoutBlurActivity
        //startActivity(new Intent(this, LayoutBlurActivity.class));
    }

    @OnClick(R.id.fast_blur_btn)
    void doFastBlur() {
        if (!alreadyCompelete) {
            int blurFactor = getBlurFactor();
            fullBlurImageView.setBlurFactor(blurFactor);
            fullBlurImageView.setFullImageByUrl(mediumSmUrl[getResIndex()], mediumNmUrl[getResIndex()]);

            blurImageView.setBlurFactor(blurFactor);
            blurImageView.setBlurImageByUrl(mediumSmUrl[getResIndex()]);
            //Picasso.with(this).load(mediumSmUrl[getResIndex()]).into(blurTarget);
            alreadyCompelete = true;
        } else {
            //fastBlurImageView.setImageBitmap(null);
            blurImageView.clear();
            fullBlurImageView.clear();
            currentIndex++;
            alreadyCompelete = false;
        }
    }

    void startBlur(Bitmap needBlurBitmap) {
        fastBlurImageView.setImageBitmap(getBlurBitmap(needBlurBitmap));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadOriginImage();
            }
        }, 1000);
    }

    Bitmap getBlurBitmap(Bitmap loadedBitmap) {
        // make this bitmap mutable
        loadedBitmap = loadedBitmap.copy(loadedBitmap.getConfig(), true);
        return FastBlurUtil.doBlur(loadedBitmap, getBlurFactor(), true);
    }

    void loadOriginImage() {
        Picasso.with(FastBlurActivity.this).load(mediumNmUrl[getResIndex()]).placeholder(fastBlurImageView.getDrawable()).into(fastBlurImageView);
        currentIndex++;
        alreadyCompelete = true;
    }

    int getBlurFactor() {
        if (TextUtils.isEmpty(blurFactorEditText.getText())) {
            return 8;
        }
        return Integer.parseInt(blurFactorEditText.getText().toString());
    }

    @Override
    protected void onDestroy() {
        //Picasso.with(this).cancelRequest(blurTarget);
        blurImageView.cancelImageLoadForSafty();
        fullBlurImageView.cancelImageLoadForSafty();
        super.onDestroy();
    }
}
