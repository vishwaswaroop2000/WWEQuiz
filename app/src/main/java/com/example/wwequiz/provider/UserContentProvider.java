package com.example.wwequiz.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.example.wwequiz.HelperForUserDB;

public class UserContentProvider extends ContentProvider {

    private HelperForUserDB userDB;

    private static final String AUTHORITY =
            "com.example.wwequiz.provider.UserContentProvider";
    private static final String USERS_TABLE = HelperForUserDB.TABLE_NAME;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USERS_TABLE);

    private static final UriMatcher URIMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    private static final int USERS = 0;
    private static final int USERNAME = 1;
    private static final int LEVEL = 2;
    private static final int EASYHS = 3;
    private static final int MEDIUMHS = 4;
    private static final int HARDHS = 5;

    static {
        URIMatcher.addURI(AUTHORITY, USERS_TABLE, USERS);
        URIMatcher.addURI(AUTHORITY, USERS_TABLE +"/#", USERNAME);
        URIMatcher.addURI(AUTHORITY, USERS_TABLE +"/#", LEVEL);
        URIMatcher.addURI(AUTHORITY, USERS_TABLE +"/#", EASYHS);
        URIMatcher.addURI(AUTHORITY, USERS_TABLE +"/#", MEDIUMHS);
        URIMatcher.addURI(AUTHORITY, USERS_TABLE +"/#", HARDHS);
    }

    @Override
    public boolean onCreate() {
        userDB = new HelperForUserDB(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(HelperForUserDB.TABLE_NAME);
        int CodeOfURI = URIMatcher.match(uri);
        switch (CodeOfURI) {
            case USERNAME:
                queryBuilder.appendWhere(HelperForUserDB.COLUMN_USERNAME + "=" + uri.getLastPathSegment());
                break;
            case USERS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }

        Cursor cursor = queryBuilder.query(userDB.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int CodeOfURI = URIMatcher.match(uri);
        SQLiteDatabase sqlDB = userDB.getWritableDatabase();
        long id = 0;
        switch (CodeOfURI) {
            case USERS:
                id = sqlDB.insert(HelperForUserDB.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(USERS_TABLE + "/" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return userDB.getWritableDatabase().update(HelperForUserDB.TABLE_NAME, values,
                selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        getContext().getContentResolver().notifyChange(uri, null);
        return userDB.getWritableDatabase().delete(HelperForUserDB.TABLE_NAME,
                selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
