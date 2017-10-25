package com.wingjay.jayandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Jay on 5/10/17.
 */

public class ForTestActivity extends BaseActivity {

    private static final String TAG = "jaydebug";
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Observable.interval(1, TimeUnit.SECONDS)
        //    .compose(this.<Long>bindUntilEvent(ActivityLifecycle.STOP))
        //    .subscribe(new Action1<Long>() {
        //      @Override
        //      public void call(Long aLong) {
        //        Log.e("jaydebug", "interval " + aLong);
        //      }
        //    });
        //
        //Observable loadDataObservable = Observable.defer(new Func0<Observable<Object>>() {
        //  @Override
        //  public Observable<Object> call() {
        //    Log.e("jaydebug", "defer");
        //    return Observable.just(null).map(new Func1<Object, Object>() {
        //      @Override
        //      public Object call(Object o) {
        //        Log.e("jaydebug", "loadData");
        //        return null;
        //      }
        //    });
        //  }
        //});
        //executeWhen(loadDataObservable, ActivityLifecycle.PRE_INFLATE);

        Log.e(TAG, "开始网络请求");
        Observable.just("1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    Log.e(TAG, "网络请求结束");
                    button.setText(s);
                }
            });
        Log.e(TAG, "setContentView，开始Inflate.");
        try {
            Thread.sleep(5000);
            Log.e(TAG, "Inflate结束，耗时5秒.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_test);

        Log.e(TAG, "开始findViewById");
        button = findViewById(R.id.bt1);
    }
}
