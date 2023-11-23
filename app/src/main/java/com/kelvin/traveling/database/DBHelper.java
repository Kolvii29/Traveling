package com.kelvin.traveling.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "login.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, userUsername TEXT, userEmail TEXT UNIQUE, password TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertData(String userUsername, String userEmail, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userUsername", userUsername);
        values.put("userEmail", userEmail);
        values.put("password", password);

        try {
            long result = db.insertOrThrow("users", null, values);
            return result != -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    public boolean checkUserName(String userUsername) {
        SQLiteDatabase db = getReadableDatabase();

        try (db; Cursor cursor = db.query("users", null, "userUsername=?", new String[]{userUsername}, null, null, null)) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkUserEmail(String userEmail) {
        SQLiteDatabase db = getReadableDatabase();

        try (db; Cursor cursor = db.query("users", null, "userEmail=?", new String[]{userEmail}, null, null, null)) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkUserEmailPassword(String userEmail, String password) {
        SQLiteDatabase db = getReadableDatabase();

        try (db; Cursor cursor = db.query("users", null, "userEmail=? AND password=?", new String[]{userEmail, password}, null, null, null)) {
            return cursor.getCount() > 0;
        }
    }
}
