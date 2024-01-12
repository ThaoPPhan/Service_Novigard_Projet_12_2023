package com.example.servicenovigrad;

import static com.example.servicenovigrad.DatabaseHelper.DATABASE_TABLE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLDataException;


public class DatabaseSuccusale extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "NOVIGRAD.DB";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_TABLE = "SUCCUSALE";
    static final String USER_ID = "_ID_";
    static final String USER_NAME_SUCCUSALE = "userName";
    static final String USER_PASSWORD_SUCCUSALE = "passWord";
    static final String USER_ADDRESS_SUCCUSALE = "address";
    static final String USER_HEURES_SUCCUSALE = "heuresDeTravail";
    static final String USER_TYPE_SERVICE_SUCCUSALE = "typeDeServices";

    static final String USER_TYPE_SUCCUSALE = "type1";

    private static final String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ("
            + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_NAME_SUCCUSALE + " TEXT NOT NULL, "
            + USER_PASSWORD_SUCCUSALE + " TEXT NOT NULL, "
            + USER_ADDRESS_SUCCUSALE + " TEXT NOT NULL, "
            + USER_HEURES_SUCCUSALE + " TEXT NOT NULL, "
            + USER_TYPE_SERVICE_SUCCUSALE + " TEXT NOT NULL, "
            + USER_TYPE_SUCCUSALE + " TEXT NOT NULL);";
    public DatabaseSuccusale(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);

    }
    public Boolean insertuserdata (String name, String pass, String address, String hour, String typeService) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USER_NAME_SUCCUSALE", name);
        contentValues.put("USER_PASSWORD_SUCCUSALE", pass);
        contentValues.put("USER_ADDRESS_SUCCUSALE", address);
        contentValues.put("USER_HEURES_SUCCUSALE", hour);
        contentValues.put("USER_TYPE_SERVICE_SUCCUSALE", typeService);
        long result = DB.insert(DATABASE_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from DATABASE_TABLE", null);
        return cursor;
    }
    public DatabaseSuccusale open() throws SQLDataException {
        return this;
    }
}


//
//    public void delete(long _id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(DATABASE_TABLE, DatabaseHelper.USER_ID + "=" + _id, null);
//        db.close();
//    }
//
//    Cursor readAllData(){
//        String[] columnsToRetrieve = {USER_ID, USER_NAME_SUCCUSALE, USER_TYPE_SUCCUSALE};
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = null;
//        if (db != null){
//            cursor = db.query(DATABASE_TABLE, columnsToRetrieve, null, null, null, null, null);
//        }
//        return cursor;
//
//    }
//}
