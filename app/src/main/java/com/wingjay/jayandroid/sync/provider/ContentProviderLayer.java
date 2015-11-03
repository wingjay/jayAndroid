package com.wingjay.jayandroid.sync.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.wingjay.jayandroid.sync.db.DailyLog;
import com.wingjay.jayandroid.sync.db.Reminder;
import com.wingjay.jayandroid.sync.db.SyncDatabaseHelper;
import com.wingjay.jayandroid.sync.service.SyncService;

/**
 * Provide data for user to use.
 * RESTful Uri.
 * Based to the given uri, this provider will know which data user want to change.
 * User can do query, insert, delete and update methods to these data.
 * In some methods like insert delete and update, it should do two things:
 * 1. change local data in db, and set this record as both 'dirty' mark and 'unSync' label.
 * 2. update this change to server.
 * After receiving its response, clean the label of 'dirty' mark;
 * If the sync of this record is successful, clean the label of 'unSync';
 *
 */
public class ContentProviderLayer extends ContentProvider {

    private static UriMatcher uriMatcher;
    private static final int DAILYLOG = 1;
    private static final int DAILYLOGS = 2;
    private static final int REMINDER = 3;
    private static final int REMINDERS = 4;
    private static final String AUTHORITY = "com.wingjay.jayandroid.contentprovider";
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "dailylog", DAILYLOG);
        uriMatcher.addURI(AUTHORITY, "dailylogs", DAILYLOGS);
        uriMatcher.addURI(AUTHORITY, "reminder", REMINDER);
        uriMatcher.addURI(AUTHORITY, "reminders", REMINDERS);
    }

    private SyncDatabaseHelper databaseHelper;
    private SyncService syncService;

    private DailyLog dailyLog;
    private Reminder reminder;
    @Override
    public boolean onCreate() {
        databaseHelper = new SyncDatabaseHelper(getContext(), SyncDatabaseHelper.DEFAULT_DB_NAME, 1);
        syncService = new SyncService();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case DAILYLOG:
                // store data locally
                long insertId = db.insert(dailyLog.getTableName(), DailyLog._ID, values);
                // start sync service to push data to server
                Intent syncIntent = new Intent(getContext(), SyncService.class);
                syncIntent.putExtra(DailyLog.DATE, values.getAsString(DailyLog.DATE));
                getContext().startService(syncIntent);

        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
