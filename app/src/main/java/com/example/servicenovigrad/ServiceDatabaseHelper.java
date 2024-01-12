

package com.example.servicenovigrad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class ServiceDatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME_1 = "NOVIGRADSERVICES.DB";

    static final int DATABASE_VERSION_1 = 1;

    static final String DATABASE_TABLE_S = "SERVICES";

    static final String SERVICE_ID = "_ID";

    static final String SERVICE_NAME = "service";

    static final String SERVICE_DESCRIPTION = "description";

    static final String SERVICE_REQUIRED_DOB = "DOB";

    static final String INCLUDE_SERVICE_ADDRESS = "address";

    static final String INCLUDE_SERVICE_PHOTO = "photo";

    static final String INCLUDE_SERVICE_CITIZEN_STATUS = "citizen";

    static final String INCLUDE_SERVICE_PROOF_RESIDENCE = "proof";



    private static final String CREATE_DB_SERVICE = "CREATE TABLE " + DATABASE_TABLE_S + " ("
            + SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SERVICE_NAME + " TEXT NOT NULL, "
            + SERVICE_DESCRIPTION + " TEXT NOT NULL, "
            + SERVICE_REQUIRED_DOB + " TEXT NOT NULL, "
            + INCLUDE_SERVICE_ADDRESS + " TEXT NOT NULL, "
            + INCLUDE_SERVICE_PHOTO + " TEXT NOT NULL, "
            + INCLUDE_SERVICE_CITIZEN_STATUS + " TEXT NOT NULL, "
            + INCLUDE_SERVICE_PROOF_RESIDENCE + " TEXT NOT NULL);";

    public ServiceDatabaseHelper(Context context) {
        super(context, DATABASE_NAME_1, null, DATABASE_VERSION_1);
    }

    public ServiceDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB_SERVICE);
        insertPredefinedServices(db);
    }

    private void insertPredefinedServices(SQLiteDatabase db) {
        insertPredefinedService(db, "Permis de conduire", "Un permis de conduire est valide pendant cinq ans. Vous devez le renouveler tous les cinq ans, en ligne ou en personne.", "true", "true", "true", "true", "true");
        insertPredefinedService(db, "Plaque d'immatriculation", "Pour avoir le droit de conduire, votre véhicule doit être assuré et sa plaque d'immatriculation doit être en cours de validité.", "false", "true", "false", "true", "false");
        insertPredefinedService(db, "Carte de santé", "Renouvelez votre carte santé en ligne à l'aide d'une autre pièce d'identité de Novigrad", "true", "true", "true", "true", "true");
    }

    private void insertPredefinedService(SQLiteDatabase db, String serviceName, String serviceDescription,
                                         String requiredDob, String includeAddress, String includePhoto,
                                         String includeCitizenStatus, String includeProofResidence) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SERVICE_NAME, serviceName);
        contentValues.put(SERVICE_DESCRIPTION, serviceDescription);
        contentValues.put(SERVICE_REQUIRED_DOB, requiredDob);
        contentValues.put(INCLUDE_SERVICE_ADDRESS, includeAddress);
        contentValues.put(INCLUDE_SERVICE_PHOTO, includePhoto);
        contentValues.put(INCLUDE_SERVICE_CITIZEN_STATUS, includeCitizenStatus);
        contentValues.put(INCLUDE_SERVICE_PROOF_RESIDENCE, includeProofResidence);
        db.insert(DATABASE_TABLE_S, null, contentValues);
    }

    public void delete(long _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_S, ServiceDatabaseHelper.SERVICE_ID + "=" + _id, null);
        db.close();
    }

    Cursor readAllData(){
        String[] columnsToRetrieve = {SERVICE_ID, SERVICE_NAME, SERVICE_DESCRIPTION};
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.query(DATABASE_TABLE_S, columnsToRetrieve, null, null, null, null, null);
        }
        return cursor;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
