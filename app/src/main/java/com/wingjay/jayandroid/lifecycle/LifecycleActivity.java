package com.wingjay.jayandroid.lifecycle;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.lifecycle.LocationLiveData.Location;

/**
 * LifecycleActivity, taste google arch.lifecycle lib.
 *
 * https://developer.android.com/topic/libraries/architecture/lifecycle.html#lco
 *
 * @author wingjay
 * @date 2017/09/24
 */
public class LifecycleActivity extends BaseActivity {
    LocationListener listener = new LocationListener(getLifecycle());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getLifecycle().addObserver(listener);

        LiveData<Location> liveData = new LocationLiveData();
        liveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                Log.e(TAG, location != null ? "onChanged: location: " + location.name : "onChanged: empty location");
            }
        });
    }
}
