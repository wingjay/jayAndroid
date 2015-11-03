package com.wingjay.jayandroid.contentprovider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User data will be provided by our contentProvider.
 */
public class Users {

    public static final String AUTHORITY = "com.wingjay.jayandroid.contentprovider";
    public static final Uri baseUri = Uri.parse("content://" + AUTHORITY);

    //column
    public static final class User implements BaseColumns {
        public static final String _ID = "id";
        public static final String NAME = "name";
        public static final String AGE = "age";

        //Uri as API
        public final static Uri JAY_ANDROID_USER_CONTENT_URI =
                Uri.withAppendedPath(baseUri, "user");
        public final static Uri JAY_ANDROID_USERS_CONTENT_URI =
                Uri.withAppendedPath(baseUri, "users");

    }

}
