package com.muftialies.made.finalsubmission.provider;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.muftialies.made.finalsubmission";
    private static final String SCHEME = "content";
    public static final String TABLE_NAME = "provider";


    public static final class ShowColumns implements BaseColumns {
        public static final String ID = "show_id";
        public static final String TITLE = "show_title";
        public static final String OVERVIEW = "show_overview";
        public static final String RATING = "show_rating";
        public static final String IMAGE_POSTER = "show_poster";
        public static final String DATE = "show_date";
    }

    /*public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();*/

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
}
