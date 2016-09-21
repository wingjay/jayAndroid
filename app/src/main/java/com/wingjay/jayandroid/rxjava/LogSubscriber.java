package com.wingjay.jayandroid.rxjava;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by Jay on 6/16/16.
 */
public class LogSubscriber<T> extends Subscriber<T> {
  @Override
  public void onCompleted() {
    Log.d("jaydebug", "onCompleted");
  }

  @Override
  public void onError(Throwable e) {
    Log.d("jaydebug", "onError e: " + e);
  }

  @Override
  public void onNext(T t) {
    if (t instanceof ILoggable) {
      Log.d("jaydebug", "onNext " + ((ILoggable) t).getLoggingString());
    } else {
      Log.d("jaydebug", "onNext " + t);
    }
  }
}
