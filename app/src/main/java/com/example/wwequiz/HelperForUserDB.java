package com.example.wwequiz;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wwequiz.provider.UserContentProvider;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HelperForUserDB extends SQLiteOpenHelper {

    private SQLiteDatabase userDb;
    private ContentResolver contentResolver;

    protected static final String DB_NAME = "User.db";
    protected static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "user_names";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_EASYHS = "easyHS";
    public static final String COLUMN_MEDUIMHS = "mediumHS";
    public static final String COLUMN_HARDHS = "hardHS";

    private static String[] projection = {COLUMN_USERNAME,COLUMN_LEVEL,
            COLUMN_EASYHS,COLUMN_MEDUIMHS,COLUMN_HARDHS};

    public HelperForUserDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase userDb) {
        this.userDb = userDb;
        String sql = "CREATE TABLE "+TABLE_NAME+" ("+
                COLUMN_USERNAME +" VARCHAR, "+
                COLUMN_LEVEL+" INT(1), " +
                COLUMN_EASYHS+" INT(2), "+
                COLUMN_MEDUIMHS+" INT(2), "+
                COLUMN_HARDHS+" INT(2) "+")";
        userDb.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME,user.getName());
        contentValues.put(COLUMN_LEVEL,user.getLevel());
        contentValues.put(COLUMN_EASYHS,user.getHighScoreInEasyLevel());
        contentValues.put(COLUMN_MEDUIMHS,user.getHighScoreInMediumLevel());
        contentValues.put(COLUMN_HARDHS,user.getHighScoreInHardLevel());
        contentResolver.insert(UserContentProvider.CONTENT_URI,contentValues);
    }

    public ArrayList<User> getUsers(){

        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = contentResolver.query(UserContentProvider.CONTENT_URI,projection,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            User user = new User(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                    ,cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EASYHS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_MEDUIMHS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_HARDHS)));
            userList.add(user);
            cursor.moveToNext();
        }

        cursor.close();
        return userList;
    }

    public User getUser(String name) {

        String selection = COLUMN_USERNAME+" = '"+name+"'";
        Cursor cursor = contentResolver.query(UserContentProvider.CONTENT_URI,projection,selection,null,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            return new User(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                    , cursor.getInt(cursor.getColumnIndex(COLUMN_LEVEL)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_EASYHS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_MEDUIMHS)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_HARDHS)));
        } else {
            return null;
        }
    }

    public void updateUserDetails(User user){
        String selection = COLUMN_USERNAME+" = '"+user.getName()+"'";
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LEVEL,user.getLevel());
        contentValues.put(COLUMN_EASYHS,user.getHighScoreInEasyLevel());
        contentValues.put(COLUMN_MEDUIMHS,user.getHighScoreInMediumLevel());
        contentValues.put(COLUMN_HARDHS,user.getHighScoreInHardLevel());
        contentResolver.update(UserContentProvider.CONTENT_URI,contentValues,selection,null);
    }

    public void deleteUser(String name) {
        String selection = "username = '"+name+"'";
        contentResolver.delete(UserContentProvider.CONTENT_URI,
                selection, null);

    }
}
