package com.wingjay.jayandroid.sync.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wingjay on 9/15/15.
 */
public class SyncDatabaseHelper extends SQLiteOpenHelper {

    public static final String DEFAULT_DB_NAME = "jay";

    public static final String TABLE_DAILYLOG_NAME = "dailylog";

    private static final String CREATE_TABLE_DAILYLOG = "create table dailylog (" +
            "id primary key autoincreament," +
            "date integer not null," +
            "temperature integer," +
            "weight integer," +
            "dirty integer default 0," +
            "sync integer default 0)";

    private static final String CREATE_TABLE_REMINDER = "create table reminder (" +
            "id primary key autoincreament," +
            "title string not null," +
            "time integer not null," +
            "dirty integer default 0," +
            "sync integer default 0)";

    public SyncDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DAILYLOG);
        db.execSQL(CREATE_TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
