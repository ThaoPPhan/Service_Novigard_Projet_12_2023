package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/// Service List Viewer for Users
public class ServiceListViewer extends AppCompatActivity {

    private Button btnCancel;

    public void sendBackToServiceList() {
        Intent i = new Intent(this, ServiceList.class);

        ServiceListViewer.this.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list_viewer);
        btnCancel = findViewById(R.id.btnCancel);

        Intent intent = getIntent();
        int serviceID = intent.getIntExtra("SERVICE_ID", -1);

        if (serviceID != -1) {
            displayServiceDetails(serviceID);
        } else {
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackToServiceList();

            }
        });
    }

    private void displayServiceDetails(int serviceID) {
        // Use the serviceID to query the database and display details in TextViews
        // You can use the ServiceDatabaseHelper class to fetch details based on the serviceID
        ServiceDatabaseHelper databaseHelper = new ServiceDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columnsToRetrieve = {
                ServiceDatabaseHelper.SERVICE_NAME,
                ServiceDatabaseHelper.SERVICE_DESCRIPTION,
                String.valueOf(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB),
                String.valueOf(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS),
                String.valueOf(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO),
                String.valueOf(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS),
                String.valueOf(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE)
        };

        Cursor cursor = db.query(
                ServiceDatabaseHelper.DATABASE_TABLE_S,
                columnsToRetrieve,
                ServiceDatabaseHelper.SERVICE_ID + "=?",
                new String[]{String.valueOf(serviceID)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String serviceName = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_NAME));
            @SuppressLint("Range") String serviceDescription = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_DESCRIPTION));
            @SuppressLint("Range") String serviceDOB = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB));
            @SuppressLint("Range") String serviceAddress = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS));
            @SuppressLint("Range") String servicePhoto = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO));
            @SuppressLint("Range") String serviceCitStatus = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS));
            @SuppressLint("Range") String serviceProofRes = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE));
            // Retrieve other details as needed

            String serviceDOBText = "true".equals(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB))) ? "Date de naissance" : "";
            String serviceAddressText = "true".equals(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS))) ? "Addresse" : "";
            String servicePhotoText = "true".equals(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO))) ? "Photo identification" : "";
            String serviceCitStatusText = "true".equals(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS))) ? "Statut de citoyenneté" : "";
            String serviceProofResText = "true".equals(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE))) ? "Preuve de résidence" : "";


            // Display details in TextViews or handle accordingly
            TextView serviceNameDisplay = findViewById(R.id.serviceNameEditText);
            TextView serviceDescriptionDisplay = findViewById(R.id.serviceDescriptionEditText);
            TextView serviceDOBDisplay = findViewById(R.id.serviceNeededDOB);
            TextView serviceAddressDisplay = findViewById(R.id.serviceNeededAddress);
            TextView servicePhotoDisplay = findViewById(R.id.serviceNeededPhoto);
            TextView serviceCitStatusDisplay = findViewById(R.id.serviceNeededCitStatus);
            TextView serviceProofResDisplay = findViewById(R.id.serviceNeededProofRes);

            serviceNameDisplay.setText(serviceName);
            serviceDescriptionDisplay.setText(serviceDescription);
            serviceDOBDisplay.setText(String.valueOf(serviceDOBText));
            serviceAddressDisplay.setText(String.valueOf(serviceAddressText));
            servicePhotoDisplay.setText(String.valueOf(servicePhotoText));
            serviceCitStatusDisplay.setText(String.valueOf(serviceCitStatusText));
            serviceProofResDisplay.setText(String.valueOf(serviceProofResText));

            // Close the cursor and database
            cursor.close();
            db.close();
        }
    }


}