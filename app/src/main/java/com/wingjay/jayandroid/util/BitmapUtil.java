package com.wingjay.jayandroid.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Jay on 3/9/16.
 */
public class BitmapUtil {

  public static int getBitmapByteSize(Bitmap bitmap) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    byte[] imageInByte = stream.toByteArray();
    return imageInByte.length;
  }

}
