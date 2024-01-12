package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/// Service Update page for Admin

public class ServiceUpdateActivity extends AppCompatActivity {

    private EditText serviceNameEditText, serviceDescriptionEditText;
    private CheckBox dobCheckBox, addressCheckBox, photoCheckBox, citizenStatusCheckBox, proofResidenceCheckBox;

    private Button updateService;
    private String originalServiceName;
    private String originalServiceDescription;
    private String originalRequiredDob;
    private String originalIncludeAddress;
    private String originalIncludePhoto;
    private String originalIncludeCitizenStatus;
    private String originalIncludeProofResidence;

    public void sendBackToServiceListAdmin() {
        Intent i = new Intent(this, ServiceListAdmin.class);

        ServiceUpdateActivity.this.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update);

        serviceNameEditText = findViewById(R.id.serviceNameEditText);
        serviceDescriptionEditText = findViewById(R.id.serviceDescriptionEditText);
        dobCheckBox = findViewById(R.id.dobCheckBox);
        addressCheckBox = findViewById(R.id.addressCheckBox);
        photoCheckBox = findViewById(R.id.photoCheckBox);
        citizenStatusCheckBox = findViewById(R.id.citizenStatusCheckBox);
        proofResidenceCheckBox = findViewById(R.id.proofResidenceCheckBox);

        View btnCancel = findViewById(R.id.btnCancel);
        Button updateService = findViewById(R.id.updateService);

        Intent intent = getIntent();
        int serviceID = intent.getIntExtra("SERVICE_ID", -1);

        if (serviceID != -1) {
            displayServiceDetails(serviceID);
        } else {
            // Handle error message
        }

        updateService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get updated values from EditText and CheckBox
                String updatedServiceName = serviceNameEditText.getText().toString();
                String updatedServiceDescription = serviceDescriptionEditText.getText().toString();
                String updatedRequiredDob = dobCheckBox.isChecked() ? "true" : "false";
                String updatedIncludeAddress = addressCheckBox.isChecked() ? "true" : "false";
                String updatedIncludePhoto = photoCheckBox.isChecked() ? "true" : "false";
                String updatedIncludeCitizenStatus = citizenStatusCheckBox.isChecked() ? "true" : "false";
                String updatedIncludeProofResidence = proofResidenceCheckBox.isChecked() ? "true" : "false";

                if (TextUtils.isEmpty(updatedServiceName) || TextUtils.isEmpty(updatedServiceDescription)) {
                    Toast.makeText(ServiceUpdateActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if any data has changed
                if (!isDataChanged(updatedServiceName, updatedServiceDescription, updatedRequiredDob,
                        updatedIncludeAddress, updatedIncludePhoto, updatedIncludeCitizenStatus, updatedIncludeProofResidence)) {
                    // No changes detected, show toast message
                    Toast.makeText(ServiceUpdateActivity.this, "Aucune modification détectée.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update the database
                updateServiceData(serviceID, updatedServiceName, updatedServiceDescription, updatedRequiredDob,
                        updatedIncludeAddress, updatedIncludePhoto, updatedIncludeCitizenStatus, updatedIncludeProofResidence);

                String message = "Service modifier avec succès!";
                Toast.makeText(ServiceUpdateActivity.this, message, Toast.LENGTH_SHORT).show();

                sendBackToServiceListAdmin();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackToServiceListAdmin();
            }
        });
    }

    private void displayServiceDetails(int serviceID) {
        ServiceDatabaseHelper databaseHelper = new ServiceDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columnsToRetrieve = {
                ServiceDatabaseHelper.SERVICE_NAME,
                ServiceDatabaseHelper.SERVICE_DESCRIPTION,
                ServiceDatabaseHelper.SERVICE_REQUIRED_DOB,
                ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS,
                ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO,
                ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS,
                ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE
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
            String serviceName = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_NAME));
            String serviceDescription = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_DESCRIPTION));
            String requiredDob = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB));
            String includeAddress = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS));
            String includePhoto = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO));
            String includeCitizenStatus = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS));
            String includeProofResidence = cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE));

            originalServiceName = serviceName;
            originalServiceDescription = serviceDescription;
            originalRequiredDob = requiredDob;
            originalIncludeAddress = includeAddress;
            originalIncludePhoto = includePhoto;
            originalIncludeCitizenStatus = includeCitizenStatus;
            originalIncludeProofResidence = includeProofResidence;



            // Display details in EditText and CheckBox
            serviceNameEditText.setText(serviceName);
            serviceDescriptionEditText.setText(serviceDescription);
            dobCheckBox.setChecked("true".equals(requiredDob));
            addressCheckBox.setChecked("true".equals(includeAddress));
            photoCheckBox.setChecked("true".equals(includePhoto));
            citizenStatusCheckBox.setChecked("true".equals(includeCitizenStatus));
            proofResidenceCheckBox.setChecked("true".equals(includeProofResidence));

            cursor.close();
            db.close();
        }

    }

    private void updateServiceData(int serviceID, String serviceName, String serviceDescription, String requiredDob,
                                   String includeAddress, String includePhoto, String includeCitizenStatus, String includeProofResidence) {
        ServiceDatabaseHelper databaseHelper = new ServiceDatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ServiceDatabaseHelper.SERVICE_NAME, serviceName);
        contentValues.put(ServiceDatabaseHelper.SERVICE_DESCRIPTION, serviceDescription);
        contentValues.put(ServiceDatabaseHelper.SERVICE_REQUIRED_DOB, requiredDob);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_ADDRESS, includeAddress);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_PHOTO, includePhoto);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_CITIZEN_STATUS, includeCitizenStatus);
        contentValues.put(ServiceDatabaseHelper.INCLUDE_SERVICE_PROOF_RESIDENCE, includeProofResidence);

        db.update(ServiceDatabaseHelper.DATABASE_TABLE_S, contentValues, ServiceDatabaseHelper.SERVICE_ID + "=?",
                new String[]{String.valueOf(serviceID)});

        db.close();
    }

    private boolean isDataChanged(String serviceName, String serviceDescription, String requiredDob,
                                  String includeAddress, String includePhoto, String includeCitizenStatus,
                                  String includeProofResidence) {
        return !serviceName.equals(originalServiceName)
                || !serviceDescription.equals(originalServiceDescription)
                || !requiredDob.equals(originalRequiredDob)
                || !includeAddress.equals(originalIncludeAddress)
                || !includePhoto.equals(originalIncludePhoto)
                || !includeCitizenStatus.equals(originalIncludeCitizenStatus)
                || !includeProofResidence.equals(originalIncludeProofResidence);
    }

}

