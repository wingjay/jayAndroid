package com.wingjay.jayandroid.lowpoly;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.uniquestudio.lowpoly.LowPoly;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Jay on 9/20/16.
 */
public class LowPolyActivity extends BaseActivity {


//  public static native String test_a(String s);
//  public static native void test_b();
//  public

  @Bind(R.id.image)
  ImageView imageView;

  @Bind(R.id.real_image)
  ImageView myImageView;

  @Bind(R.id.low_poly_blur)
  EditText blurValue;

  String[] mediumUrl = {
      "https://d.fastcompany.net/multisite_files/fastcompany/imagecache/slideshow_large/slideshow/2016/02/3056470-slide-s-2-15-of-the-best-designs-merging-fashion-and-tech.jpg",
      "https://www.virginchan.org/boards/b/image/1387/83/13878366673409.png",
      "http://orig00.deviantart.net/1617/f/2014/109/e/1/taylor_swift_png_by_ppineapples-d7f48mq.png",
      "http://orig07.deviantart.net/ab07/f/2011/249/9/d/taylor_swift_png_by_xdicsii-d494c2z.png",
      "http://www.topdrawersoccer.com/the91stminute/wp-content/uploads/2012/07/Kobe.png",
      "http://www.pngmart.com/files/2/Kobe-Bryant-Transparent-Background.png",
      "http://costaricadivecenter.com/images/Slider/Fish/Sea-turtle-in-Guanacaste.png",
      "https://www.visitsealife.com/SiteImages/Assets/1/1/turtle.png",
      "http://img06.tooopen.com/images/20160811/tooopen_sy_175085182258.jpg",
      "http://www.bz55.com/uploads/allimg/130425/1-130425101I6.jpg",
      "http://uploadfile.zhuoku.org/2015/0828/20150828015216573.jpg",
      "http://www.bz55.com/uploads/allimg/141024/138-141024103032.jpg"
  };

  int index = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_low_poly);
  }

  @OnClick(R.id.low_poly_update)
  void update() {
    final Integer blur;
    if (!TextUtils.isEmpty(blurValue.getText().toString())) {
      blur = Integer.parseInt(blurValue.getText().toString());
    } else {
      blur = 20;
    }
    loadImage(blur);
  }

  @OnClick(R.id.low_poly_next)
  void next() {
    index++;
    if (index >= mediumUrl.length) {
      index = 0;
    }
    blurValue.setText("20");
    loadImage(20);
  }

  private void loadImage(final int blur) {
    LowPolyHelper.generate(null, blur, true, 0);
    Picasso.with(this).load(mediumUrl[index]).into(new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.d("jaydebug", "onBitmapLoaded success");
        long time = System.currentTimeMillis();
        Bitmap lowpolyBitmap = LowPoly.generate(bitmap, blur);
        Log.d("jaydebug", "c++ time: " + (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        Bitmap myLowpolyBitmap = LowPolyHelper.generate(bitmap, blur, true, 0);
        Log.d("jaydebug", "java time: " + (System.currentTimeMillis() - time));
        imageView.setImageBitmap(lowpolyBitmap);
        myImageView.setImageBitmap(myLowpolyBitmap);
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {
        Log.d("jaydebug", "onBitmapFailed");
      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {
        Log.d("jaydebug", "onPrepareLoad");
      }
    });
  }
}
