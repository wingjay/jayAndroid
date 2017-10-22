package com.wingjay.jayandroid.livedata;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.Lifecycle.State;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
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
        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Event.ON_CREATE)
    protected void start() {
        Log.e(TAG, "LocationListener Ready for START");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e(TAG, "LocationListener Sleep");
                    Thread.sleep(5000);
                    if (lifecycle.getCurrentState().isAtLeast(State.STARTED)) {
                        Log.e(TAG, "LocationListener START!");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnLifecycleEvent(Event.ON_STOP)
    protected void stop() {
        Log.e(TAG, "LocationListener STOP");
    }

    @OnLifecycleEvent(Event.ON_DESTROY)
    private void destory() {
        Log.e(TAG, "LocationListener DESTORY");
    }

    @OnLifecycleEvent(Event.ON_ANY)
    private void any(LifecycleOwner owner, Lifecycle.Event event) {
        Log.e(TAG, "LocationListener ANY: " + event.name() + "; owner: " + owner.toString());
    }
}
