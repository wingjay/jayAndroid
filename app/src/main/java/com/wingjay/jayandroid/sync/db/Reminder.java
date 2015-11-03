package com.wingjay.jayandroid.sync.db;

import android.net.Uri;

/**
 * Created by wingjay on 9/15/15.
 */
public class Reminder implements Table{

    public static final String TITLE = "title";
    public static final String TIME = "time";

    @Override
    public String getTableName() {
        return "reminder";
    }
}
