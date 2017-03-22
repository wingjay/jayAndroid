package com.wingjay.jayandroid.weex;

import android.content.Context;
import android.util.Log;

import rx.functions.Action1;

/**
 * Created by Jay on 2/23/17.
 */

public class WebFailAction implements Action1<Throwable> {

  private Context context;

  private boolean isSilent;

  public WebFailAction(Context context) {
    this(context, false);
  }

  public WebFailAction(Context context, boolean isSilent) {
    this.context = context.getApplicationContext();
    this.isSilent = isSilent;
  }

  @Override
  public void call(Throwable throwable) {
    Log.d("jaydebug", "Throwable: " + throwable.getMessage());
    throw new RuntimeException(throwable);
  }
}
