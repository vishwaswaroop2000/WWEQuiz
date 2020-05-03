package com.example.wwequiz;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.wwequiz.UserTableModel.*;
import com.example.wwequiz.provider.UserContentProvider;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HelperForUserDB extends SQLiteOpenHelper {

    private SQLiteDatabase userDb;
    private ContentResolver contentResolver;

    protected static final String DB_NAME = "User.db";
    protected static final int DB_VERSION = 1;


    public HelperForUserDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase userDb) {
        this.userDb = userDb;
        String sql = "CREATE TABLE "+UsersTable.TABLE_NAME+" ( "+
                UsersTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                UsersTable.COLUMN_USERNAME +" VARCHAR, "+
                UsersTable.COLUMN_LEVEL+" INT(1), " +
                UsersTable.COLUMN_EASYHS+" INT(2), "+
                UsersTable.COLUMN_MEDUIMHS+" INT(2), "+
                UsersTable.COLUMN_HARDHS+" INT(2) "+")";
        userDb.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ UsersTable.TABLE_NAME);
        onCreate(db);
    }

    public void addUser(User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersTable.COLUMN_USERNAME,user.getName());
        contentValues.put(UsersTable.COLUMN_LEVEL,user.getLevel());
        contentValues.put(UsersTable.COLUMN_EASYHS,user.getHighScoreInEasyLevel());
        contentValues.put(UsersTable.COLUMN_MEDUIMHS,user.getHighScoreInMediumLevel());
        contentValues.put(UsersTable.COLUMN_HARDHS,user.getHighScoreInHardLevel());
        contentResolver.insert(UserContentProvider.CONTENT_URI,contentValues);
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase userDb = this.getReadableDatabase();
        Cursor cursor = userDb.rawQuery("SELECT * FROM "+UsersTable.TABLE_NAME,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            User user = new User(cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME))
                    ,cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_LEVEL)),
                    cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_EASYHS)),
                    cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_MEDUIMHS)),
                    cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_HARDHS)));
            userList.add(user);

            cursor.moveToNext();
        }

        cursor.close();
        return userList;
    }

    public User getUser(String name) {
        deleteUser("Ben");
        SQLiteDatabase userDb = this.getReadableDatabase();
        Log.i("SQL", "SELECT * FROM " + UsersTable.TABLE_NAME + " WHERE " + UsersTable.COLUMN_USERNAME + " = '" + name + "'");
        Cursor cursor = userDb.rawQuery("SELECT * FROM " + UsersTable.TABLE_NAME +
                " WHERE " + UsersTable.COLUMN_USERNAME + " = '" + name + "'", null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            return new User(cursor.getString(cursor.getColumnIndex(UsersTable.COLUMN_USERNAME))
                    , cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_LEVEL)),
                    cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_EASYHS)),
                    cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_MEDUIMHS)),
                    cursor.getInt(cursor.getColumnIndex(UsersTable.COLUMN_HARDHS)));
        } else {
            return null;
        }
    }

    public boolean deleteUser(String name) {
        String query = "Select * FROM " + UsersTable.TABLE_NAME + " WHERE " + UsersTable.COLUMN_USERNAME + " = '" + name + "'";
        SQLiteDatabase userDb = this.getWritableDatabase();
        Cursor cursor = userDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            userDb.delete(UsersTable.TABLE_NAME, UsersTable.COLUMN_USERNAME+" = '"+name+"'",null);
            return true;
        }
        return false;
    }
}
