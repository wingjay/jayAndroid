package com.wingjay.jayandroid.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * LocationListener
 *
 * @author wingjay
 * @date 2017/09/24
 */
public class LocationListener implements LifecycleObserver {

    private static final String TAG = "jaydebug";
    private Lifecycle lifecycle;

    public LocationListener(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @OnLifecycleEvent(Event.ON_CREATE)
    public void start() {
        Log.e(TAG, "Ready for START");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e(TAG, "Sleep");
                    Thread.sleep(5000);
                    if (lifecycle.getCurrentState().isAtLeast(State.STARTED)) {
                        Log.e(TAG, "START!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnLifecycleEvent(Event.ON_STOP)
    public void stop() {
        Log.e(TAG, "STOP");
    }
}
