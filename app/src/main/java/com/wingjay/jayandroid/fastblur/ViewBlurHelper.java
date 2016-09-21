package com.wingjay.jayandroid.fastblur;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wingjay.jayandroid.util.BitmapUtil;

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
    holder.setLayoutParams(new ViewGroup.LayoutParams(
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
        screenshotBitmap.getWidth() / 2,
        screenshotBitmap.getHeight() / 2,
        false);

    // blur this small bitmap
    long start = System.currentTimeMillis();
    bluredScreenshotBitmap = FastBlurUtil.doBlur(smallScreenshotBitmap, radius, true);
    Log.i("viewblurhelper", "pure blur time " + (System.currentTimeMillis() - start) + "ms");

    // set this blur bitmap
    holder.setImageBitmap(bluredScreenshotBitmap);
    holder.setBackgroundColor(Color.WHITE);
    tobeBlurViewGroup.addView(holder, 1);

    // recycle bitmap
    Log.i("viewblurhelper", "whole blur time " + (System.currentTimeMillis() - startBlur) + "ms");
    Log.i("viewblurhelper", "screenshotBitmap size byte "
        + String.valueOf(BitmapUtil.getBitmapByteSize(screenshotBitmap)));
    Log.i("viewblurhelper", "smallScreenshotBitmap size byte "
        + String.valueOf(BitmapUtil.getBitmapByteSize(smallScreenshotBitmap)));
    Log.i("viewblurhelper", "bluredScreenshotBitmap size byte "
        + String.valueOf(BitmapUtil.getBitmapByteSize(bluredScreenshotBitmap)));

    if (screenshotBitmap.isRecycled()) {
      screenshotBitmap.recycle();
    }
    if (smallScreenshotBitmap.isRecycled()) {
      smallScreenshotBitmap.recycle();
    }
  }

  public Bitmap getSmallScreenshotBitmap() {
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

    bluredScreenshotBitmap = FastBlurUtil.doBlur(smallScreenshotBitmap, 12, true);
    return bluredScreenshotBitmap;
  }

  public void removeBlurLayer() {
    tobeBlurViewGroup.removeView(holder);
  }

  public ImageView getBlurImageView() {
    return holder;
  }

}
