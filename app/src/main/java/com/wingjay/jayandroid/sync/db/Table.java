package com.wingjay.jayandroid.sync.db;

import android.net.Uri;

/**
 * Created by wingjay on 9/15/15.
 */
public interface Table {
    public static final Uri base = Uri.parse("content://com.wingjay.jayandroid.contentprovider");

    public static final String _ID = "id";
    public static final String DIRTY = "dirty";
    public static final String SYNC = "sync";
    public static final String SYNC_TACNSCATION_ID = "sync_id";


    public String getTableName();
}
