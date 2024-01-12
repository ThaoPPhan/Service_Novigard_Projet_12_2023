package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLDataException;

public class ServiceViewer extends AppCompatActivity {

    private Button saveService;
    private Button backToServiceListAdmin;
    private EditText serviceName;
    private EditText serviceDescription;
    private CheckBox services_dob_required;
    private CheckBox services_adresse_required;
    private CheckBox services_photo_required;
    private CheckBox services_citoyenneté_required;
    private CheckBox services_residence_required;


    ServiceDatabaseManager dbManager;

    public void sendBackToServiceListAdmin(){
        Intent i = new Intent(this,ServiceListAdmin.class);

        ServiceViewer.this.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_viewer);

        saveService = findViewById(R.id.saveService);
        backToServiceListAdmin = findViewById(R.id.backToServiceListAdmin);

        serviceName = findViewById(R.id.serviceNameEditText);
        serviceDescription = findViewById(R.id.serviceDescriptionEditText);

        services_dob_required = findViewById(R.id.dobCheckBox);
        services_adresse_required = findViewById(R.id.addressCheckBox);
        services_photo_required = findViewById(R.id.photoCheckBox);
        services_citoyenneté_required = findViewById(R.id.citizenStatusCheckBox);
        services_residence_required = findViewById(R.id.proofResidenceCheckBox);

        dbManager = new ServiceDatabaseManager(this);
        try {
            dbManager.open();
        } catch (SQLDataException e) {
            throw new RuntimeException(e);
        }

        saveService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newServiceNameData = serviceName.getText().toString();
                String newServiceDescriptionData = serviceDescription.getText().toString();

                if (TextUtils.isEmpty(newServiceNameData) || TextUtils.isEmpty(newServiceDescriptionData)) {
                    Toast.makeText(ServiceViewer.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Call a method to handle the button click


                try {
                    dbManager.open();
                } catch (SQLDataException e) {
                    throw new RuntimeException(e);
                }

                dbManager.insert(newServiceNameData,
                        newServiceDescriptionData,
                        services_dob_required.isChecked() ? "true" : "false",
                        services_adresse_required.isChecked() ? "true" : "false",
                        services_photo_required.isChecked() ? "true" : "false",
                        services_citoyenneté_required.isChecked() ? "true" : "false",
                        services_residence_required.isChecked() ? "true" : "false"
                );

                dbManager.close();

                String message = "Service créé avec succès!";
                Toast.makeText(ServiceViewer.this, message, Toast.LENGTH_SHORT).show();

                sendBackToServiceListAdmin();

            }
        });

        backToServiceListAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackToServiceListAdmin();

            }
        });
    }

    private void onSaveService(){
        int newDOB = services_dob_required.isChecked() ? 1 : 0;
        int newAddress = services_adresse_required.isChecked() ? 1 : 0;
        int newPhoto = services_photo_required.isChecked() ? 1 : 0;
        int newCitizenship = services_citoyenneté_required.isChecked() ? 1 : 0;
        int newProofRes = services_residence_required.isChecked() ? 1 : 0;
    }
}

























