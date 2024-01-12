

package com.example.servicenovigrad;


import static com.example.servicenovigrad.DatabaseHelper.DATABASE_TABLE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.sql.SQLDataException;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DatabaseManager(Context ctx){
        context = ctx;
    }

    ///open database
    public DatabaseManager open() throws SQLDataException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /// close database
    public void close(){
        dbHelper.close();
    }

    ///insert values in database table
    public void insert (String username, String password, String type){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USER_NAME, username);
        contentValues.put(DatabaseHelper.USER_PASSWORD, password);
        contentValues.put(DatabaseHelper.USER_TYPE, type);
        database.insert(DATABASE_TABLE, null, contentValues);
    }

    /// fetch data from Database
    public Cursor fetch(){
        String [] colums = new String[] {DatabaseHelper.USER_ID, DatabaseHelper.USER_NAME, DatabaseHelper.USER_PASSWORD, DatabaseHelper.USER_TYPE};
        Cursor cursor = database.query(DATABASE_TABLE, colums, null, null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();

        }
        return cursor;
    }


}



