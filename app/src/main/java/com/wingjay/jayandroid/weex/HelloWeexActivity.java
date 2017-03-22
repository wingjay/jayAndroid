package com.wingjay.jayandroid.weex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.wingjay.jayandroid.App;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import com.wingjay.jayandroid.abot.ApiAiResponse;
import com.wingjay.jayandroid.abot.ApiAiService;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;

/**
 * Created by Jay on 2/23/17.
 */

public class HelloWeexActivity extends BaseActivity implements IWXRenderListener {

  private WXSDKInstance mInstance;
  ViewGroup container;

  @Inject
  ApiAiService apiAiService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hello_weex);

    ((App) getApplication()).getAppComponent().inject(this);

    container = (ViewGroup) findViewById(R.id.container);

    mInstance = new WXSDKInstance(this);
    mInstance.registerRenderListener(this);

//    String file = "linear-gradient.js";
//    Log.d("jaydebug", "start render. file:" + file + "\n js: " + WXFileUtils.loadAsset(file, HelloWeexActivity.this));
//    mInstance.render("PAGE 1", WXFileUtils.loadAsset(file, HelloWeexActivity.this),
//        null, null, WXRenderStrategy.APPEND_ASYNC);

    RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
      @Override
      public void handleError(Throwable e) {
        Log.w("Jaydebug", e);
      }
    });

    apiAiService.query("asdf")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<ApiAiResponse>() {
          @Override
          public void call(ApiAiResponse s) {
            Log.d("jaydebug", "ERROR 2");
            throw new RuntimeException("Runtime 2");
          }
        }, new WebFailAction(this));
  }

  @Override
  public void onViewCreated(WXSDKInstance instance, View view) {
    Log.d("jaydebug", "onViewCreated");
    container.addView(view);
  }

  @Override
  public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
    Log.d("jaydebug", "onRenderSuccess width: " + width + ", height: " + height);
  }

  @Override
  public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
    Log.d("jaydebug", "onRefreshSuccess width: " + width + ", height: " + height);
  }

  @Override
  public void onException(WXSDKInstance instance, String errCode, String msg) {
    Log.d("jaydebug", "onException  errCode: " + errCode + ", msg: " + msg);
  }

  @Override
  protected void onResume() {
    super.onResume();
    if(mInstance != null){
      mInstance.onActivityResume();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if(mInstance != null){
      mInstance.onActivityPause();
    }
  }
  
  @Override
  protected void onStop() {
    super.onStop();
    if(mInstance != null){
      mInstance.onActivityStop();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(mInstance != null){
      mInstance.onActivityDestroy();
    }
  }
}
