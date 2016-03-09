package com.wingjay.jayandroid.fastblur;

import android.graphics.Bitmap;

/**
 * Created by jay on 11/26/15.
 */
public class FastBlurJni {
    public static native void blurIntArray(int[] pImg, int w, int h, int r);

//    public static native void blurBitMap(Bitmap bitmap, int r);

    public static native String test();
    public static native String test(int a);

    static {
//        System.loadLibrary("FastBlurLib"); //defaultConfig.ndk.moduleName
    }
}
