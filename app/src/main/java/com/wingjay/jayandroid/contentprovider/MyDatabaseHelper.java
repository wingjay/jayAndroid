package com.wingjay.jayandroid.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wingjay.jayandroid.R;

/**
 * Created by wingjay on 9/15/15.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String JAY_DB_NAME = "jayDb";
    public static final String JAY_DB_2_NAME = "jayDb2";

    private String CREATE_TABLE_SQL = "create table user (id integer primary key autoincrement, name string not null, " +
            "age string not null, height integer)";
    public MyDatabaseHelper(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // when a db is created for the first time.
        Log.i("MyDatabaseHelper", "create user table in db");
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // when database upgrade.
        Log.i("MyDatabaseHelper", "db onUpgrade");
    }
}
