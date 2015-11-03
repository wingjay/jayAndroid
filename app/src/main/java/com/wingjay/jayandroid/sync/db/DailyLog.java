package com.wingjay.jayandroid.sync.db;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wingjay on 9/15/15.
 */
public class DailyLog implements Table{

    public static final String DATE = "date";
    public static final String TEMPERATURE = "temperature";
    public static final String WEIGHT = "weight";

    @Override
    public String getTableName() {
        return "dailylog";
    }

}
