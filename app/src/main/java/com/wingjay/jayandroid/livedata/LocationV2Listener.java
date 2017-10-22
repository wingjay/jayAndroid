package com.wingjay.jayandroid.livedata;

import android.arch.lifecycle.Lifecycle;
import android.util.Log;

/**
 * LocationV2Listener
 *
 * @author wingjay
 * @date 2017/10/16
 */
public class LocationV2Listener extends LocationListener {
    public LocationV2Listener(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void start() {
        //super.start();
        // it won't be invoked when Event.ON_CREATE
        Log.d("jaydebug", "LocationV2Listener start");
    }

    @Override
    protected void stop() {
        //super.stop();
        // it won't be invoked when Event.ON_STOP
        Log.d("jaydebug", "LocationV2Listener stop");
    }
}
