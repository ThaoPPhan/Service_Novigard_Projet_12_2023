

package com.example.servicenovigrad;


import static com.example.servicenovigrad.ServiceDatabaseHelper.DATABASE_TABLE_S;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.sql.SQLDataException;

public class ServiceDatabaseManager {

    private ServiceDatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public ServiceDatabaseManager(Context ctx){
        context = ctx;
    }

    ///open database
    public ServiceDatabaseManager open() throws SQLDataException {
        dbHelper = new ServiceDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /// close database
    public void close(){
        dbHelper.close();
    }

    ///insert values in database table
    public void insert (String serviceName, String serviceDescription, String requiredDob, String includeAddress,
                        String includePhoto, String includeCitizenStatus, String includeProofResidence){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ServiceDatabaseHelper.SERVICE_NAME, serviceName);
        contentValues.put(ServiceDatabaseHelper.SERVICE_DESCRIPTION, serviceDescription);
        contentValues.put(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB, requiredDob);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS, includeAddress);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO, includePhoto);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS, includeCitizenStatus);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE, includeProofResidence);
        database.insert(DATABASE_TABLE_S, null, contentValues);
    }

    /// fetch data from Database
    public Cursor fetch(){
        String [] colums = new String[] {ServiceDatabaseHelper.SERVICE_NAME, ServiceDatabaseHelper.SERVICE_DESCRIPTION,
                ServiceDatabaseHelper.SERVICE_REQUIRED_DOB, ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS,
                ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO, ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS,
                ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE, };
        Cursor cursor = database.query(DATABASE_TABLE_S, colums, null, null,null,null,null);
        if (cursor != null) {
            cursor.moveToFirst();

        }
        return cursor;
    }
    /// update (Livrable 2, not implemented yet)
    public int update(long _id, String serviceName, String serviceDescription, String requiredDob,
                      String includeAddress, String includePhoto, String includeCitizenStatus, String includeProofResidence) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ServiceDatabaseHelper.SERVICE_NAME, serviceName);
        contentValues.put(ServiceDatabaseHelper.SERVICE_DESCRIPTION, serviceDescription);
        contentValues.put(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB, requiredDob);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS, includeAddress);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO, includePhoto);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS, includeCitizenStatus);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE, includeProofResidence);

        int ret = database.update(DATABASE_TABLE_S, contentValues, ServiceDatabaseHelper.SERVICE_ID + "=" + _id, null);
        return ret;
    }


}