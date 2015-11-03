package com.wingjay.jayandroid.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by wingjay on 9/15/15.
 */
public class JayContentProvider extends ContentProvider {
    private static final String TAG = "UserContentProvider";

    // when user call uri, here we can interpret uri.
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int USER = 1;
    private static final int USERS = 2;
    private static final int BOOK = 3;
    private static final int BOOKS = 4;

    static {
        uriMatcher.addURI(Users.AUTHORITY, "user/#", USER);
        uriMatcher.addURI(Users.AUTHORITY, "users", USERS);
        uriMatcher.addURI(Books.AUTHORITY, "book/#", BOOK);
        uriMatcher.addURI(Books.AUTHORITY, "books", BOOKS);
    }

    // database, data source
    private MyDatabaseHelper databaseHelper;

    // this method will run just once.
    @Override
    public boolean onCreate() {
        //connect database
        // initial
        databaseHelper = new MyDatabaseHelper(getContext(), MyDatabaseHelper.JAY_DB_NAME, 1);
        Log.i(TAG, "onCreate");
        return true;
    }

    //based on URI, return MIME type of data
    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "getType with uri : " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case USER:
            case BOOK:
                return "vnd.android.cursor.item/org.crazyit.dict";
            case USERS:
            case BOOKS:
                return "vnd.android.cursor.dir/org.crazyit.dict";
            default:
                throw throwException(uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query with uri : " + uri.toString() + ", where : " + selection);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case USERS:
                return db.query("user", projection, selection, selectionArgs, null, null, sortOrder);
            case USER:
                long id = ContentUris.parseId(uri);
                String whereClause = Users.User._ID + "=" + id;
                String where = (selection != null) ? (selection + " and " + whereClause) : whereClause;
                return db.query("user", projection, where, selectionArgs, null, null, sortOrder);
            default:
                throw throwException(uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.i(TAG, "insert in uri : " + uri.toString() + ", values are : " + values.toString());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case USERS:
                long newId = db.insert("user", Users.User._ID, values);
                if (newId > 0) {
                    Uri newUri = ContentUris.withAppendedId(uri, newId);
                    //notify data changed
                    getContext().getContentResolver().notifyChange(newUri, null);
                    return newUri;
                }
                break;
            default:
                throw throwException(uri);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.i(TAG, "delete uri : " + uri.toString() + ", where : " + selection);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case USER:
                long deleteId = ContentUris.parseId(uri);
                String whereCaluse = Users.User._ID + "=" + String.valueOf(deleteId);
                String where = (selection != null) ? selection + " and " + whereCaluse : whereCaluse;
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete("user", where, selectionArgs);
            case USERS:
                getContext().getContentResolver().notifyChange(uri, null);
                return db.delete("user", selection, selectionArgs);
            default:
                throw throwException(uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update uri : " + uri.toString() + ", new values: " + values.toString());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case USER:
                long updateId = ContentUris.parseId(uri);
                String whereCaluse = Users.User._ID + "=" + String.valueOf(updateId);
                String where = (selection != null) ? selection + " and " + whereCaluse : whereCaluse;
                getContext().getContentResolver().notifyChange(uri, null);
                return db.update("user", values, where, selectionArgs);
            case USERS:
                getContext().getContentResolver().notifyChange(uri, null);
                return db.update("user", values, selection, selectionArgs);
            default:
                throw throwException(uri);
        }
    }

    private IllegalArgumentException throwException(Uri uri) {
        return new IllegalArgumentException("unknown uri: " + uri);
    }

}
