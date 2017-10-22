package com.wingjay.jayandroid.livedata;

import java.util.concurrent.TimeUnit;

import android.arch.lifecycle.LiveData;
import android.util.Log;
import com.wingjay.jayandroid.livedata.LocationLiveData.Location;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * LocationLiveData
 *
 * @author wingjay
 * @date 2017/09/24
 */
public class LocationLiveData extends LiveData<Location> {
    private static final String TAG = "jaydebug";
    private Subscription loadDataSubscription;
    @Override
    protected void onActive() {
        super.onActive();
        Log.e(TAG, "onActive LocationLiveData");
        loadData();
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Log.e(TAG, "onInactive LocationLiveData");
        stopLoad();
    }

    private void loadData() {
        Log.e(TAG, "loadData: START");
        loadDataSubscription = Observable.interval(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    Log.e(TAG, "call: SetValue" + aLong);
                    setValue(new Location("ShangHai" + aLong));
                }
            });
    }

    private void stopLoad() {
        loadDataSubscription.unsubscribe();
        Log.e(TAG, "loadData: STOP");
    }

    static class Location {
        String name;
        public Location(String name) {
            this.name = name;
        }
    }
}
