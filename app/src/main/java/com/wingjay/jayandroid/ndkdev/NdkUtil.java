package com.wingjay.jayandroid.ndkdev;

/**
 * Created by Jay on 9/21/16.
 */
public class NdkUtil {

  static {
    System.loadLibrary("hello-jni");

    System.loadLibrary("sai_micbasex");
    System.loadLibrary("sai_micarray");
    System.loadLibrary("sai_denoise");
    System.loadLibrary("sai-ma");
  }
  public native String stringFromJNI();

  public native String stringFromJayJni();

}
