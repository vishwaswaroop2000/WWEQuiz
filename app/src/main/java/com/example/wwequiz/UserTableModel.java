package com.example.wwequiz;

import android.provider.BaseColumns;

public final class UserTableModel {

    private UserTableModel(){}

    public static class UsersTable implements BaseColumns {
        public static final String TABLE_NAME = "user_names";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_EASYHS = "easyHS";
        public static final String COLUMN_MEDUIMHS = "mediumHS";
        public static final String COLUMN_HARDHS = "hardHS";

    }
}
