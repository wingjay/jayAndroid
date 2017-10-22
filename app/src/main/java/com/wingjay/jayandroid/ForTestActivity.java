package com.wingjay.jayandroid;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.util.Log;
import com.wingjay.jayandroid.autolifecycle.ActivityLifecycle;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by Jay on 5/10/17.
 */

public class ForTestActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Observable.interval(1, TimeUnit.SECONDS)
        .compose(this.<Long>bindUntilEvent(ActivityLifecycle.STOP))
        .subscribe(new Action1<Long>() {
          @Override
          public void call(Long aLong) {
            Log.e("jaydebug", "interval " + aLong);
          }
        });

    Observable loadDataObservable = Observable.defer(new Func0<Observable<Object>>() {
      @Override
      public Observable<Object> call() {
        Log.e("jaydebug", "defer");
        return Observable.just(null).map(new Func1<Object, Object>() {
          @Override
          public Object call(Object o) {
            Log.e("jaydebug", "loadData");
            return null;
          }
        });
      }
    });
    executeWhen(loadDataObservable, ActivityLifecycle.PRE_INFLATE);
    setContentView(R.layout.activity_test);
  }
}
