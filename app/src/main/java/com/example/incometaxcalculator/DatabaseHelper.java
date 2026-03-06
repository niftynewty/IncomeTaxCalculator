package com.example.incometaxcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version
    public static final String DATABASE_NAME = "UserData.db";
    public static final String TABLE_NAME = "users_table";

    // History Table Constants
    public static final String HISTORY_TABLE = "history_table";
    public static final String H_COL_1 = "ID";
    public static final String H_COL_2 = "USERNAME";
    public static final String H_COL_3 = "CALC_TYPE"; // Tax or EMI
    public static final String H_COL_4 = "INPUT_DATA";
    public static final String H_COL_5 = "RESULT_DATA";

    // Table Columns for User
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // Version increased to 2 for the new table
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create User Table
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");

        // Create History Table
        db.execSQL("CREATE TABLE " + HISTORY_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, CALC_TYPE TEXT, INPUT_DATA TEXT, RESULT_DATA TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        onCreate(db);
    }

    // Method to insert a new user (Registration)
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username);
        contentValues.put(COL_3, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // Method to check if username and password exist (Login)
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE USERNAME=? AND PASSWORD=?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // NEW: Method to save a calculation record
    public boolean insertHistory(String username, String type, String input, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(H_COL_2, username);
        contentValues.put(H_COL_3, type);
        contentValues.put(H_COL_4, input);
        contentValues.put(H_COL_5, result);
        long success = db.insert(HISTORY_TABLE, null, contentValues);
        return success != -1;
    }

    // NEW: Method to retrieve history for a specific user
    public Cursor getUserHistory(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + HISTORY_TABLE + " WHERE USERNAME=? ORDER BY ID DESC", new String[]{username});
    }
}