package com.wingjay.jayandroid.sync;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;

import com.wingjay.jayandroid.BaseActivity;
import com.wingjay.jayandroid.sync.db.DailyLog;
import com.wingjay.jayandroid.sync.provider.ContentProviderLayer;

import java.text.SimpleDateFormat;

/**
 * change db data based on content provider.
 */
public class SyncActivity extends BaseActivity {

    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentResolver = getContentResolver();

        // change data
        Uri insertUri = Uri.parse("content://com.wingjay.jayandroid.contentprovider" + "/dailylogs");
        ContentValues values = new ContentValues();
        values.put(DailyLog.DATE, "202930291");
        values.put(DailyLog.TEMPERATURE, "38");
        contentResolver.insert(insertUri, values);
    }
}
