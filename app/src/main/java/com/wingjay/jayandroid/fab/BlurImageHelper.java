package com.wingjay.jayandroid.fab;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

/**
 * Created by Jay on 12/23/15.
 */
public class BlurImageHelper {

  public static Bitmap blurImageWithRs(Context context, Bitmap input, int radius) {
    RenderScript renderScript = RenderScript.create(context);
    Allocation allocation = Allocation.createFromBitmap(renderScript, input);

    ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
    blur.setRadius(radius);
    blur.setInput(allocation);

    Bitmap result = Bitmap.createBitmap(input.getWidth(), input.getHeight(), input.getConfig());
    Allocation outAlloc = Allocation.createFromBitmap(renderScript, result);
    blur.forEach(outAlloc);
    outAlloc.copyTo(result);

    renderScript.destroy();
    return result;
  }
}
