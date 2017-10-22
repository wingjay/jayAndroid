package com.wingjay.jayandroid.autolifecycle;

import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.util.Log;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.R;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * AutoLifecycleActivity
 *
 * @author wingjay
 * @date 2017/10/22
 */
public class AutoLifecycleActivity extends BaseActivity {
    private IKnowLifecycle mIKnowLifecycle = new IKnowLifecycle(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable.interval(1, TimeUnit.SECONDS)
            .compose(this.<Long>bindUntilEvent(ActivityLifecycle.STOP))
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.e(TAG, "auto-stop when Activity onDestroy: interval " + aLong);
                }
            });

        Observable loadDataObservable = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(null).map(new Func1<Object, Object>() {
                    @Override
                    public Object call(Object o) {
                        Log.e(TAG, "auto-execute when Activity PRE_INFLATE: loadData()");
                        return null;
                    }
                });
            }
        });
        executeWhen(loadDataObservable, ActivityLifecycle.PRE_INFLATE);

        setContentView(R.layout.activity_test);
    }
}
