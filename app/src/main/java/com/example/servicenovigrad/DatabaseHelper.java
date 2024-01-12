

package com.example.servicenovigrad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "NOVIDRAG.DB";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "USERS";
    static final String USER_ID = "_ID";
    static final String USER_NAME = "username";
    static final String USER_PASSWORD = "password";

    static final String USER_TYPE = "type";


    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME + " TEXT NOT NULL, "
            + USER_PASSWORD + " TEXT NOT NULL, "
            + USER_TYPE + " TEXT NOT NULL);";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /// Create Database with the preexisting Admin account
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_QUERY);
        insertPredefinedAccount(db);
    }

    ///initialise le compte admin predefini
    private void insertPredefinedAccount(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, "admin");
        contentValues.put(USER_PASSWORD, "123admin456");
        contentValues.put(USER_TYPE, "admin");
        db.insert(DATABASE_TABLE, null, contentValues);
    }

    /// Upgrade method not yet implemented, for Livrable 2
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL( "DROP TABLE IF EXISTS " + DATABASE_TABLE);
    }

    /// delete (Livrable 2, not implemented yet)
    public void delete(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, DatabaseHelper.USER_ID + "=" + _id, null);
        db.close();
    }

    Cursor readAllData(){
        String[] columnsToRetrieve = {USER_ID, USER_NAME, USER_TYPE};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.query(DATABASE_TABLE, columnsToRetrieve, null, null, null, null, null);
        }
        return cursor;

    }
}


