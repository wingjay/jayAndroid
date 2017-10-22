package com.wingjay.jayandroid.autolifecycle;

import android.util.Log;

/**
 * IKnowLifecycle
 *
 * @author wingjay
 * @date 2017/10/22
 */
public class IKnowLifecycle {

    public IKnowLifecycle(ILifecycleProvider lifecycleProvider) {
        AutoLifecycle.getInstance().init(this, lifecycleProvider);
    }

    @RxLifecycleEvent(activity = ActivityLifecycle.CREATE)
    private void onCreate() {
        Log.e("jaydebug", "IKnowLifecycle onCreate");
    }

    @RxLifecycleEvent(activity = ActivityLifecycle.START)
    private void onSTART() {
        Log.e("jaydebug", "IKnowLifecycle onSTART");
    }

    @RxLifecycleEvent(activity = ActivityLifecycle.RESUME)
    private void onRESUME() {
        Log.e("jaydebug", "IKnowLifecycle onRESUME");
    }

    @RxLifecycleEvent(activity = ActivityLifecycle.PAUSE)
    private void onPAUSE() {
        Log.e("jaydebug", "IKnowLifecycle onPAUSE");
    }

    @RxLifecycleEvent(activity = ActivityLifecycle.STOP)
    private void onSTOP() {
        Log.e("jaydebug", "IKnowLifecycle onSTOP");
    }

    @RxLifecycleEvent(activity = ActivityLifecycle.DESTROY)
    private void onDESTROY() {
        Log.e("jaydebug", "IKnowLifecycle onDESTROY");
    }
}
