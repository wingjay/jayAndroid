package com.wingjay.jayandroid.contentprovider;

import android.net.Uri;

/**
 * Created by wingjay on 9/15/15.
 */
public class Books {

    public static final String AUTHORITY = "com.wingjay.jayandroid.contentprovider";
    public static final Uri base = Uri.parse("content://" + AUTHORITY);

    public final class Book {
        public static final String _ID = "id";
        public static final String NAME = "name";
        public static final String PRICE = "price";
    }


}
