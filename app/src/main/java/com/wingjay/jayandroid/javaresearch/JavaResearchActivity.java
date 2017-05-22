package com.wingjay.jayandroid.javaresearch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.wingjay.jayandroid.BaseActivity;

/**
 * Created by Jay on 3/22/17.
 */

public class JavaResearchActivity extends BaseActivity {

  final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      Log.i("jaydebug", msg.toString());
      super.handleMessage(msg);
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        Looper.prepare();
        Handler workerThread = new Handler() {
          @Override
          public void handleMessage(Message msg) {
            super.handleMessage(msg);
          }
        };
        Looper.loop();
      }
    });
    thread.start();
  }
}
