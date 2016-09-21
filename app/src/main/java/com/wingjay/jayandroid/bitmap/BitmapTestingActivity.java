package com.wingjay.jayandroid.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Transformation;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.fastblur.BlurImageView;

import butterknife.ButterKnife;

/**
 * Created by Jay on 6/7/16.
 */
public class BitmapTestingActivity extends BaseActivity {

  TextView tip;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bitmap_testing);

    BlurImageView blurImageView = ButterKnife.findById(this, R.id.blur_image_view);
//    blurImageView.setProgressBarBgColor(Color.BLUE);
//    blurImageView.setProgressBarColor(Color.RED);
    blurImageView.setFullImageByUrl("https://avatars0.githubusercontent.com/u/9619875?v=3&s=460",
        "http://www.itmin.cn/wp-content/uploads/2015/04/flask-login3.jpg");

    final CustomizeTransformation customizeTransformation = new CustomizeTransformation();

    Button decodeBitmapFromResBtn = ButterKnife.findById(this, R.id.decode_bitmap_from_resource);
    Button decodeAndTransformBtn = ButterKnife.findById(this, R.id.decode_bitmap_from_resource_and_transform);
    final ImageView imageView = ButterKnife.findById(this, R.id.bitmap_imageview);
    tip = ButterKnife.findById(this, R.id.tip);

    decodeBitmapFromResBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setTip("1. Normal Decode");
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.drawer_default_user, options);
//        Picasso.with(BitmapTestingActivity.this)
//            .load("https://wingjay.com/img/dream222.jpg")
//            .placeholder(new BitmapDrawable(getResources(), bitmap))
//            .into(imageView);
      }
    });

    decodeAndTransformBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        setTip("2. Decode bitmap and Transform into imageView");
        Log.d("jaydebug", "imageView width " + imageView.getWidth() + ", height " + imageView.getHeight());
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.drawer_default_user, options);
        customizeTransformation.transform(bitmap);
//        Picasso.with(BitmapTestingActivity.this)
//            .load("https://wingjay.com/img/dream222.jpg")
//            .placeholder(new BitmapDrawable(getResources(), customizeTransformation.transform(bitmap)))
//            .into(imageView);
      }
    });

  }

  class CustomizeTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
      source = getRoundedCornerBitmap(source);
      int width = source.getWidth();
      int height = source.getHeight();
      Bitmap dest = Bitmap.createBitmap(width, height, source.getConfig());
      Canvas canvas = new Canvas(dest);
      Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
      paint.setFilterBitmap(true);
      paint.setColor(Color.WHITE);

      RectF circleRect = new RectF(0, 0, width, height);
      canvas.drawArc(circleRect, 0, 360, true, paint);

      RectF imageRect = new RectF(4, 4, width - 4, height - 4);
      canvas.drawBitmap(source, new Rect(0, 0, width, height), imageRect, paint);
      if (source != dest) {
        source.recycle();
      }

      return dest;
    }

    @Override
    public String key() {
      return "CustomizeTransformation";
    }
  }

  public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();

    float left, right, top, bottom;
    if (width < height) {
      left = 0;
      right = width;
      float diff = height - width;
      top = diff / 2;
      bottom = top + width;
    } else {
      top = 0;
      bottom = height;
      float diff = width - height;
      left = diff / 2;
      right = left + height;
    }

    Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
    final RectF rectF = new RectF(new Rect(0, 0, width, height));

    paint.setAntiAlias(true);
    paint.setFilterBitmap(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, width, height, paint);

    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, new Rect(0, 0, width, height), paint);

    if (output != bitmap) {
      bitmap.recycle();
    }

    return output;
  }

  private void setTip(String tipString) {
    tip.setText(tipString);
  }

}
