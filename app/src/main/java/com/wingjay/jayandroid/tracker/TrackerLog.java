package com.wingjay.jayandroid.tracker;

import android.util.Log;

/**
 * TrackerLog
 *
 * @author wingjay
 * @date 2017/10/26
 */
public class TrackerLog {
    private static final String TAG = "Tracker";
    public static void e(String msg) {
        Log.e(TAG, msg);
    }
}
