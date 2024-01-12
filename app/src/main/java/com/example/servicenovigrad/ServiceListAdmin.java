package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/// Interface that displays User database to Admin (Anyone including clients)
public class ServiceListAdmin extends AppCompatActivity {

    RecyclerView recyclerView;

    ServiceDatabaseHelper myDB;
    ArrayList<String> service_ID, service_name, service_description;
    ServiceUpdateCustomAdapter customAdapter;

    Button back;

    Button adminCreerUnService;


    public void sendBackToAdminPage() {
        Intent i = new Intent(this, HomePageAdmin.class);

        ServiceListAdmin.this.startActivity(i);
    }

    public void sendToServiceViewer() {
        Intent i = new Intent(this, ServiceViewer.class);

        ServiceListAdmin.this.startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list_admin);

        back = findViewById(R.id.back);
        adminCreerUnService = findViewById(R.id.adminCreerUnService);
        recyclerView = findViewById(R.id.recyclerView);


        myDB = new ServiceDatabaseHelper(ServiceListAdmin.this);
        service_ID = new ArrayList<>();
        service_name = new ArrayList<>();
        service_description = new ArrayList<>();

        displayData();

        customAdapter = new ServiceUpdateCustomAdapter(ServiceListAdmin.this, service_ID, service_name, service_description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceListAdmin.this));


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackToAdminPage();

            }
        });

        adminCreerUnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToServiceViewer();

            }
        });


    }


    void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                service_ID.add(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_ID)));
                service_name.add(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_NAME)));
                service_description.add(cursor.getString(cursor.getColumnIndex(ServiceDatabaseHelper.SERVICE_DESCRIPTION)));
            }
            // Add logging to check the retrieved data
            for (int i = 0; i < service_ID.size(); i++) {
                Log.d("ServiceListAdmin", "ID: " + service_ID.get(i) + ", Name: " + service_name.get(i) + ", Description: " + service_description.get(i));
            }
        }
    }
}