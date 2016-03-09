package com.wingjay.jayandroid.fastblur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * This helper class will blur any given view fastly.
 */
public class ViewBlurHelper {

  private final int DEFAULT_BLUR_RADIUS = 8;

  private ViewGroup tobeBlurViewGroup;

  private ImageView holder;

  private Bitmap screenshotBitmap;
  private Bitmap smallScreenshotBitmap;
  private Bitmap bluredScreenshotBitmap;

  public ViewBlurHelper(ViewGroup tobeBlurViewGroup) {
    this.tobeBlurViewGroup = tobeBlurViewGroup;
    this.holder = new ImageView(tobeBlurViewGroup.getContext());
    holder.setLayoutParams(new FrameLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT));
    holder.setScaleType(ImageView.ScaleType.CENTER_CROP);
  }

  public void blur() {
    blur(DEFAULT_BLUR_RADIUS);
  }

  public void blur(int radius) {
    long startBlur = System.currentTimeMillis();
    if (tobeBlurViewGroup == null) {
      return;
    }

    // get screenshot
    screenshotBitmap = Bitmap.createBitmap(
        tobeBlurViewGroup.getWidth(),
        tobeBlurViewGroup.getHeight(),
        Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(screenshotBitmap);
    tobeBlurViewGroup.draw(canvas);

    // scale screenshot to a very small size
    smallScreenshotBitmap = Bitmap.createScaledBitmap(
        screenshotBitmap,
        screenshotBitmap.getWidth() / 20,
        screenshotBitmap.getHeight() / 20,
        false);

    // blur this small bitmap
    long start = System.currentTimeMillis();
    bluredScreenshotBitmap = FastBlurUtil.doBlur(smallScreenshotBitmap, radius, true);
    Log.i("viewblurhelper", "pure blur time " + (System.currentTimeMillis() - start));

    // set this blur bitmap
    holder.setImageBitmap(bluredScreenshotBitmap);

    tobeBlurViewGroup.addView(holder);

    // recycle bitmap
    Log.i("viewblurhelper", "whole blur time " + (System.currentTimeMillis() - startBlur));
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    screenshotBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    byte[] imageInByte = stream.toByteArray();
    Log.i("viewblurhelper", "screenshotBitmap size byte " + String.valueOf(imageInByte.length));

    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
    smallScreenshotBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
    byte[] imageInByte2 = stream2.toByteArray();
    Log.i("viewblurhelper", "smallScreenshotBitmap size byte " + String.valueOf(imageInByte2.length));

    ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
    bluredScreenshotBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream3);
    byte[] imageInByte3 = stream3.toByteArray();
    Log.i("viewblurhelper", "bluredScreenshotBitmap size byte " + String.valueOf(imageInByte3.length));

    if (screenshotBitmap.isRecycled()) {
      screenshotBitmap.recycle();
    }
    if (smallScreenshotBitmap.isRecycled()) {
      smallScreenshotBitmap.recycle();
    }
//    screenshotBitmap.recycle();
//    smallScreenshotBitmap.recycle();
  }

  public void removeBlurLayer() {
    tobeBlurViewGroup.removeView(holder);
//    bluredScreenshotBitmap.recycle();
  }

  public ImageView getBlurImageView() {
    return holder;
  }

}
